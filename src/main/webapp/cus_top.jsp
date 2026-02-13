<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.User,model.Room"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%
User user = (User) session.getAttribute("loginUser");
%>
<%
Room room = (Room) session.getAttribute("room");
if (room == null) {
    response.sendRedirect(request.getContextPath() + "/RoomListServlet");
    return;
}
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
%>
<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">
<title>顧客側トップ画面-フロント操作時</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/03_01.css">
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
					<li><a class="gnav_button"
						href="<%=request.getContextPath()%>/cart_detail.jsp"> <img
							class="cart_img" src="<%=request.getContextPath()%>/img/cart.png"
							alt="cart" width="20" height="20">カート内容を確認
					</a></li>
				</ul>
			</nav>
		</div>
	</header>
	<main>
		<div>
			<h1 class="bodytitle text-center">サービス一覧</h1>
			<p class="bodymsg text-center">ご利用するサービスをお選びください</p>
			<div class="container">
				<a class="button" href="<%=request.getContextPath()%>/item_list">一覧から商品を探す</a>
				<a class="button"
					href="<%=request.getContextPath()%>/item_search.jsp">メニュー番号から商品を探す</a>

			</div>
			<div class="container">
				<a class="button"
					href="<%=request.getContextPath()%>/cusPurchaseHistory">注文履歴</a> <a
					class="button"
					href="<%=request.getContextPath()%>/ExtendCanServlet">利用時間の延長申請</a>
				<a class="button"
					href="<%=request.getContextPath()%>/cart_detail.jsp">カート内容を確認</a>
			</div>
		</div>

		<div class="footer-wrap">
			<h1>部屋番号${room.getRoomNo() }</h1>
			<c:if test="user != null && user.isFront()">
			<button type="button" class="btn-back"
				onclick="location.href='<%=request.getContextPath()%>/front/front_top.jsp'">表示選択画面へ戻る</button>
			</c:if>
			<c:if test="remainingMinutes != null">
			<h1>
				残り時間 <span id="remainingTime">--:--</span>
			</h1>
			</c:if>
		</div>
	</main>
	<jsp:include page="common.jsp" />

</body>

</html>