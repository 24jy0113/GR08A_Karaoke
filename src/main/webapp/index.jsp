<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang=ja>
<head>
<meta charset="UTF-8">
<title>ログイン</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/login.css">
</head>
<body>
	<!-- Header -->
	<header>
		<div class="header_inner">
			<img class="title_img"
				src="<%=request.getContextPath()%>/img/logo.png" alt="logo"
				width="60" height="60">
			<h1 class="title_name">七福サウンド</h1>
		</div>
	</header>
	<%
	String logoutMsg = (String) session.getAttribute("logoutMsg");
	if (logoutMsg != null) {
	%>
	<p style="color: red; text-align: left;">
		<%=logoutMsg%>
	</p>
	<%
	session.removeAttribute("logoutMsg");
	}
	%>

	<main>
		<h2>アカウントIDとパスワードを入力し、ログインしてください</h2>
		<h3>アカウントログイン</h3>
		<form method="post"
			action="<%=request.getContextPath()%>/LoginServlet">
			<table>
				<tr>
					<th>アカウントID</th>
					<td><input type="text" name="userId" required
						placeholder="例：000001"></td>
				</tr>
				<tr>
					<th>パスワード</th>
					<td><input type="password" name="password" required></td>
				</tr>
			</table>
			<input class="btn-next" type="submit" value="ログイン">
		</form>

		<%
		String error = (String) request.getAttribute("error");
		if (error != null) {
		%>
		<p style="color: red;"><%=error%></p>
		<%
		}
		%>
	</main>
</body>
</html>