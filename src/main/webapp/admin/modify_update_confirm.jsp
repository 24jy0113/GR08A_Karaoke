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
<title>商品情報確認画面-${editItem.id<1 ? "追加" : "更新" }-管理者</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/08_04.css">
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
				<h2>商品情報確認</h2>
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
					<c:if test='${sessionScope.admin}'>
						<tr>
							<th>注文番号</th>
							<td>${editItem.itemNo }</td>
						</tr>
						<tr>
							<th>カテゴリー</th>
							<td>${editItem.category }</td>
						</tr>
					</c:if>
					<tr>
						<th>在庫</th>
						<td>${editItem.stock ? "あり":"なし" }</td>
					</tr>
					<c:if test='${sessionScope.admin}'>
						<tr>
							<th>オプション</th>
							<td>${editItem.hasOption() ? "あり":"なし" }</td>
						</tr>
						<c:if test="${editItem.hasOption() }">
							<c:forEach var="option" items="${editItem.optionList}">
								<tr>
									<th></th>
									<td>${option.name }</td>
								</tr>
							</c:forEach>
						</c:if>
					</c:if>
				</table>
				<div class="action-buttons">
					<form method="post"
						action="${pageContext.request.contextPath}/ItemEditServlet">
						<button name="cmd" value="edit" type="submit" class="btn-next">修正する</button>
						<button name="cmd" value="execute" type="submit" class="btn-next">確認する</button>
					</form>
				</div>
			</div>
		</div>
	</main>

</body>
</html>