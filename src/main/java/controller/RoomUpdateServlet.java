package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.RoomDao;
import model.Room;
import service.RoomTimeService;

@WebServlet("/RoomUpdateServlet")
public class RoomUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RoomUpdateServlet() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    try {
	        int roomId = Integer.parseInt(request.getParameter("roomId"));
	        boolean alcohol = request.getParameter("alcohol").equals("1");
	        int statusId = Integer.parseInt(request.getParameter("statusId"));
	        String receptionTime = request.getParameter("receptionTime");
	        String leavingTime = request.getParameter("leavingTime");

	        RoomTimeService service = new RoomTimeService();
	        Room room = RoomDao.getRoomById(roomId);

	        if (!RoomDao.existsRoomUsageStatus(roomId)) {
	            RoomDao.insertRoomUsageStatus(roomId);
	        }

	        // 「空き」にするには「会計済み」からのみ許可
	        if (statusId == 1 && room.getStatusId() != 4) {
	            request.setAttribute("message", "会計済みの状態からのみ空きに変更できます。");
	            request.setAttribute("roomList", RoomDao.getAllRooms());
	            request.getRequestDispatcher("/front/room_list.jsp").forward(request, response);
	            return;
	        }
	     // 受付時間・退室時間が空の場合は更新しない
	        if ((receptionTime == null || receptionTime.isEmpty()) 
	             && (leavingTime == null || leavingTime.isEmpty()) 
	             && statusId != 1) {
	            request.setAttribute("message", "受付時間と退室時間を入力してください。");
	            request.setAttribute("roomList", RoomDao.getAllRooms());
	            request.getRequestDispatcher("/front/room_list.jsp").forward(request, response);
	            return;
	        }
	        // 受付時間・退室時間更新.
	        if (statusId == 3 || statusId == 2) {
	            if (room.getRes_receptionTime() != null && room.getRes_leavingTime() != null) {
	                service.updateRoomTimes(roomId, receptionTime);
	            } else {
	                service.updateRoomTimes(roomId, receptionTime, leavingTime);
	            }
	        }

	        // 酒類・状態更新.
	        RoomDao.updateAlcohol(roomId, alcohol);
	        RoomDao.updateStatus(roomId, statusId);

	        request.setAttribute("message", "更新できました");
	        request.getRequestDispatcher("/front/room_updated.jsp").forward(request, response);

	    } catch (IllegalStateException e) {
	        try {
	            request.setAttribute("message", e.getMessage());
	            request.getRequestDispatcher("/front/room_list.jsp").forward(request, response);
	        } catch (Exception ex) {
	            throw new ServletException(ex);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        try {
	            request.setAttribute("message", "更新に失敗しました: " + e.getMessage());
	            request.setAttribute("roomList", RoomDao.getAllRooms());
	            request.getRequestDispatcher("/front/room_list.jsp").forward(request, response);
	        } catch (Exception ex) {
	            throw new ServletException(ex);
	        }
	    }
	}
}
