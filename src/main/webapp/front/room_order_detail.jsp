<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.List,java.util.ArrayList,model.*"%>
<%
Order order = (Order) request.getAttribute("order");
Room room = (Room) request.getAttribute("room");
String errorMsg = (String) request.getAttribute("errorMsg");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>注文詳細変更 - 注文ID <%=order.getId()%></title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/room_order.css">
</head>
<body>
	<%@ include file="/shared/biz_header.jsp"%>
	<main class="container-base">
		<div class="text-center">
			<h2>注文詳細変更</h2>
			<p class="bodymsg">
				部屋<%=room.getRoomNo()%> ／ 注文ID: <%=order.getId()%>
			</p>

			<%
			if (errorMsg != null) {
			%>
			<p class="errormsg"><%=errorMsg%></p>
			<%
			}
			%>

			<form action="<%=request.getContextPath()%>/RoomOrderDetailServlet" method="post">
				<input type="hidden" name="orderId" value="<%=order.getId()%>">
				<input type="hidden" name="roomId" value="<%=room.getId()%>">

				<table class="order-detail-table">
					<tr>
						<th>商品名</th>
						<th>単価(税込)</th>
						<th>オプション</th>
						<th>個数</th>
						<th>小計</th>
					</tr>
					<%
					int displayTotal = 0;
					List<OrderItem> items = order.getItemList();
					if (items != null) {
						for (OrderItem oi : items) {
							displayTotal += oi.getTotal();
					%>
					<tr>
						<td><%=oi.getItemName()%></td>
						<td><%=oi.getItemPrice()%>円</td>
						<td class="option-cell">
							<%
							List<OrderItem.SelectedOptionDetail> opts = oi.getSelectedOptionDetails();
							if (opts != null && !opts.isEmpty()) {
								for (OrderItem.SelectedOptionDetail d : opts) {
							%>
							<%=d.optName()%>：<%=d.selectionName()%>（<%=d.price()%>円）<br>
							<%
								}
							} else {
							%>
							なし
							<%
							}
							%>
						</td>
						<td>
							<input type="number" name="count_<%=oi.getId()%>"
								value="<%=oi.getCount()%>" min="1" class="count-input" required>
						</td>
						<td class="subtotal-cell"><%=oi.getTotal()%>円</td>
					</tr>
					<%
						}
					}
					%>
				</table>

				<div class="order-summary-section">
					<p class="order-total-display">合計：<%=displayTotal%>円（税込）</p>

					<div class="status-select-area">
						<label for="statusId">注文状態：</label>
						<select name="statusId" id="statusId">
							<option value="1" <%=order.getItemCreatingStatusId() == 1 ? "selected" : ""%>>注文済み</option>
							<option value="2" <%=order.getItemCreatingStatusId() == 2 ? "selected" : ""%>>調理済み</option>
							<option value="3" <%=order.getItemCreatingStatusId() == 3 ? "selected" : ""%>>完了</option>
						</select>
					</div>
				</div>

				
				<div class="action-buttons flex-center">
					<button type="button" class="btn-back"
						onclick="location.href='<%=request.getContextPath()%>/RoomOrderListServlet?roomId=<%=room.getId()%>'">
						注文一覧へ戻る
					</button>
					<button type="submit" class="btn-next">確定する</button>
				</div>
			</form>
		</div>
	</main>
</body>
</html>
