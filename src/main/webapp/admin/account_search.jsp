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
<%
Boolean ok = (Boolean) session.getAttribute("ACCOUNT_REAUTH_OK");
if (ok == null || !ok) {
    response.sendRedirect(request.getContextPath() + "/account_secure.jsp");
    return;
}
%>

<!DOCTYPE html>
<html lang=ja>
<head>
<meta charset="UTF-8">
<title>アカウント情報検索画面</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/01_04.css">
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
				<h1>アカウント情報の確認、変更、削除</h1>
			</div>
			<h4>アカウントIDかアカウント名を入力してください</h4>
			<form action="../AccountSearchServlet" method="get">
				<input type="search" name="keyword"/>
				<button type="submit" style="height: 50px;">検索</button>
			</form>
			<div class="new">
				<p>アカウントを新規で作成する場合は下記のボタンから</p>
			</div>
			<div class="action-buttons">
				<button type="submit" class="btn-next"
					onclick="location.href='sign_up.jsp'">アカウント新規作成</button>
				<button type="button" class="btn-back"
					onclick="location.href='manage_top.jsp'">表示選択画面へ戻る</button>
			</div>
		</div>
	</main>

</body>
</html>