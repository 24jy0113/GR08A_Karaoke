package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.RoomDao;
import model.Room;

@WebServlet("/CusTopServlet")
public class CusTopServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		var session = req.getSession();
		try {
			int roomId;
			String roomNoStr = req.getParameter("roomNumber");
			if (roomNoStr != null && !roomNoStr.isEmpty()) {
				roomId = RoomDao.getRoomByRoomNumber(Integer.parseInt(roomNoStr)).getId();
			} else {
				String roomIdStr = req.getParameter("roomId");
				if (roomIdStr == null) {
					res.sendRedirect(req.getContextPath() + "/RoomListServlet");
					return;
				}
				roomId = Integer.parseInt(roomIdStr);
			}

			boolean isStaffAction = Boolean.parseBoolean(req.getParameter("isStaffAction"));
			if (isStaffAction) {
				session.setAttribute("isStaffAction", true);
			}

			Room room = RoomDao.getRoomById(roomId);

			if (room == null) {
				res.sendRedirect(req.getContextPath() + "/RoomListServlet");
				return;
			}
			session.setAttribute("room", room);
			req.getRequestDispatcher("/cus_top.jsp").forward(req, res);

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
