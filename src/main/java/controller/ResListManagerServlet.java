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

@WebServlet("/ResListManagerServlet")
public class ResListManagerServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

    	try {
    		String statusIdStr = req.getParameter("statusId");

            List<ReservationView> list;

            if (statusIdStr == null || statusIdStr.isEmpty()) {
                list = ReservationListDao.findAll();
            } else {
                int statusId = Integer.parseInt(statusIdStr);
                list = ReservationListDao.findByStatus(statusId);
            }

            req.setAttribute("reservationList", list);
            req.getRequestDispatcher("/admin/res_list_manager.jsp")
                   .forward(req, res);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
