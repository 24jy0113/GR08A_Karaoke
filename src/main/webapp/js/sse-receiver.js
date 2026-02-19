let currentMessageId = null;
/**
 * 通知の受信を開始する
 * @param {string} roomId - JSP側から渡されるルームID
 */
function initializeNotification(roomId) {
	if (!roomId) return;

	// SSE接続を開始.
	const eventSource = new EventSource(`SseNotificationServlet?roomId=${roomId}`);

	// 通知を受信したときの処理.
	eventSource.onmessage = function(event) {
		currentMessageId = event.lastEventId; // サーバーが送った id: xxx がここに入る.
		const data = event.data;            // data: xxx の中身.

		console.log("通知を受信:", data);
		showNotice(data);
	};

	// エラー（切断）時の処理.
	eventSource.onerror = function() {
		console.log("接続が切れました。再接続を待機中...");
	};
}

// モーダル表示関数.
function showNotice(msg) {
	const modal = document.getElementById("sseNoticeModal");
	const text = document.getElementById("sseNoticeText");
	const closeBtn = document.getElementById("sseCloseBtn");
	text.innerHTML = msg;
	modal.style.display = "block";
	closeBtn.onclick = function() {
		sseCloseNotice(currentMessageId);
	};
}

// サーバーに確認を送信.
function sendAckToServer(messageId) {
	if (!messageId) return;

	// 届いたことをサーバーに報告（ACK）.
	fetch(`SseNotificationServlet?action=ack&messageId=${messageId}`, {
		method: 'POST'
	}).then(response => {
		console.log("ACK送信完了");
	});
	currentMessageId = null;
}

// 確認とモーダルの非表示.
function sseCloseNotice(messageId) {
	sendAckToServer(messageId)
	const modal = document.getElementById("sseNoticeModal");
	modal.style.display = "none";
}