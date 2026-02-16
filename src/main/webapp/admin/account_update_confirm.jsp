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
User u = (User) session.getAttribute("UPDATE_USER");
if (u == null) {
	response.sendRedirect(request.getContextPath() + "/admin/account_search.jsp");
	return;
}
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>アカウント情報の変更確認</title>

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
		<c:if test="${ error != null}">
			<p class="errormsg">${error}</p>
		</c:if>
		<div>
			<h1 class="bodytitle text-center">アカウント情報の変更確認</h1>
			<p class="bodymsg text-center">下記の情報を確認してください</p>
			<table class="tbstyle">
				<tbody>
					<tr>
						<th>アカウントID</th>
						<td><%=u.getUserId()%></td>
					</tr>
					<tr>
						<th>アカウント名</th>
						<td><%=u.getUserName()%></td>
					</tr>
					<tr>
						<th>パスワード</th>
						<td><%=u.getPasswordHash() == null ? "変更なし" : "変更あり"%></td>
					</tr>
					<tr>
						<th>役割</th>
						<td><%=u.getRoleName()%></td>
					</tr>

				</tbody>
			</table>

			<div class="action-buttons flex-center">
				<button type="button" class="btn-back" onclick="history.back()">修正する</button>
				<form
					action="<%=request.getContextPath()%>/AccountUpdateConfirmServlet"
					method="post">
					<button type="submit" class="btn-next">変更を完了する</button>
				</form>
			</div>
		</div>
	</main>

</body>
</html>