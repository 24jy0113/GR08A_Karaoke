<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.User"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
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
<title>商品変更検索画面-キッチン</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/08_01.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>

<body>
	<!-- Header -->
	<header>
		<div class="header_inner">
			<img class="title_img" src="<%= request.getContextPath() %>/img/logo.png" alt="logo" width="60"
				height="60">
			<h1 class="title_name">七福サウンド</h1>
			<nav class="gnav">
				<ul class="gnav_list">
					 <li><img class="user_img" src="<%= request.getContextPath() %>/img/user.png" alt="cart" width="25" height="25"><%= user.getUserName() %></li>
                    <li><a class="gnav_botton" href="<%= request.getContextPath() %>/LogoutServlet">ログアウト</a></li>
				</ul>
			</nav>
		</div>
	</header>
	<main>
		<div class="bodymsg">
			<div class="msg">
				<h1>商品検索</h1>
				<h3>変更する商品名を入力してください</h3>
			</div>
			<form action="<%=request.getContextPath()%>/SearchItemByName"
				method="GET"> <input type="hidden" name="admin" value="1"> <input type="search" name="q"> <input
				type="submit" class="search" value="検索">
			</form>
			
			<c:if test="${!e.isEmpty }">
			<p class="errormsg">${param.e }</p>
			</c:if>

			<div class="action-buttons">
				<button type="button" class="btn-back"
						onclick="location.href='<%=request.getContextPath()%>/KitchenOrderList'">注文情報画面へ戻る</button>
			</div>
		</div>
	</main>

</body>
</html>