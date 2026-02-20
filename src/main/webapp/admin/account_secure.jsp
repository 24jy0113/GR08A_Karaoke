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
String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>フロントアカウント確認画面</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/login.css">

</head>
<body>
	<%@ include file="/shared/biz_header.jsp"%>
	<main>
		<h2>セキュリティのため再度ログインしてください</h2>
		<h3>アカウントログイン</h3>
		<c:if test="${ error != null}">
			<p class="errormsg">${error}</p>
		</c:if>
		<form method="post"
			action="<%=request.getContextPath()%>/AccountSecureServlet">
			<table>
				<tr>
					<th>アカウントID</th>
					<td><input type="text" name="userId" value="" required
						placeholder="例：000001"></td>
				</tr>
				<tr>
					<th>パスワード</th>
					<td><input type="password" name="password" required></td>
				</tr>
			</table>
			<div class="action-buttons">
				<input class="btn-back" type="button" value="戻る"
					onclick="location.href='${pageContext.request.contextPath}/admin/manage_top.jsp'">
				<input class="btn-next" type="submit" value="ログイン">
			</div>
		</form>
	</main>
</body>
</html>