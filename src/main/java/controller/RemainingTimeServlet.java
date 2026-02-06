package controller;

import java.io.IOException;
import java.time.LocalTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.RoomDao;
import model.Room;

@WebServlet("/RemainingTimeServlet")
public class RemainingTimeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RemainingTimeServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("room") == null) {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write("{\"sessionExpired\":true}");
			return;
		}

		Room sessionRoom = (Room) session.getAttribute("room");

		try {
			// DBから最新情報取得.
			Room room = RoomDao.getRoomById(sessionRoom.getId());

			// 実際の退室時間.
			LocalTime leaving = room.getLeavingTime().toLocalTime();

			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write("{\"leaveTime\":\"" + leaving.toString() + "\"}");

		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("{\"error\":true}");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
