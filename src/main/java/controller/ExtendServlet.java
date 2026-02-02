package controller;

import java.io.IOException;
import java.time.LocalTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Room;
import service.RoomTimeService;

@WebServlet("/ExtendServlet")
public class ExtendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("room") == null) {
			response.sendRedirect("room_search.jsp");
			return;
		}

		// 延長分（分）.
		int extendMinutes = Integer.parseInt(request.getParameter("extendMinutes"));

		// セッションからルーム情報を取得.
		Room room = (Room) session.getAttribute("room");

		// 現在の退室時間を取得.
		LocalTime currentLeaving = RoomTimeService.calcActualLeavingTime(room);

		// 延長後の退室時間を計算.
		LocalTime newLeaving = currentLeaving.plusMinutes(extendMinutes);

		// JSPに渡す
		request.setAttribute("currentLeaving", currentLeaving);
		request.setAttribute("extendMinutes", extendMinutes);
		request.setAttribute("newLeaving", newLeaving);

		// 確認画面へ.
		request.getRequestDispatcher("/time_extend_confirm.jsp").forward(request, response);

	}
}
