package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.RoomDao;
import service.RoomTimeService;

@WebServlet("/RoomUpdateServlet")
public class RoomUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RoomUpdateServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int roomId = Integer.parseInt(request.getParameter("roomId"));
			boolean alcohol = request.getParameter("alcohol").equals("1");
			int statusId = Integer.parseInt(request.getParameter("statusId"));
			String receptionTime = request.getParameter("receptionTime");
			String leavingTime = request.getParameter("leavingTime");

			RoomTimeService service = new RoomTimeService();

			// 受付時間・退室時間更新
			if(statusId == 2) {
				service.updateRoomTimes(roomId, receptionTime);
			}else {
				service.updateRoomTimes(roomId, receptionTime,leavingTime);
			}
			

			// 酒類・状態更新
			RoomDao.updateAlcohol(roomId, alcohol);
			RoomDao.updateStatus(roomId, statusId);

			// 完了画面へ
			request.setAttribute("message", "更新できました");
			request.getRequestDispatcher("/room_updated.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
