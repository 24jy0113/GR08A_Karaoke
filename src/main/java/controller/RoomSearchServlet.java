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

@WebServlet("/RoomSearchServlet")
public class RoomSearchServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String rawRoomNumber = request.getParameter("roomNumber");
		if (!rawRoomNumber.matches("^\\d+$")) {
			// 入力されたパラメータが数字以外の時.
			// 検索フォームに返却.
			response.sendRedirect(request.getContextPath() + "/room_search.jsp");
			return;
		}

		try {
			int roomNumber = Integer.parseInt(rawRoomNumber);

			// 部屋番号からroom_idを取得.
			Room room = RoomDao.getRoomByRoomNumber(roomNumber);

			if (room == null) {
				// 部屋の検索結果がnull.

				// フロントエンド用のメッセージ.
				String message = URLEncoder.encode("該当する部屋が見つかりません", "UTF-8");

				// 検索フォームに返却.
				response.sendRedirect(request.getContextPath() + "/room_search.jsp?e=" + message);
				return;
			}
			request.getRequestDispatcher("/room_idle.jsp?roomNumber=" + room.getRoomNo()).forward(request, response);

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
