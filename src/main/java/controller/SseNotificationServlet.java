package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.AsyncEvent;
import jakarta.servlet.AsyncListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SseNotificationServlet
 */
@WebServlet(urlPatterns = "/SseNotificationServlet", asyncSupported = true)
public class SseNotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// ルームIdごとにAsyncContextをを管理
	public static final Map<String, AsyncContext> roomConnections = new ConcurrentHashMap<>();

	// ルームごとにメッセージを管理.
	private static final Map<String, Set<String>> roomMessageIds = new ConcurrentHashMap<>();

	// 送信済みだが確認が取れていないメッセージを管理.
	private static final Map<String, ScheduledFuture<?>> pendingAcks = new ConcurrentHashMap<>();

	// スケジュール作る感じ.
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/event-stream");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Connection", "keep-alive");

		// 部屋idを取得する.
		String roomId = request.getParameter("roomId");
		if (roomId == null || roomId.isEmpty())
			return;

		PrintWriter pw = response.getWriter();
		pw.write(": ok\n\n"); // SSEのコメント行
		pw.flush();

		// 非同期コンテキストの開始.
		final AsyncContext asyncContext = request.startAsync();

		// タイムアウト設定（ミリ秒）.
		asyncContext.setTimeout(3600000);

		// 接続が切れたらMapから削除するリスナー
		asyncContext.addListener(new AsyncListener() {
			@Override
			public void onComplete(AsyncEvent event) {
				roomConnections.remove(roomId);
			}

			@Override
			public void onTimeout(AsyncEvent event) {
				roomConnections.remove(roomId);
				event.getAsyncContext().complete();
			}

			@Override
			public void onError(AsyncEvent event) {
				roomConnections.remove(roomId);
			}

			@Override
			public void onStartAsync(AsyncEvent event) {
			}
		});

		roomConnections.put(roomId, asyncContext);
	}

	public static void sendWithRetry(String roomId, String messageId, String payload) {

		// 部屋のメッセージに入れる.
		roomMessageIds.computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet()).add(messageId);

		// 同じメッセージの別のスケジュールがあれば削除.
		ScheduledFuture<?> oldFuture = pendingAcks.remove(messageId);
		if (oldFuture != null)
			oldFuture.cancel(false);

		// まだ有効なメッセージのではないなら何もせず止まる.
		Set<String> activeIds = roomMessageIds.get(roomId);
		if (activeIds == null || !activeIds.contains(messageId)) {
			return;
		}

		AsyncContext ac = roomConnections.get(roomId);

		if (ac != null) {
			try {
				synchronized (ac.getResponse()) {
					PrintWriter pw = ac.getResponse().getWriter();
					pw.write("id: " + messageId + "\n");
					pw.write("data: " + payload + "\n\n");
					pw.flush();
				}
			} catch (IOException e) {
				roomConnections.remove(roomId);
				ac = null;
			}
		}

		// 30秒後に確認がなければ再送するタスクをスケジュール.
		ScheduledFuture<?> future = scheduler.schedule(() -> {
			System.out.println("再送実行: " + messageId);
			sendWithRetry(roomId, messageId, payload);
		}, 30, TimeUnit.SECONDS);

		// 確認待ちに入れる.
		pendingAcks.put(messageId, future);
	}

	// クライアントからの確認応答（ACK）を受け取るメソッド（別ServletかPOSTリクエストで呼ぶ）
	public static void receiveAck(String messageId) {
		// 確認待ちから削除.
		ScheduledFuture<?> future = pendingAcks.remove(messageId);
		if (future != null) {
			future.cancel(false); // タイマーを止める.
		}

		// ルームのセットからメッセージIDを削除.
		roomMessageIds.values().forEach(set -> set.remove(messageId));

		System.out.println("確認完了: " + messageId);
	}

	// 部屋のリセットと部屋のメッセージの再送を停止.
	public static void resetRoom(String roomId) {
		// 1. そのルームに紐づく全メッセージIDを取得
		Set<String> messageIds = roomMessageIds.remove(roomId);

		if (messageIds != null) {
			for (String msgId : messageIds) {
				// 2. pendingAcksからタイマーを取り出してキャンセル
				ScheduledFuture<?> future = pendingAcks.remove(msgId);
				if (future != null) {
					future.cancel(false); // 再送ループを止める
					System.out.println("ルームリセットにより停止: " + msgId);
				}
			}
		}

		// 接続自体も切断してMapから削除
		AsyncContext ac = roomConnections.remove(roomId);
		if (ac != null) {
			try {
				ac.complete();
			} catch (Exception e) {
				// すでに切れている場合は無視
			}
		}
	}

	// 通知を確認したら終了.
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		String messageId = request.getParameter("messageId");

		if ("ack".equals(action) && messageId != null) {
			// 前に作った receiveAck を呼び出してループを止める
			receiveAck(messageId);
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
}
