package controller;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import dao.ReservationListDao;
import model.*;
import util.CsvUtil;
import jakarta.servlet.annotation.MultipartConfig;

@WebServlet("/ResMsgUploadServlet")
@MultipartConfig//getPart() を使っているので 必須.
public class ResMsgUploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            Part filePart = req.getPart("csvFile");

            List<CsvReservationRow> rows =
                CsvUtil.parse(filePart.getInputStream());

            ReservationListDao dao = new ReservationListDao();
            dao.importFromCsv(rows);
            
            req.getRequestDispatcher("/admin/res_msg_uploaded.jsp")
               .forward(req, res);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

