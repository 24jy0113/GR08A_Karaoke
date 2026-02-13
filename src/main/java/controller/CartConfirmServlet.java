package controller;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/CartConfirmServlet")
public class CartConfirmServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        // フラッシュメッセージ（1回だけ表示）.
        session.setAttribute("cartMessage", "カートに入れました");

        res.sendRedirect(req.getContextPath() + "/cus_top.jsp");
    }
}
