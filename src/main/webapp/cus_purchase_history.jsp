<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.*,java.util.*"%>
<%
Room room = (Room) session.getAttribute("room");
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
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
	<!-- Header -->
	<header>
		<div class="header_inner">
			<img class="title_img"
				src="<%=request.getContextPath()%>/img/logo.png" alt="logo"
				width="60" height="60">
			<h1 class="title_name">七福サウンド</h1>
			<nav class="gnav">
				<ul class="gnav_list">
					<li><a href="<%=request.getContextPath()%>/cus_top.jsp">トップへ</a></li>
					<li><a href="<%=request.getContextPath()%>/ExtendCanServlet">延長申請</a></li>
					<li><a href="<%=request.getContextPath()%>/item_search.jsp">メニューを番号で探す</a></li>
					<li><a href="<%=request.getContextPath()%>/item_list.jsp">フード・ドリンク</a></li>
					<li><a href="<%=request.getContextPath()%>/cusPurchaseHistory">注文履歴</a></li>
					<li><a class="gnav_botton" href="cart_detail.jsp"> <img
							class="cart_img" src="<%=request.getContextPath()%>/img/cart.png"
							alt="cart" width="20" height="20">カート内容を確認
					</a></li>
				</ul>
			</nav>
		</div>
	</header>
	<main>
		<div class="container">
			<div class="text-center">
				<h1 class="bodytitle">注文履歴一覧</h1>
				<%
				for (Order o : orderList) {
				%>
				<div class="order-card">
					<div class="order-header">
						<div class="order-no">
							受取番号_<%=o.getReceivingNo()%></div>
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
		<div class="footer-wrap">
			<%
			if (room != null) {
			%>
			<h1>
				部屋番号
				<%=room.getRoomNo()%></h1>
			<%
			}
			%>
			<%
			if (remainingMinutes != null) {
			%>
			<h1>
				残り時間
				<%=remainingMinutes%>
				分
			</h1>
			<%
			}
			%>
		</div>
	</main>

</body>
</html>