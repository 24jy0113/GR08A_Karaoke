package controller;

import java.io.IOException;
import java.sql.Time;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.ReservationListDao;

@WebServlet("/ResListUpdateServlet")
public class ResListUpdateServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            String[] reservationNums = req.getParameterValues("reservationNumber");
            String[] startTimes      = req.getParameterValues("startTime");
            String[] endTimes        = req.getParameterValues("endTime");
            String[] statusIds       = req.getParameterValues("statusId");

            if (reservationNums == null) {
                throw new ServletException("更新対象がありません");
            }

            ReservationListDao dao = new ReservationListDao();

            for (int i = 0; i < reservationNums.length; i++) {
                int reservationNumber = Integer.parseInt(reservationNums[i]);

                Time start = parseTime(startTimes[i]);
                Time end   = parseTime(endTimes[i]);
                int status = Integer.parseInt(statusIds[i]);

                dao.updateFrontOperation(
                    reservationNumber,
                    start,
                    end,
                    status
                );
            }

            // 更新後
            res.sendRedirect(req.getContextPath() + "/front/res_list_updated.jsp");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private Time parseTime(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        if (value.length() == 5) { // HH:mm
            value = value + ":00";
        }
        return Time.valueOf(value);
    }
}
