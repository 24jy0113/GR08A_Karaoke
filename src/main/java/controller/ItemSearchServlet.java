package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.ItemDao;
import model.Item;
import model.Room;
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

            // DAO で検索
            ItemDao dao = new ItemDao();
            Item item = dao.searchItemByNumber(orderNumber);

            // 見つからない場合
            if (item == null) {
                req.setAttribute("error", "該当する商品がありません");
                req.getRequestDispatcher("/item_search.jsp")
                       .forward(req, res);
                return;
            }
            
            var session = req.getSession();
            var room=(Room)(session.getAttribute("room"));
            
            if (item.getCategoryId()==1&&!room.isAlcohol()) {
                req.setAttribute("error", "20歳未満へのお酒の販売は致しません。");
                req.getRequestDispatcher("/item_search.jsp")
                       .forward(req, res);
                return;            	
            }

            // 売り切れ判定
            if (!item.isStock()) {
                req.setAttribute("item", item);
                req.setAttribute("error", "売り切れのため注文できません");
                req.getRequestDispatcher("/item_detail.jsp").forward(req, res);
                return;
            }

            // 商品を次画面へ
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

