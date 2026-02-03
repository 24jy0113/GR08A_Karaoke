package controller;

import model.OrderItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet("/CartAddServlet")
public class CartAddServlet extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		OrderItem oi = (OrderItem) session.getAttribute("buildingItem");

		int count = Integer.parseInt(req.getParameter("count"));
		oi.setCount(count);

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

		req.getRequestDispatcher("/item_cart_confirm.jsp").forward(req, res);
	}
}

