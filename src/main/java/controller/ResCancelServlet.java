package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.ReservationListDao;

@WebServlet("/ResCancelServlet")
public class ResCancelServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            int reservationNumber = Integer.parseInt(req.getParameter("reservationNumber"));
            ReservationListDao dao = new ReservationListDao();
            dao.cancelReservation(reservationNumber);
            res.sendRedirect(req.getContextPath() + "/ResListFrontServlet");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}