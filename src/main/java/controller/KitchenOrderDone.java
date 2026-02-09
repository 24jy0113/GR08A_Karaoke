package controller;

import java.io.IOException;
import java.util.List;

import dao.OrderDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Order;

@WebServlet("/KitchenOrderDone")
public class KitchenOrderDone extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

    	try {
            OrderDao dao = new OrderDao();

            //調理済み（status = 2）
            List<Order> orderList = dao.findCookingFinishedList();

            //商品 + option
            for (Order o : orderList) {
                o.setItemList(
                    dao.findOrderItemsByOrderId(o.getId())
                );
            }

            req.setAttribute("orderList", orderList);

            RequestDispatcher rd =
                    req.getRequestDispatcher("/kitchen/kitchen_order_list.jsp");
            rd.forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
