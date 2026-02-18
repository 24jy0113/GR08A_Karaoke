package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

		try {
			int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));

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

			// 残り時間計算.
			int remainingMinutes = calcRemainingMinutes(room.getReceptionTime(), room.getLeavingTime());

			request.getSession().setAttribute("room", room);
			request.getSession().setAttribute("remainingMinutes", remainingMinutes);

			request.getRequestDispatcher("/cus_top.jsp").forward(request, response);

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private int calcRemainingMinutes(Time receptionTime, Time leavingTime) {

		if (leavingTime == null)
			return 0;

		LocalDate today = LocalDate.now();

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime leaveDateTime = LocalDateTime.of(today, leavingTime.toLocalTime());

		// 翌日にまたぐ場合
		if (receptionTime != null &&
				leavingTime.before(receptionTime)) {
			leaveDateTime = leaveDateTime.plusDays(1);
		}

		long minutes = Duration.between(now, leaveDateTime).toMinutes();

		return (int) Math.max(minutes, 0);
	}

}
