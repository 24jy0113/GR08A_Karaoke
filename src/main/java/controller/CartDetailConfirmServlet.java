package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.*;
import dao.OrderDao;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/CartDetailConfirmServlet")
public class CartDetailConfirmServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        @SuppressWarnings("unchecked")
        ArrayList<OrderItem> cart =
            (ArrayList<OrderItem>) session.getAttribute("cart");
        

        if (cart == null || cart.isEmpty()) {
            res.sendRedirect(req.getContextPath() + "/cus_top.jsp");
            return;
        }

        // 受取方法
        String pickupMethod = req.getParameter("pickupMethod");

        // 部屋情報
        Room room = (Room) session.getAttribute("room");

        // ① OrderItem を「注文確定状態」に凍結
        int total = 0;
        for (OrderItem oi : cart) {
            if (oi.hasOptionUnselected()) {
                req.setAttribute("errorMsg", "未選択のオプションがあります");
                req.getRequestDispatcher("/cart_detail_confirm.jsp")
                   .forward(req, res);
                return;
            }
            oi.freezeFromItem();
            total += oi.getTotal();
        }

        // ② Order 作成
        Order order = new Order();
        order.setItemList(cart);
        order.setTotal(total);
        order.setRoomId(room.getId());
        order.setReceivingNo(generateReceivingNo());
        order.setPickupMethod(pickupMethod);
        order.setItemCreatingStatusId(1); 

        // ③ DB登録（トランザクション）
        try {
            OrderDao orderDao = new OrderDao();
            int orderId = orderDao.insertOrder(order);

            // セッション整理
            session.setAttribute("orderNo", order.getReceivingNo());
            session.removeAttribute("cart");

            res.sendRedirect(req.getContextPath() + "/cart_order_finished.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMsg", e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }

    // 受取番号生成
    private int generateReceivingNo() {
        return (int)(System.currentTimeMillis() % 100000);
    }
}
