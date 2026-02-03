package controller;

import dao.ItemDao;
import model.Item;
import model.OrderItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;


@WebServlet("/ItemOptionServlet")
public class ItemOptionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        int itemId = Integer.parseInt(req.getParameter("itemId"));
        ItemDao itemDao = new ItemDao();
        Item item;
		try {
			item = itemDao.searchItemById(itemId);
		} catch (Exception e) {
			throw new ServletException(e);
		}

     OrderItem oi = new OrderItem(item);
     HttpSession session = req.getSession();
     session.setAttribute("buildingItem", oi);

     req.setAttribute("item", item);
     req.getRequestDispatcher("/item_option_pick.jsp").forward(req, res);

    }
}