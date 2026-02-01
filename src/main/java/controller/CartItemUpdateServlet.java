package controller;

import model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
@WebServlet("/CartItemUpdateServlet")
public class CartItemUpdateServlet extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		HttpSession session = req.getSession();
		ArrayList<OrderItem> cart =
			(ArrayList<OrderItem>) session.getAttribute("cart");

		int index = Integer.parseInt(req.getParameter("index"));
		OrderItem target = cart.get(index);
		Item item = target.getItem();

		// ① option 上書き.
		for (Option opt : item.getOptionList()) {
			String param = req.getParameter("opt_" + opt.getId());
			int selectionId = Integer.parseInt(param);
			target.setSelectedOption(opt.getId(), selectionId);
		}

		// ② count 上書き.
		int count = Integer.parseInt(req.getParameter("count"));
		target.setCount(count);

		// ③ cart 内合并（自分以外と比較）.
		for (int i = 0; i < cart.size(); i++) {
			if (i == index) continue;

			OrderItem other = cart.get(i);
			if (other.isSameItemAndOption(target)) {
				other.setCount(other.getCount() + target.getCount());
				cart.remove(index);
				break;
			}
		}

		session.setAttribute("cart", cart);
		res.sendRedirect("cart_detail.jsp");
	}
}

