package controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
@WebServlet("/CartConfirmServlet")
public class CartConfirmServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        // フラッシュメッセージ（1回だけ表示）.
        session.setAttribute("cartMessage", "カートに入れました");

        res.sendRedirect(req.getContextPath() + "/cart_detail.jsp");
    }
}
