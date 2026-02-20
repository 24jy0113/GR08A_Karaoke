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
<html lang="ja">
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
	<%@ include file="/shared/biz_header.jsp"%>
	<main>
		<div class="text-center">
			<h1 class="bodytitle">アカウント削除</h1>
			<h3 class="bodymsg">本当にこのアカウントを削除しますか？</h3>
			<h3 class="warning">※この操作は元に戻せません。</h3>
			<table class="tbstyle">
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