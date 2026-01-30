package controller;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.ItemDao;
import model.Item;


@WebServlet("/item_detail")
public class ItemDetailServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // ① パラメータ取得.
            int itemId = Integer.parseInt(request.getParameter("id"));

            // ② DAO呼び出し.
            ItemDao dao = new ItemDao();
            Item item = dao.searchItemById(itemId);


            if (item == null) {
                request.setAttribute("error", "商品が見つかりません");
                request.getRequestDispatcher("/error.jsp")
                       .forward(request, response);
                return;
            }

            request.setAttribute("item", item);
            request.getRequestDispatcher("/item_detail.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
