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
		String rawRoomNumber = req.getParameter("roomNumber");

		var session = req.getSession();
		try {
			int roomId;
			Room room;
			if (rawRoomNumber != null && !rawRoomNumber.isEmpty()) {
				if (!rawRoomNumber.matches("^\\d+$")) {
					// 入力されたパラメータが数字以外の時.
					// フロントエンド用のメッセージ.
					String message = URLEncoder.encode("半角数字以外は使用できません", "UTF-8");
					
					// 検索フォームに返却.
					res.sendRedirect(req.getContextPath() + "/front/front_room_search.jsp?e=" + message);
					return;
				}
				room = RoomDao.getRoomByRoomNumber(Integer.parseInt(rawRoomNumber));
				// 部屋の検索結果がnull.
				if (room == null) {
					// フロントエンド用のメッセージ.
					String message = URLEncoder.encode("該当する部屋が見つかりません", "UTF-8");

					// 検索フォームに返却.
					res.sendRedirect(req.getContextPath() + "/front/front_room_search.jsp?e=" + message);
					return;
				}
				// 部屋情報のstatusが「空き」の状態である.
				if (room.getStatusId() == 1) {
					// フロントエンド用のメッセージ.
					String message = URLEncoder.encode("該当する部屋は現在空室です", "UTF-8");

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
				room = RoomDao.getRoomById(roomId);
				if (room == null || room.getStatusId() == 1) {
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
