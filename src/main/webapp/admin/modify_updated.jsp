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
<title>商品${editItem.id<1?追加:更新 }完了画面-管理者</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/08_05.css">
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
				<h2>商品情報${editItem.id<1?追加:更新 }完了</h2>
				<p>下記の内容で商品${editItem.id<1?追加:更新 }を更新しました</p>
				<table>
					<tr>
						<th>商品名</th>
						<td>${editItem.name }</td>
					</tr>
					<tr>
						<th>価格(税込)</th>
						<td>${editItem.price }円（税込）</td>
					</tr>
					<tr>
						<th>商品画像</th>
						<td><img
							src="${pageContext.request.contextPath}/img/${editItem.image}"
							alt="${editItem.name }" width="30px" height="30px"></td>
					</tr>
					<tr>
						<th>注文番号</th>
						<td>${editItem.itemNo }</td>
					</tr>
					<tr>
						<th>カテゴリー</th>
						<td>${editItem.category }</td>
					</tr>
					<tr>
						<th>在庫</th>
						<td>${editItem.stock ? "あり":"なし" }</td>
					</tr>
					<tr>
						<th>オプション</th>
						<td>${editItem.hasOption() ? "あり":"なし" }</td>
					</tr>

					<c:forEach var="option" items="${editItem.optionList}">
						<tr>
							<th></th>
							<td>${option.name }</td>
						</tr>
					</c:forEach>

				</table>

				<div class="action-buttons">

					<button type="button" class="btn-back"
						onclick="location.href='<%=request.getContextPath()%>/manage_top.jsp'">表示選択画面へ戻る</button>
					<button type="submit" class="btn-next"
						onclick="location.href='<%=request.getContextPath()%>/admin/modify_search.jsp'">商品検索画面へ戻る</button>
				</div>
			</div>

		</div>
	</main>

</body>

</html>