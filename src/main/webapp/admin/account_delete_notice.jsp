<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.User"%>
<%
User user = (User) session.getAttribute("loginUser");
if (user == null) {
	response.sendRedirect(request.getContextPath() + "/index.jsp");
	return;
}
%>
<%
User u = (User) session.getAttribute("DELETE_USER");
if (u == null) {
	response.sendRedirect(request.getContextPath() + "/admin/account_search.jsp");
	return;
}
%>
<!DOCTYPE html>
<html lang=ja>
<head>
<meta charset="UTF-8">
<title>アカウント削除</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/01.css">
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
		<div>
			<div class="text-center">
				<h1 class="bodytitle">アカウント削除</h1>
				<h3 class="bodymsg">本当にこのアカウントを削除しますか？</h3>
				<h3 class="warning">※この操作は元に戻せません。</h3>
			</div>
			<table class="tbstyle">
				<tbody>
					<tr>
						<th>アカウントID</th>
						<td><%=u.getUserId()%>
					</tr>
					<tr>
						<th>アカウント名</th>
						<td><%=u.getUserName()%>
					</tr>
					<tr>
						<th>パスワード</th>
						<td>XXXXXXXX</td>
					</tr>
					<tr>
						<th>役割</th>
						<td><%=u.getRoleName()%></td>
					</tr>
				</tbody>
			</table>
			<div class="action-buttons flex-center">
				<button type="button" class="btn-back" onclick="history.back()">キャンセル</button>
				<form action="<%=request.getContextPath()%>/AccountDeleteServlet"
					method="post">
					<input type="hidden" name="userId" value="<%=u.getUserId()%>">
					<button class="btn-next" type="submit">削除する</button>
				</form>
			</div>
		</div>
	</main>

</body>
</html>