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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/05_01.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/header.css">
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
					<li><a
						href="<%=request.getContextPath()%>/cusPurchaseHistory">注文履歴</a></li>
					<li><a class="gnav_botton" href="cart_detail.jsp"> <img
							class="cart_img" src="<%=request.getContextPath()%>/img/cart.png"
							alt="cart" width="20" height="20">カート内容を確認
					</a></li>
				</ul>
			</nav>
		</div>
	</header>
	<main>
		<div class="bodymsg">
			<div class="msg">
				<h2>注文履歴一覧</h2>
					<%
					for (Order o : orderList) {
					%>
					
					<h3>受取番号_<%=o.getReceivingNo()%></h3>
					
					<h4>
					<%= "お部屋までお届け".equals(o.getPickupMethod())
					    ? "お部屋までお届け"
					    : "カウンター受取" %>
					</h4>

					<table>
					<tr>
					    <th>商品名</th>
					    <th>単価</th>
					    <th>オプション</th>
					    <th>個数</th>
					    <th>小計</th>
					</tr>
					
					<%
					for (OrderItem oi : o.getItemList()) {
					%>
					<tr>
					    <td><%=oi.getItemName()%></td>
					    <td><%=oi.getItemPrice()%>円</td>
					
					    <td>
					    <%
					        List<OrderItem.SelectedOptionDetail> opts =
					            oi.getSelectedOptionDetails();
					
					        if (opts == null || opts.isEmpty()) {
					    %>
					        なし
					    <%
					        } else {
					            for (OrderItem.SelectedOptionDetail d : opts) {
					    %>
					        <%=d.selectionName()%>（<%=d.price()%>円）<br>
					    <%
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
					</table>
					
					<h4>注文小計：<%=o.calculateTotal()%>円（税込）</h4>
					<hr>
					
					<%
					}
					%>
					
					<h4>
					合計 <%=request.getAttribute("totalSum")%> 円（税込）＋ 室料他
					</h4>
				<div class="action-buttons">

					<button type="button" class="btn-back" onclick="history.back()">トップページへ戻る</button>
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