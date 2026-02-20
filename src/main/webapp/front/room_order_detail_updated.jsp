<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.List,java.util.ArrayList,model.*"%>
<%
Order order = (Order) request.getAttribute("order");
Room room = (Room) request.getAttribute("room");

String statusName = "";
switch (order.getItemCreatingStatusId()) {
	case 1: statusName = "注文済み"; break;
	case 2: statusName = "調理済み"; break;
	case 3: statusName = "完了"; break;
	default: statusName = "不明"; break;
}
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>注文更新完了</title>
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
			<h2>注文の更新が完了しました</h2>
			<p class="bodymsg success-msg">以下の内容で更新されました。</p>

			<div class="updated-info">
				<p>部屋<%=room.getRoomNo()%> ／ 注文ID: <%=order.getId()%> ／ 状態: <%=statusName%></p>
			</div>

			<table class="order-detail-table">
				<tr>
					<th>商品名</th>
					<th>単価</th>
					<th>オプション</th>
					<th>個数</th>
					<th>小計</th>
				</tr>
				<%
				List<OrderItem> items = order.getItemList();
				if (items != null) {
					for (OrderItem oi : items) {
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
					<td><%=oi.getCount()%></td>
					<td><%=oi.getTotal()%>円</td>
				</tr>
				<%
					}
				}
				%>
			</table>

			<div class="order-summary-section">
				<p class="order-total-display">合計：<%=order.getTotal()%>円</p>
			</div>

			<div class="action-buttons flex-center">
				<button type="button" class="btn-back"
					onclick="location.href='<%=request.getContextPath()%>/RoomOrderListServlet?roomId=<%=room.getId()%>'">
					注文一覧へ戻る
				</button>
				<button type="button" class="btn-back"
					onclick="location.href='<%=request.getContextPath()%>/RoomListServlet'">
					部屋状況一覧へ戻る
				</button>
			</div>
		</div>
	</main>
</body>
</html>
