package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import service.RoomTimeService;

@WebServlet("/RoomTimeUpdateServlet")
public class RoomTimeUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RoomTimeUpdateServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int roomId = Integer.parseInt(request.getParameter("roomId"));
		String receptionTimeStr = request.getParameter("receptionTime");

		try {
			RoomTimeService service = new RoomTimeService();
			service.updateRoomTimes(roomId, receptionTimeStr);

			// 処理成功後は一覧画面にリダイレクト.
			response.sendRedirect("room_list.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "時間更新に失敗しました");
			request.getRequestDispatcher("/front/room_list.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
