<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=ja>
<head>
<meta charset="UTF-8">
<title>ログイン</title>
<link rel="stylesheet" type="text/css" href="./css/index.css">
</head>
<body>
	<!-- Header -->
	<header>
		<div class="header-container">
			<a href="./index.jsp"> <img src="./img/logo.png" alt="logo"
				width="44" height="44">
			</a> <a href="index.jsp" class="logo-text">七福サウンド</a>
		</div>
	</header>
	<main>
		<p>アカウントIDとパスワードを入力し、ログインしてください</p>
		<h2>アカウントログイン</h2>
		<form method="post" action="<%= request.getContextPath() %>/LoginServlet">
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
				<tr>
					<td></td>
					<td><input class="button" type="submit" value="ログイン">
					</td>
				</tr>
				<tr>
					<td></td>
					<td><a href="#">パスワードをお忘れの方はフロント担当にお聞きください。</a></td>
				</tr>
			</table>
		</form>

		<%
		String error = (String) request.getAttribute("error");
		if (error != null) {
		%>
		<p style="color: red;"><%=error%></p>
		<%
		}
		%>
	
</body>
</html>