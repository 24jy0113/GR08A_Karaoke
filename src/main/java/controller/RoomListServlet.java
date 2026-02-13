package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.RoomDao;
import model.Room;

@WebServlet("/RoomListServlet")
public class RoomListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RoomListServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		var session = request.getSession();
		session.removeAttribute("isStaffAction");
		try {
			// フォームから送られてくる絞り込みパラメータを取得.
			String statusParam = request.getParameter("statusId");
			List<Room> roomList;

			if (statusParam == null || statusParam.equals("0")) {
				// 絞り込みなし(全部屋取得).
				roomList = RoomDao.getAllRooms();
			} else {
				// 絞り込みあり(特定ステータスの部屋だけ取得).
				int statusId = Integer.parseInt(statusParam);
				roomList = RoomDao.getRoomsByStatus(statusId);
			}

			// JSPに渡す.
			request.setAttribute("roomList", roomList);
			request.getRequestDispatcher("/front/room_list.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
