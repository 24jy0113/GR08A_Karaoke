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
Boolean ok = (Boolean) session.getAttribute("ACCOUNT_REAUTH_OK");
if (ok == null || !ok) {
	response.sendRedirect(request.getContextPath() + "/account_secure.jsp");
	return;
}
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>アカウント情報検索画面</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/01_04.css">
</head>

<body>
	<%@ include file="/shared/biz_header.jsp"%>
	<main>
		<div class="text-center">
			<h1 class="bodytitle">アカウント情報の確認、変更、削除</h1>
			<p class="bodymsg">アカウントIDかアカウント名を入力してください</p>
			<form action="<%=request.getContextPath()%>/AccountSearchServlet"
				method="get">
				<input type="search" name="keyword" class="searchbox">
				<button type="submit" class="search-btn">検索</button>
			</form>
			<div class="action-buttons flex-center">
				<p>アカウントを新規で作成する場合は下記のボタンから</p>
				<button type="submit" class="btn-next"
					onclick="location.href='sign_up.jsp'">アカウント新規作成</button>
				<button type="button" class="btn-back"
					onclick="location.href='manage_top.jsp'">表示選択画面へ戻る</button>
			</div>
		</div>
	</main>

</body>
</html>