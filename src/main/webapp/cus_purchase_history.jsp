<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" % import="model.*"%>
<%
Room room = (Room) session.getAttribute("room");
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
%>
<%
Integer orderNo = (Integer) session.getAttribute("orderNo");
if (orderNo == null) {
	response.sendRedirect(request.getContextPath() + "/cus_top.jsp");
	return;
}
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>注文履歴一覧画面</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/05_01.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
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
					<li><a href="<%=request.getContextPath()%>/time_extend.jsp">延長申請</a></li>
					<li><a href="<%=request.getContextPath()%>/item_search.jsp">メニューを番号で探す</a></li>
					<li><a href="<%=request.getContextPath()%>/item_list.jsp">フード・ドリンク</a></li>
					<li><a href="<%= request.getContextPath() %>/cusPurchaseHistory">注文履歴</a></li>
					<li><a class="gnav_botton" href="cart_detail.jsp"> <img
							class="cart_img"
							src="<%=request.getContextPath()%>/img/cart.png" alt="cart"
							width="20" height="20">カート内容を確認
					</a></li>
				</ul>
			</nav>
		</div>
	</header>
	<main>
		<div class="bodymsg">
			<div class="msg">
				<h2>注文履歴一覧</h2>
				<h3>
					受取番号_<%=session.getAttribute("orderNo")%></h3>
				<table>
					<tr>
						<th></th>
						<th>単価</th>
						<th>オプション価格</th>
						<th>個数</th>
						<th>小計</th>
					</tr>

					<!-- 1行目 -->
					<tr>
						<td>サラダ</td>
						<td>300円（税込）</td>
						<td>-</td>
						<td>1</td>
						<td>300円（税込）</td>
					</tr>
					<tr>
						<th></th>
						<th>単価</th>
						<th>オプション価格</th>
						<th>個数</th>
						<th>小計</th>
					</tr>

					<!-- 2行目 -->
					<tr>
						<td>生ビール M</td>
						<td>300円（税込）</td>
						<td>＋30円</td>
						<td>2</td>
						<td>600円（税込）</td>
					</tr>
					<tr>
						<th></th>
						<th>単価</th>
						<th>オプション価格</th>
						<th>個数</th>
						<th>小計</th>
					</tr>

					<!-- 3行目 -->
					<tr>
						<td>チキン</td>
						<td>1000円（税込）</td>
						<td>-</td>
						<td>1</td>
						<td>1000円（税込）</td>
					</tr>
				</table>
				<h3>スタッフがお部屋までお届け</h3>
				<table>
					<tr>
						<th></th>
						<th>単価</th>
						<th>オプション価格</th>
						<th>個数</th>
						<th>小計</th>
					</tr>

					<!-- 1行目 -->
					<tr>
						<td>サラダ</td>
						<td>300円（税込）</td>
						<td>-</td>
						<td>1</td>
						<td>300円（税込）</td>
					</tr>
					<tr>
						<th></th>
						<th>単価</th>
						<th>オプション価格</th>
						<th>個数</th>
						<th>小計</th>
					</tr>

					<!-- 2行目 -->
					<tr>
						<td>生ビール M</td>
						<td>300円（税込）</td>
						<td>＋30円</td>
						<td>2</td>
						<td>600円（税込）</td>
					</tr>
					<tr>
						<th></th>
						<th>単価</th>
						<th>オプション価格</th>
						<th>個数</th>
						<th>小計</th>
					</tr>

					<!-- 3行目 -->
					<tr>
						<td>チキン</td>
						<td>1000円（税込）</td>
						<td>-</td>
						<td>1</td>
						<td>1000円（税込）</td>
					</tr>
				</table>
				<%
				for (Order o : orderList) {
				%>

				<%
				if (prevNo == null || !prevNo.equals(o.getReceivingNo())) {
				%>

				<h3>
					受取番号_<%=o.getReceivingNo()%></h3>

				<%
				if ("ROOM".equals(o.getPickupMethod())) {
				%>
				<h4>スタッフがお部屋までお届け</h4>
				<%
				} else {
				%>
				<h4>カウンター受取</h4>
				<%
				}
				%>

				<table>
					<tr>
						<th>商品名</th>
						<th>単価</th>
						<th>オプション価格</th>
						<th>個数</th>
						<th>小計</th>
					</tr>

					<%
					}
					%>

					<!-- 注文明細 -->
					<tr>
						<td>oi.getItem().getName()</td>
						<td><%= oi.getItem().getPrice() %>円(税込)</td>
						<td><%=oi.getItem().getOptionPrice() %>円</td>
						<td><%= oi.getCount() %></td>
						<td><%= oi.getTotal() %>円(税込)</td>
					</tr>

					<%
					prevNo = oi.getReceivingNo();
					%>

					<%
					}
					%>

				</table>
				<h4>合計3,920円（税込）＋ 室料他</h4>
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