<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>ログイン</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/login.css">
</head>
<body>
	<%@ include file="/shered/biz_header.jsp"%>
	<main>
		<h2>アカウントIDとパスワードを入力し、ログインしてください</h2>
		<h3>アカウントログイン</h3>
		<c:if test="${ param.logoutMsg != null}">
			<p class="errormsg">${param.logoutMsg}</p>
		</c:if>
		<c:if test="${ error != null}">
			<p class="errormsg">${error}</p>
		</c:if>
		<form method="post"
			action="<%=request.getContextPath()%>/LoginServlet">
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
			</table>
			<input class="btn-next" type="submit" value="ログイン">
		</form>
	</main>
</body>
</html>