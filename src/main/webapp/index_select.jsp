<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="model.User"
%>
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
<title>利用する項目を選択</title>
<link rel="stylesheet" type="text/css" href="./css/index_choice.css">
<link rel="stylesheet" type="text/css" href="./css/header.css">
</head>
<body>
	<!-- Header -->
	<header>
		<div class="header_inner">
			<img class="title_img" src="./img/logo.png" alt="logo" width="60"
				height="60">
			<h1 class="title_name">七福サウンド</h1>
			<nav class="gnav">
				<ul class="gnav_list">
					 <li><img class="user_img" src="./img/user.png" alt="cart" width="25" height="25"><%= user.getUserName() %></li>
                    <li><a class="gnav_botton" href="<%= request.getContextPath() %>/LogoutServlet">ログアウト</a></li>
				</ul>
			</nav>
		</div>
	</header>
	<main>
		<div>
			<p class="bodymsg">フロント・フロア・キッチン・顧客の中から利用する項目を選択してください</p>
			<div class="container">
				<a class="button" href="front_top.jsp">フロント用</a> <a
					class="button" href="floor_order_list.jsp">フロア用</a>

			</div>
			<div class="container">
				<a class="button" href="kitchen_order_list.jsp">キッチン用</a> <a
					class="button" href="room_search.jsp">顧客用</a>
			</div>
			<div class="container">
				<a class="button" href="manage_top.jsp">管理者用</a>
			</div>
		</div>
	</main>

</body>
</html>