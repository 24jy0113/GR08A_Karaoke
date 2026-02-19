package controller;

import dao.OrderDao;
import dao.RoomDao;
import model.Order;
import model.OrderItem;
import model.Room;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/RoomOrderDetailServlet")
public class RoomOrderDetailServlet extends HttpServlet {

    // GET: 注文詳細の編集画面を表示
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        int roomId  = Integer.parseInt(req.getParameter("roomId"));

        try {
            OrderDao orderDao = new OrderDao();
            RoomDao roomDao = new RoomDao();

            Room room = roomDao.getRoomById(roomId);

            // 注文の基本情報を取得
            int currentStatusId = orderDao.getItemCreatingStatus(orderId);

            // 注文明細を取得
            List<OrderItem> detailList = orderDao.findOrderItemsByOrderId(orderId);

            // 合計を算出
            int total = 0;
            for (OrderItem oi : detailList) {
                total += oi.getTotal();
            }

            Order order = new Order();
            order.setId(orderId);
            order.setItemCreatingStatusId(currentStatusId);
            order.setItemList(new ArrayList<>(detailList));
            order.setTotal(total);
            order.setRoomId(roomId);

            req.setAttribute("order", order);
            req.setAttribute("room", room);
            req.getRequestDispatcher("/front/room_order_detail.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMsg", "注文詳細の取得に失敗しました。");
            // エラー時は一覧に戻す
            res.sendRedirect("RoomOrderListServlet?roomId=" + roomId);
        }
    }

    // POST: 個数と状態を更新してDBに反映
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        int roomId  = Integer.parseInt(req.getParameter("roomId"));
        int newStatusId = Integer.parseInt(req.getParameter("statusId"));

        try {
            OrderDao orderDao = new OrderDao();

            // 現在の明細を取得（item_price とオプション価格の計算に必要）
            List<OrderItem> detailList = orderDao.findOrderItemsByOrderId(orderId);

            int newTotal = 0;

            for (OrderItem oi : detailList) {
                String countParam = req.getParameter("count_" + oi.getId());
                if (countParam != null) {
                    int newCount = Integer.parseInt(countParam);
                    oi.setCount(newCount);

                    // 小計を再計算: (item_price + オプション合計) × 個数
                    int optionSum = 0;
                    if (oi.getSelectedOptionDetails() != null) {
                        for (OrderItem.SelectedOptionDetail d : oi.getSelectedOptionDetails()) {
                            optionSum += d.price();
                        }
                    }
                    int subTotal = (oi.getItemPrice() + optionSum) * newCount;
                    oi.setTotal(subTotal);
                }
                newTotal += oi.getTotal();
            }

            // DB更新
            orderDao.updateOrderDetail(detailList);
            orderDao.updateOrderTotalAndStatus(orderId, newTotal, newStatusId);

            // 更新後の情報をリクエストにセット
            RoomDao roomDao = new RoomDao();
            Room room = roomDao.getRoomById(roomId);

            Order updatedOrder = new Order();
            updatedOrder.setId(orderId);
            updatedOrder.setItemCreatingStatusId(newStatusId);
            updatedOrder.setItemList(new ArrayList<>(detailList));
            updatedOrder.setTotal(newTotal);
            updatedOrder.setRoomId(roomId);

            req.setAttribute("order", updatedOrder);
            req.setAttribute("room", room);
            req.getRequestDispatcher("/front/room_order_detail_updated.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMsg", "注文の更新に失敗しました：" + e.getMessage());
            // GET で再表示
            doGet(req, res);
        }
    }
}
