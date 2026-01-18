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
<title>フロントアカウント確認画面</title>
<link rel="stylesheet" type="text/css" href="../css/01_03.css">
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
					 <li><img class="user_img" src="../img/user.png" alt="cart" width="25" height="25"><%= user.getUserName() %></li>
                    <li><a class="gnav_botton" href="<%= request.getContextPath() %>/LogoutServlet">ログアウト</a></li>
				</ul>
			</nav>
		</div>
	</header>
	<main>

		<h2>セキュリティのため再度ログインしてください</h2>
		<h3>アカウントログイン</h3>
		<form method="post" action="<%= request.getContextPath() %>/AccountSecureServlet">
			<table>
				<tr>
					<th>アカウントID</th>
					<td><input type="text" name="userId" value="" required
						placeholder="例：000001"></td>
					<td></td>
				</tr>
				<tr>
					<th>パスワード</th>
					<td><input type="password" name="password" required></td>
					<td></td>
				</tr>
				<tr class="action-buttons">
					<td><input class="back" type="button" value="戻る"
						onclick="history.back()"></td>
					<td><input class="submit" type="submit" value="ログイン"></td>
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
	</main>

</body>
</html>