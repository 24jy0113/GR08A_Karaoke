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
<title>予約情報登録完了画面</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/10.css">
</head>

<body>
	<%@ include file="/shered/biz_header.jsp"%>
	<main class="text-center">
		<h1 class="bodytitle">予約情報取得完了</h1>
		<h2 class="bodymsg">予約情報の読み込みが完了しました！</h2>
		<div class="action-buttons flex-center">
			<button type="button" class="btn-back"
				onclick="location.href='<%=request.getContextPath()%>/ResListManagerServlet'">予約情報画面へ戻る</button>
		</div>
	</main>

</body>
</html>