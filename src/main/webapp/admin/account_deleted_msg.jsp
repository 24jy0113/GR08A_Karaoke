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
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>アカウント情報の削除</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
</head>
<body>
	<%@ include file="/shered/biz_header.jsp"%>
	<main>
		<div class="text-center">
			<h1 class="bodytitle">アカウント削除完了</h1>
			<p class="bodymsg">アカウント情報の削除が完了しました</p>
			<div class="action-buttons flex-center">
				<button type="button" class="btn-back"
					onclick="location.href='account_search.jsp'">アカウント検索へ戻る</button>
			</div>
		</div>
	</main>

</body>
</html>