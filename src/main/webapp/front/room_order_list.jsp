<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.List,java.util.ArrayList,model.*"%>
<%
Room room = (Room) request.getAttribute("room");
List<Order> orderList = (List<Order>) request.getAttribute("orderList");
if (orderList == null) orderList = new ArrayList<>();
String errorMsg = (String) request.getAttribute("errorMsg");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>注文一覧 - 部屋<%=room != null ? room.getRoomNo() : ""%></title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/room_order.css">
</head>
<body>
	<%@ include file="/shered/biz_header.jsp"%>
	<main class="container-base">
		<div class="text-center">
			<h2>部屋<%=room != null ? room.getRoomNo() : ""%> の注文一覧</h2>

			<%
			if (errorMsg != null) {
			%>
			<p class="errormsg"><%=errorMsg%></p>
			<%
			}
			%>

			<%
			if (orderList.isEmpty()) {
			%>
			<p class="bodymsg">この部屋には未精算の注文がありません。</p>
			<%
			} else {
				for (Order order : orderList) {
					String statusName = "";
					switch (order.getItemCreatingStatusId()) {
						case 1: statusName = "注文済み"; break;
						case 2: statusName = "調理済み"; break;
						case 3: statusName = "完了"; break;
						default: statusName = "不明"; break;
					}
			%>
			<div class="order-card">
				<div class="order-card-header">
					<span class="order-id">注文ID: <%=order.getId()%></span>
					<%
					if (order.getReceivingNo() != null && order.getReceivingNo() > 0) {
					%>
					<span class="receiving-no">受取番号: <%=order.getReceivingNo()%></span>
					<%
					}
					%>
					<span class="order-status status-<%=order.getItemCreatingStatusId()%>"><%=statusName%></span>
					<span class="pickup-method"><%=order.getPickupMethod() != null ? order.getPickupMethod() : ""%></span>
				</div>

				<table class="order-items-table">
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

				<div class="order-card-footer">
					<span class="order-total">合計：<%=order.getTotal()%>円</span>
					<button type="button" class="btn"
						onclick="location.href='<%=request.getContextPath()%>/RoomOrderDetailServlet?orderId=<%=order.getId()%>&roomId=<%=room.getId()%>'">
						この注文を変更する
					</button>
				</div>
			</div>
			<%
				}
			}
			%>

			<div class="action-buttons flex-center">
				<button type="button" class="btn-back"
					onclick="location.href='<%=request.getContextPath()%>/RoomListServlet'">
					部屋状況一覧へ戻る
				</button>
			</div>
		</div>
	</main>
</body>
</html>
