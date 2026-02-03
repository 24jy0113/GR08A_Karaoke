package controller;

import model.OrderItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/CartRemoveServlet")
public class CartRemoveServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		int index = Integer.parseInt(req.getParameter("index"));
		ArrayList<OrderItem> cart =
			(ArrayList<OrderItem>) req.getSession().getAttribute("cart");

		if (cart != null && index >= 0 && index < cart.size()) {
			cart.remove(index);
		}

		res.sendRedirect("cart_detail.jsp");
	}
}

