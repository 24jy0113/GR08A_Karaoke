package controller;
import dao.ItemDao;
import model.Item;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/item_list")
public class ItemListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        Integer categoryId = null;
        int page = 1;
        int pageSize = 6;

        // カテゴリ
        String catParam = req.getParameter("category");
        if (catParam != null && !catParam.isEmpty()) {
            categoryId = Integer.parseInt(catParam);
        }

        // ページ
        String pageParam = req.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            page = Integer.parseInt(pageParam);
        }

        int offset = (page - 1) * pageSize;

        ItemDao dao = new ItemDao();

        try {
            int totalCount = dao.getItemCount(categoryId);
            int totalPages = (int) Math.ceil((double) totalCount / pageSize);

            ArrayList<Item> itemList =
                dao.getItemListByPage(categoryId, offset, pageSize);

            req.setAttribute("itemList", itemList);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("categoryId", categoryId);

            req.getRequestDispatcher("/item_list.jsp")
                   .forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}