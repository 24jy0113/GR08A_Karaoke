package controller;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.OrderItem;
@WebServlet("/CartAddServlet")
public class CartAddServlet extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");

		HttpSession session = req.getSession();
		OrderItem oi = (OrderItem) session.getAttribute("buildingItem");


		ArrayList<OrderItem> cart = (ArrayList<OrderItem>) session.getAttribute("cart");
		if (cart == null) {
			cart = new ArrayList<>();
		}

		boolean merged = false;
		for (OrderItem ci : cart) {
			if (ci.isSameItemAndOption(oi)) {
				ci.setCount(ci.getCount() + oi.getCount());
				merged = true;
				break;
			}
		}

		if (!merged) {
			cart.add(oi);
		}

		session.setAttribute("cart", cart);
		session.setAttribute("buildingItem", oi);

		req.getRequestDispatcher("/cart_detail.jsp").forward(req, res);
	}
}

