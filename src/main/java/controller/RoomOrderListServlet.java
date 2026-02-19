package controller;

import dao.OrderDao;
import dao.RoomDao;
import model.Order;
import model.Room;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/RoomOrderListServlet")
public class RoomOrderListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        int roomId = Integer.parseInt(req.getParameter("roomId"));

        try {
            RoomDao roomDao = new RoomDao();
            Room room = roomDao.getRoomById(roomId);

            OrderDao orderDao = new OrderDao();
            // usage_history_id IS NULL の未精算注文を取得
            List<Order> orderList = orderDao.findActiveOrdersByRoom(roomId);

            // 各注文の明細を取得
            for (Order order : orderList) {
                order.setItemList(
                    new java.util.ArrayList<>(orderDao.findOrderItemsByOrderId(order.getId()))
                );
            }

            req.setAttribute("room", room);
            req.setAttribute("orderList", orderList);
            req.getRequestDispatcher("/front/room_order_list.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMsg", "注文情報の取得に失敗しました。");
            req.getRequestDispatcher("/front/room_order_list.jsp").forward(req, res);
        }
    }
}
