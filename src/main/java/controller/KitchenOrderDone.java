package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.OrderDao;

@WebServlet("/KitchenOrderDone")
public class KitchenOrderDone extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		try {
			int orderId = Integer.parseInt(req.getParameter("orderId"));

			OrderDao dao = new OrderDao();

			// 注文済み(1) → 調理済み(2)
			dao.updateStatus(orderId, 2);

			var order = dao.searchOrderById(orderId);
			var receivingNo = order.getReceivingNo();
			if (receivingNo != null && receivingNo > 0) {
				String roomId = String.valueOf(order.getRoomId());
				String messageId = "msg_" + System.currentTimeMillis();
				String payload = "ご注文の商品ができあがりました！<br>"
						+ "フロントにてお渡しいたしますのでお越しください<br>"
						+ "受取番号：" + String.valueOf(receivingNo);

				// 通知を追加
				SseNotificationServlet.sendWithRetry(roomId, messageId, payload);
			}

			res.sendRedirect(
					req.getContextPath() + "/KitchenOrderList");

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
