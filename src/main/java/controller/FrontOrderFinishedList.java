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
@WebServlet("/FrontOrderFinishedList")
public class FrontOrderFinishedList extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            OrderDao dao = new OrderDao();
            List<Order> orderList = dao.findByStatus(3);

         // ② 各注文に明細を詰める
            for (Order o : orderList) {
                o.setItemList(
                    dao.findOrderItemsByOrderId(o.getId())
                );
            }

            req.setAttribute("orderList", orderList);

            req.getRequestDispatcher(
                "/front/front_order_finished_list.jsp"
            ).forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
