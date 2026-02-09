package controller;

import java.io.IOException;

import dao.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/KitchenOrderBack")
public class KitchenOrderBack extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));

            OrderDao dao = new OrderDao();
            dao.updateStatus(orderId, 1); // ← 注文済みに戻す

            response.sendRedirect(
                request.getContextPath() + "/KitchenOrderList");

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
