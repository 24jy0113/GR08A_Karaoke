package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Order;
import model.OrderItem;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/CartDetailServlet")
public class CartDetailServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        ArrayList<OrderItem> cart =
            (ArrayList<OrderItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            res.sendRedirect(req.getContextPath() + "/cus_top.jsp");
            return;
        }
        
        Order order = new Order(cart);
        session.setAttribute("order",order);
        
        req.getRequestDispatcher("/cart_detail_confirm.jsp")
           .forward(req, res);
    }
}
