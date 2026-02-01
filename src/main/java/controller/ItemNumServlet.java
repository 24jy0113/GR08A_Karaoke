package controller;

import model.Item;
import model.OrderItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.Option;


@WebServlet("/ItemNumServlet")
public class ItemNumServlet extends HttpServlet {
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		OrderItem oi = (OrderItem) session.getAttribute("buildingItem");

		Item item = oi.getItem();

		for (Option opt : item.getOptionList()) {
			String param = req.getParameter("opt_" + opt.getId());
			int selectionId = Integer.parseInt(param);
			oi.setSelectedOption(opt.getId(), selectionId);
		}
		
		// 表示用データは request.
        req.setAttribute("item", item);
        
		session.setAttribute("buildingItem", oi);
		req.getRequestDispatcher("/item_num_pick.jsp").forward(req, res);
	}
}
