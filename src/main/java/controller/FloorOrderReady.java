package controller;


import java.io.IOException;
import java.util.List;

import dao.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Order;

@WebServlet("/FloorOrderReady")
public class FloorOrderReady extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            OrderDao dao = new OrderDao();
            List<Order> orderList = dao.findByStatus(2);

            for (Order o : orderList) {
                o.setItemList(
                    dao.findOrderItemsByOrderId(o.getId())
                );
            }

            req.setAttribute("orderList", orderList);

            req.getRequestDispatcher(
                "/floor/floor_order_ready.jsp"
            ).forward(req, res);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}


