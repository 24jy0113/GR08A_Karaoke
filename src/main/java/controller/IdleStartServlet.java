package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.RoomDao;
import model.Room;

@WebServlet("/IdleStartServlet")
public class IdleStartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public IdleStartServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));

		try {
			// 部屋番号からRoomインスタンスを取得.
			Room room = RoomDao.getRoomByRoomNumber(roomNumber);

			if (room.getStatusId() == 1) {
				response.sendRedirect(
						request.getContextPath() + "/RoomSearchServlet?roomNumber=" + String.valueOf(roomNumber));
				return;
			}

			request.getSession().setAttribute("room", room);

			request.getRequestDispatcher("/cus_top.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
