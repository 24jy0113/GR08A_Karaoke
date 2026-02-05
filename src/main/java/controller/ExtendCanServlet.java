package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import action.ExtendCheckAction;
import dao.RoomDao;
import model.Room;

@WebServlet("/ExtendCanServlet")
public class ExtendCanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExtendCanServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Room sessionRoom = (Room) session.getAttribute("room");
		if (sessionRoom == null) {
			throw new IllegalStateException("セッションに部屋情報がありません");
		}

		try {
			Room latestRoom = RoomDao.getRoomById(sessionRoom.getId());
			session.setAttribute("room", latestRoom); // セッション更新.

			ExtendCheckAction action = new ExtendCheckAction();
			List<Integer> availableMinutes = action.getAvailableExtendMinutes(sessionRoom.getId());

			if (availableMinutes.isEmpty()) {
				request.getRequestDispatcher("/time_extend_refuse.jsp").forward(request, response);
				return;
			}
			request.setAttribute("availableMinutes", availableMinutes);
			request.getRequestDispatcher("/time_extend.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "延長情報の取得に失敗しました");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
