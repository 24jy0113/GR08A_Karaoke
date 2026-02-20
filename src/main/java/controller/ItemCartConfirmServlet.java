package controller;

import model.OrderItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/ItemCartConfirmServlet")
public class ItemCartConfirmServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();

        OrderItem oi = (OrderItem) session.getAttribute("buildingItem");

        // count を反映（setCount 内で calcTotal も呼ばれる）
        int count = Integer.parseInt(req.getParameter("count"));
        oi.setCount(count);

        session.setAttribute("buildingItem", oi);

        req.getRequestDispatcher("/item_cart_confirm.jsp").forward(req, res);
    }
}
