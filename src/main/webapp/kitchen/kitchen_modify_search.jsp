<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.User"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
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
<title>商品変更検索画面-キッチン</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
</head>

<body>
	<%@ include file="/shared/biz_header.jsp"%>
	<main class="text-center">
		<h1 class="bodytitle">商品検索</h1>
		<h3 class="bodymsg">変更する商品名を入力してください</h3>
		<form action="<%=request.getContextPath()%>/SearchItemByName"
			method="GET">
			<input type="hidden" name="isAdmin" value="false"> <input
				type="search" name="q" class="searchbox"> <input
				type="submit" class="search-btn" value="検索">
		</form>

		<c:if test="${!e.isEmpty }">
			<p class="errormsg">${param.e }</p>
		</c:if>

		<div class="action-buttons flex-center">
			<button type="button" class="btn-back"
				onclick="location.href='<%=request.getContextPath()%>/KitchenOrderList'">注文情報画面へ戻る</button>
		</div>
	</main>

</body>
</html>