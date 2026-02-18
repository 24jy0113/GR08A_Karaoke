package controller;

import java.io.IOException;
import java.net.URLEncoder;

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
			Room room;
			if (roomNoStr != null && !roomNoStr.isEmpty()) {
				room=RoomDao.getRoomByRoomNumber(Integer.parseInt(roomNoStr));
				if(room==null) {
					// 部屋の検索結果がnull.

					// フロントエンド用のメッセージ.
					String message = URLEncoder.encode("該当する部屋が見つかりません", "UTF-8");

					// 検索フォームに返却.
					res.sendRedirect(req.getContextPath() + "/front/front_room_search.jsp?e=" + message);
					return;
				}
			} else {
				String roomIdStr = req.getParameter("roomId");
				if (roomIdStr == null) {
					res.sendRedirect(req.getContextPath() + "/RoomListServlet");
					return;
				}
				roomId = Integer.parseInt(roomIdStr);
				room=RoomDao.getRoomById(roomId);
				if(room == null) {
					res.sendRedirect(req.getContextPath() + "/RoomListServlet");
					return;
				}
			}

			boolean isStaffAction = Boolean.parseBoolean(req.getParameter("isStaffAction"));
			if (isStaffAction) {
				session.setAttribute("isStaffAction", true);
			}
			session.setAttribute("room", room);
			req.getRequestDispatcher("/cus_top.jsp").forward(req, res);

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
