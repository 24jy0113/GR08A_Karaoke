package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import action.ExtendAction;
import action.ExtendCheckAction;
import dao.RoomDao;
import model.Room;

@WebServlet("/ExtendConfirmServlet")
public class ExtendConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExtendConfirmServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendRedirect("room_search.jsp");
			return;
		}

		Room sessionRoom = (Room) session.getAttribute("room");
		if (sessionRoom == null) {
			throw new IllegalStateException("セッションに部屋情報がありません");
		}

		Integer extendMinutes = (Integer) session.getAttribute("extendMinutes");
		if (extendMinutes == null) {
			throw new IllegalStateException("延長時間が未指定です");
		}

		try {
			ExtendCheckAction checkAction = new ExtendCheckAction();
			List<Integer> available = checkAction.getAvailableExtendMinutes(sessionRoom.getId());

			if (!available.contains(extendMinutes)) {
				throw new IllegalStateException("不正な延長時間です");
			}
			new ExtendAction().execute(extendMinutes, session);

			// DBから最新の部屋情報を取得
			Room latestRoom = RoomDao.getRoomById(sessionRoom.getId());
			session.setAttribute("room", latestRoom);

			// 完了画面へ行く.
			response.sendRedirect(request.getContextPath() + "/time_extend_confirmed.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "延長処理に失敗しました");
		}
	}
}
