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

@WebServlet("/KitchenOrderFinished")
public class KitchenOrderFinished extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            OrderDao dao = new OrderDao();
            List<Order> orderList = dao.findCookingFinishedList();

            request.setAttribute("orderList", orderList);

            RequestDispatcher rd =
                request.getRequestDispatcher("/kitchen/kitchen_order_finished.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
