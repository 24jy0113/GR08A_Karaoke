package controller;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.ReservationListDao;
import model.ReservationView;

@WebServlet("/ResListFrontServlet")
public class ResListFrontServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

    	try {
    		String roomNoStr = req.getParameter("room_num");

            List<ReservationView> list;

            if (roomNoStr == null || roomNoStr.isEmpty()) {
                list = ReservationListDao.findAll();
            } else {
                int roomNo = Integer.parseInt(roomNoStr);
                list = ReservationListDao.findByRoom(roomNo);
            }
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getStatusName() == null)
					list.get(i).setStatusName("予約");
			}

            req.setAttribute("reservationList", list);
            req.getRequestDispatcher("/front/res_list_front.jsp")
                   .forward(req, res);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}