package controller;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.RoomDao;
import model.Room;
import service.RoomTimeService;

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
		Room room;
		LocalTime actualLeaving;
		Time nextRecTime;

		// 延長時間の最小値.
		int extendMinutes = 30;

		try {
			// DBから最新の部屋情報を取得.
			room = RoomDao.getRoomById(sessionRoom.getId());
			if (room == null) {
				throw new IllegalStateException("部屋情報がDBに存在しません");
			}
			// 現在の退室時間.
			actualLeaving = RoomTimeService.calcActualLeavingTime(room);

			// 次の予約受付時間.
			nextRecTime = RoomDao.getNextReceptionTime(room.getId());

			LocalTime nextReception = nextRecTime != null
					? nextRecTime.toLocalTime()
					: null;

			// 延長可能か？.
			boolean canExtend = RoomTimeService.canExtend(actualLeaving, nextReception, extendMinutes);

			if (canExtend) {
				request.getRequestDispatcher("/time_extend.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("/time_extend_refuse.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
