package controller;

import java.io.IOException;
import java.time.LocalTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        Room room = (Room) session.getAttribute("room");
        
        if (room == null) {
            response.sendRedirect("cus_top.jsp"); // ルーム情報がない場合はトップへ
            return;
        }

        // 延長時間の初期値（選択肢の最小値など）
        int extendMinutes = 30;

        // 次の予約時間を取得（roomオブジェクトにある想定）
        LocalTime currentLeaving = room.getLeavingTime().toLocalTime(); // Timestampなら toLocalTime()
        LocalTime nextReception = room.getNextReceptionTime(); // nullの場合は次予約なし

        // 延長可否判定
        boolean canExtend = RoomTimeService.canExtend(currentLeaving, nextReception, extendMinutes);

        if (canExtend) {
            request.getRequestDispatcher("/time_extend.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/time_extend_refuse.jsp").forward(request, response);
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
