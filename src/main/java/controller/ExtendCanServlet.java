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

/**
 * Servlet implementation class ExtendCanServlet
 */
@WebServlet("/ExtendCanServlet")
public class ExtendCanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExtendCanServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Room room = (Room) session.getAttribute("room");

		if (room == null) {
			response.sendRedirect("cus_top.jsp"); // ルーム情報がない場合はトップへ
			return;
		}

		// 延長時間の初期値（選択肢の最小値など）
		int extendMinutes = 30;

		// 現在の退室時間
		LocalTime actualLeaving = RoomTimeService.calcActualLeavingTime(room);

		// 次の予約受付時間
		Time nextRecTime;

		try {
			nextRecTime = RoomDao.getNextReceptionTime(room.getId());

			LocalTime nextReception = nextRecTime != null
					? nextRecTime.toLocalTime()
					: null;

			// 延長可能か？
			boolean canExtend = RoomTimeService.canExtend(actualLeaving, nextReception, extendMinutes);

			if (canExtend) {
				request.getRequestDispatcher("/time_extend.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("/time_extend_refuse.jsp").forward(request, response);
			}
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
