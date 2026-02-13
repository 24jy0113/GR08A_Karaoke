package controller;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.ItemDao;
import model.Item;
import model.Room;

@WebServlet("/item_list")
public class ItemListServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		int pageSize = 6;
		int page = 1;

		Room room = (Room) req.getSession().getAttribute("room");

		// ▼ category（デフォルト1）
		String catParam = req.getParameter("category");
		Integer selCategoryId = (catParam == null || catParam.isEmpty()) ? null : Integer.parseInt(catParam);

		// ▼ page（カテゴリ変更時は1）
		String pageParam = req.getParameter("page");
		if (pageParam != null && !pageParam.isEmpty()) {
			page = Integer.parseInt(pageParam);
		}

		int offset = (page - 1) * pageSize;

		ItemDao dao = new ItemDao();

		try {
			int totalCount = dao.getItemCount(selCategoryId);
			int totalPages = Math.max(1,
					(int) Math.ceil((double) totalCount / pageSize));

			ArrayList<Item> itemList = dao.getItemListByPage(selCategoryId, offset, pageSize, room.isAlcohol());
			var categoryMap = dao.getCategoryList();
			if(!room.isAlcohol()) {
				categoryMap.remove(1);
			}

			req.setAttribute("itemList", itemList);
			req.setAttribute("currentPage", page);
			req.setAttribute("totalPages", totalPages);
			req.setAttribute("categoryId", selCategoryId);
			req.setAttribute("room", room);
			req.setAttribute("categoryMap", categoryMap);

			req.getRequestDispatcher("/item_list.jsp").forward(req, res);

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
