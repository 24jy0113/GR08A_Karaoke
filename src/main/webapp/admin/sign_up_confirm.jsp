<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.User"%>
<%@ taglib prefix="auth" uri="/auth"%>
<%
User user = (User) session.getAttribute("loginUser");
if (user == null) {
	response.sendRedirect(request.getContextPath() + "/index.jsp");
	return;
}
%>
<%
String userId = (String) session.getAttribute("SIGNUP_USER_ID");
String userName = (String) session.getAttribute("SIGNUP_USER_NAME");
String roleName = (String) session.getAttribute("SIGNUP_ROLE_NAME");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>アカウント登録確認</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/01.css">
</head>
<body>
	<%@ include file="/shered/biz_header.jsp"%>
	<main>
		<div class="text-center">
			<h1 class="bodytitle">アカウント登録情報の確認</h1>
			<p class="bodymsg">下記の情報を確認してください</p>
			<table class="tbstyle">
				<tr>
					<th>アカウントID</th>
					<td><%=userId%></td>
				</tr>
				<tr>
					<th>アカウント名</th>
					<td><%=userName%></td>
				</tr>
				<tr>
					<th>パスワード</th>
					<td>XXXXXXXX</td>
				</tr>
				<tr>
					<th>役割情報</th>
					<td><%=roleName%></td>
				</tr>
			</table>
			<div class="action-buttons flex-center">
				<button type="button" class="btn-back"
					onclick="location.href='<%=request.getContextPath()%>/admin/sign_up.jsp'">修正する</button>
				<form action="<%=request.getContextPath()%>/SignUpCompleteServlet"
					method="post">
					<button type="submit" class="btn-next">ユーザ登録を完了する</button>
				</form>
			</div>
		</div>
	</main>

</body>
</html>