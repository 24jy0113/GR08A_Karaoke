<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.Item,model.Room, model.Option"%>
<%
Item item = (Item) request.getAttribute("item");
%>
<%
Room room = (Room) session.getAttribute("room");
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
%>
<!DOCTYPE html>
<html lang=ja>
<head>
<meta charset="UTF-8">
<title>商品詳細情報画面</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/06_03.css">
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
					<li><a class="gnav_button" href="cart_detail.jsp"> <img
							class="cart_img" src="<%=request.getContextPath()%>/img/cart.png"
							alt="cart" width="20" height="20">カート内容を確認
					</a></li>
				</ul>
			</nav>
		</div>
	</header>
	<main>
		<div class="text-center">
			<%
			String error = (String) request.getAttribute("error");
			%>
			<%
			if (error != null) {
			%>
			<p class="errermsg"><%=error%></p>
			<%
			}
			%>
			<br>

			<div class="container">
				<!-- 左側：画像領域 -->
				<div class="left-box">
					<img class="item-img"
						src="<%=request.getContextPath()%>/img/<%=item.getImage()%>"
						alt="product">
				</div>
				<!-- 右側：検索欄＋テンキー -->
				<div class="right-box">
					<h1 class="item-name"><%=item.getName()%></h1>
					<h2 class="item-price"><%=item.getPrice()%>
						円(税込)
					</h2>
					<div class="pad">
						<%
						if (!item.isStock()) {
						%>
						<h3 class="errermsg">この商品は現在売切れです</h3>
						<!-- 押せないボタン -->
						<button type="button" class="btn-next is-disabled" disabled>カートに入れる</button>
						<%
						} else {
						%>
						<!-- 在庫あり：通常ボタン -->
						<form action="ItemOptionServlet" method="get">
							<input type="hidden" name="itemId" value="<%=item.getId()%>">
							<button type="submit" class="btn-next">カートに入れる</button>
						</form>
						<%
						}
						%>
						<button type="button" class="btn-back" onclick="history.back()">メニュー一覧に戻る</button>
						<button type="button" class="btn-back" onclick="location.href='item_search.jsp'">メニューを番号で探す</button>
					</div>
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