package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import action.ExtendPreviewAction;
import action.ExtendPreviewResult;
import model.Room;

@WebServlet("/ExtendServlet")
public class ExtendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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

		ExtendPreviewAction action = new ExtendPreviewAction();
		ExtendPreviewResult result;
		try {
			result = action.preview(room.getId(), extendMinutes);
			session.setAttribute("extendMinutes", extendMinutes);
			request.setAttribute("currentLeaving", result.getCurrentLeaving());
			request.setAttribute("extendMinutes", extendMinutes);
			request.setAttribute("newLeaving", result.getNewLeaving());

			// 確認画面へ.
			request.getRequestDispatcher("/time_extend_confirm.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
