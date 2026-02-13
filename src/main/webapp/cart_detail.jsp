<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.ArrayList,model.*"%>
<%
Room room = (Room) session.getAttribute("room");
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
%>
<%
ArrayList<OrderItem> cart = (ArrayList<OrderItem>) session.getAttribute("cart");
if (cart == null || cart.isEmpty()) {
	cart = new ArrayList<>();
}
int totalSum = 0;
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>カート内容</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
	<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/07_01.css">

</head>
<body>
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
					<li><a class="gnav_botton"
						href="<%=request.getContextPath()%>/cart_detail.jsp"> <img
							class="cart_img" src="<%=request.getContextPath()%>/img/cart.png"
							alt="cart" width="20" height="20">カート内容を確認
					</a></li>
				</ul>


			</nav>
		</div>
	</header>
	<main>
		<div class="text-center">
			<div class="msg">
				<%
				if (cart.isEmpty()) {
				%>
				<h2>カート内容</h2>
				<p class="errermsg">カートの中身は空です。</p>
				<button type="button" class="btn-back"
					onclick="location.href='<%=request.getContextPath()%>/cus_top.jsp'">
					トップへ戻る</button>
				<%
				} else {
				%>
				<h2>カート内容</h2>
				<p>カートの内容を確認し、注文へ進む場合は「注文へ進む」を押してください</p>
				<form action="CartDetailServlet" method="post">
					<table>
						<%
						for (int i = 0; i < cart.size(); i++) {
							OrderItem oi = cart.get(i);
							totalSum += oi.getTotal();
						%>
						<tr>
							<th><%=oi.getItem().getName()%></th>
							<th>個数</th>
							<th>小計</th>
							<th><a href="item_detail_change.jsp?index=<%=i%>">
									個数・オプションを変更する </a></th>
						</tr>
						<tr>
							<td><%=oi.getItem().getPrice()%>円(税込)</td>
							<td><%=oi.getCount()%></td>
							<td><%=oi.getTotal()%>円(税込)</td>
						</tr>
						<tr>
							<td><a href="CartRemoveServlet?index=<%=i%>">削除する</a></td>
						</tr>
						<%
						}
						%>
					</table>
					<p>
						商品合計金額：<%=totalSum%>円(税込)
					</p>
					<div class="action-buttons flex-center">
						<button type="button" class="btn-back" onclick="history.back()">戻る</button>
						<button type="submit" class="btn-next">注文へ進む</button>
					</div>
				</form>
				<%
				}
				%>
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