<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.*,java.util.*"%>
<%
Room room = (Room) session.getAttribute("room");
%>

<%
List<Order> orderList = (List<Order>) request.getAttribute("orderList");
if (orderList == null) {
	orderList = new java.util.ArrayList<>();
}
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>注文履歴一覧画面</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/05_01.css">
</head>
<body>
	<%@ include file="/shered/cus_header.jsp"%>
	<main>
		<div class="container-base">
			<div class="text-center">
				<h1 class="bodytitle">注文履歴一覧</h1>
				<%
				for (Order o : orderList) {
				%>
				<div class="order-card">
					<div class="order-header">
						<div class="order-no">
							<%
							Integer receivingNo = o.getReceivingNo();

							if (receivingNo == null || receivingNo == 0) {
							%>
							部屋までお届け
							<%
							} else {
							%>
							受取番号：<%=String.format("%04d", receivingNo)%>
							<%
							}
							%>

						</div>

						<div
							class="pickup <%="お部屋までお届け".equals(o.getPickupMethod()) ? "delivery" : "counter"%>">
							<%=o.getPickupMethod()%>
						</div>
					</div>
					<table class="order-table">
						<thead>
							<tr>
								<th>商品名</th>
								<th>単価</th>
								<th>オプション</th>
								<th>数量</th>
								<th>小計</th>
							</tr>
						</thead>
						<tbody>
							<%
							for (OrderItem oi : o.getItemList()) {
							%>
							<tr>
								<td><%=oi.getItemName()%></td>
								<td><%=oi.getItemPrice()%>円</td>
								<td>
									<%
									List<OrderItem.SelectedOptionDetail> opts = oi.getSelectedOptionDetails();

									if (opts == null || opts.isEmpty()) {
									%> なし <%
									} else {
									for (OrderItem.SelectedOptionDetail d : opts) {
									%> <%=d.selectionName()%>（<%=d.price()%>円）<br> <%
 }
 }
 %>
								</td>
								<td><%=oi.getCount()%></td>
								<td><%=oi.getTotal()%>円</td>
							</tr>
							<%
							}
							%>
						</tbody>
					</table>
					<div class="order-total">
						注文小計 <span><%=o.calculateTotal()%>円（税込）</span>
					</div>

				</div>
				<%
				}
				%>
				<div class="flex-center">
					<div class="grand-total">
						合計 <span><%=request.getAttribute("totalSum")%>円</span> <small>（税込）＋
							室料他</small>
					</div>
				</div>
				<div class="action-buttons flex-center">
					<button type="button" class="btn-back"
						onclick="location.href='<%=request.getContextPath()%>/cus_top.jsp'">トップページへ戻る</button>
				</div>
			</div>
		</div>
	</main>
	<%@ include file="/shered/cus_footer.jsp"%>
</body>
</html>