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
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>該当商品詳細画面-管理者</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/08_02.css">
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
					<li><img class="user_img"
						src="<%=request.getContextPath()%>/img/user.png" alt="cart"
						width="25" height="25"><%=user.getUserName()%></li>
					<li><a class="gnav_botton"
						href="<%=request.getContextPath()%>/LogoutServlet">ログアウト</a></li>
				</ul>
			</nav>
		</div>
	</header>
	<main>
		<div class="bodymsg">
			<div class="msg">
				<h2>該当商品一覧</h2>
				<form method="get"
					action="${pageContext.request.contextPath}/ItemEditServlet">
					<table>
						<tr>
							<th>商品画像</th>
							<th>商品名</th>
							<th>単価</th>
							<th>在庫</th>
							<th></th>
						</tr>

						<c:forEach var="item" items="${searchResult}">
							<tr>
								<td><img
									src="${pageContext.request.contextPath}/img/${item.image}"
									alt="${item.name }"></td>
								<td>${item.name }</td>
								<td>${item.price }円（税込）</td>
								<td>${item.isStock() ? "あり" : "なし"}</td>
								<td><button type="submit" name="edit" value="${item.id }">"変更</button></td>
							</tr>
						</c:forEach>
					</table>

					<div class="action-buttons">
						<button type="button" class="btn-back" onclick="history.back()">商品検索画面へ戻る</button>
						<button name="cmd" value="add" type="submit" class="btn-next">商品新規追加</button>
					</div>
				</form>

			</div>

		</div>
	</main>


</body>
</html>