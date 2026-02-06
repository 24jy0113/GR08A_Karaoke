package controller;

import dao.ItemDao;
import model.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
@WebServlet("/ItemSearchServlet")
public class ItemSearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
        	String numStr = req.getParameter("orderNumber");

            if (numStr == null || numStr.isEmpty()) {
                req.setAttribute("error", "メニュー番号を入力してください");
                req.getRequestDispatcher("/item_search.jsp")
                       .forward(req, res);
                return;
            }

            int orderNumber = Integer.parseInt(numStr);

            // ② DAO で検索
            ItemDao dao = new ItemDao();
            Item item = dao.searchItemByNumber(orderNumber);

            // ③ 見つからない場合
            if (item == null) {
                req.setAttribute("error", "該当する商品がありません");
                req.getRequestDispatcher("/item_search.jsp")
                       .forward(req, res);
                return;
            }

            // ④ 売り切れ判定（boolean 運用）
            if (!item.isStock()) {
                req.setAttribute("error", "こちらの商品は売り切れです");
                req.getRequestDispatcher("/item_search.jsp")
                       .forward(req, res);
                return;
            }

            // ⑤ 商品を次画面へ
            req.setAttribute("item", item);
            req.getRequestDispatcher("/item_detail.jsp")
                   .forward(req, res);

        } catch (NumberFormatException e) {
            req.setAttribute("error", "番号は数字で入力してください");
            req.getRequestDispatcher("/item_search.jsp")
                   .forward(req, res);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

