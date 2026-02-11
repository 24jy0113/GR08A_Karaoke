package controller;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Room;
import dao.*;
@WebServlet("/CusTopServlet")
public class CusTopServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String roomIdStr = req.getParameter("roomId");
        if (roomIdStr == null) {
            res.sendRedirect(req.getContextPath() + "/RoomListServlet");
            return;
        }

        int roomId = Integer.parseInt(roomIdStr);

        try {
            RoomDao dao = new RoomDao();
            Room room = dao.getRoomById(roomId);

            if (room == null) {
                res.sendRedirect(req.getContextPath() + "/RoomListServlet");
                return;
            }
            req.getSession().setAttribute("room", room);
            req.getRequestDispatcher("/cus_top.jsp").forward(req, res);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

