<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.User"%>
<%
User user = (User) session.getAttribute("loginUser");
if (user == null) {
	response.sendRedirect(request.getContextPath() + "/index.jsp");
	return;
}
%>
<!DOCTYPE html>
<html lang=ja>
<head>
<meta charset="UTF-8">
<title>部屋番号入力画面</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/02_01.css">
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
					<li><img class="user_img"
						src="<%=request.getContextPath()%>/img/user.png" alt="cart"
						width="25" height="25"><%=user.getUserName()%></li>
					<li><a class="gnav_button"
						href="<%=request.getContextPath()%>/LogoutServlet">ログアウト</a></li>
				</ul>
			</nav>
		</div>
	</header>
	<main>
		<div class="text-center">
			<h1 class="bodytitle">部屋番号を入力してください</h1>
			<form action="<%=request.getContextPath()%>/RoomSearchServlet"
				method="get">
				<input type="search" name="roomNumber" required />
		</div>
		<div class="action-buttons flex-center">
			<button type="button" class="btn-back" onclick="history.back()">戻る</button>
			<button type="submit" class="btn-next">次へ</button>
		</div>
		</form>

	</main>

</body>
</html>