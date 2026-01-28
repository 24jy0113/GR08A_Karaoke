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
<link rel="stylesheet" type="text/css" href="../css/08_02.css">
<link rel="stylesheet" type="text/css" href="../css/header.css">
</head>

<body>
	<!-- Header -->
	<header>
		<div class="header_inner">
			<img class="title_img" src="../img/logo.png" alt="logo" width="60"
				height="60">
			<h1 class="title_name">七福サウンド</h1>
			<nav class="gnav">
				<ul class="gnav_list">
					<li><img class="user_img" src="../img/user.png" alt="cart"
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
				<form method="post">
				<table>

					<tr>
						<th>商品画像</th>
						<th>商品名</th>
						<th>単価</th>
						<th>在庫</th>
						<th></th>
						<th></th>
					</tr>

					<c:forEach var="item" items="${searchResult}">
						<tr>
							<td><img src="${item.getImage()} }" alt="salad"></td>
							<td>${item.name }</td>
							<td>${item.price }円（税込）</td>
							<td>${item.isStock() ? "あり" : "なし"}</td>
							<td><button type="submit" name="id" value="${item.id }" formaction="modify_update.jsp">変更</button></td>
							<td><button type="submit" name="id" value="${item.id }" formaction="modify_delete.jsp">削除</button></td>
						</tr>
						</c:forEach>
				</table>
				</form>

				<div class="action-buttons">
					<button type="button" class="btn-back"
						onclick="location.href='modify_search.jsp'">商品検索画面へ戻る</button>
					<button type="submit" class="btn-next"
						onclick="location.href='modify_add.jsp'">商品新規追加</button>

				</div>
			</div>

		</div>
	</main>


</body>
</html>