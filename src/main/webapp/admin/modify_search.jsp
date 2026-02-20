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
<title>商品変更検索画面-管理者</title>
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
			<input type="hidden" name="isAdmin" value="true"> <input
				type="search" name="q" class="searchbox"> <input
				type="submit" class="search-btn" value="検索">
		</form>

		<c:if test="${!e.isEmpty }">
			<p class="errormsg">${param.e }</p>
		</c:if>

		<div class="action-buttons flex-center">
			<button type="button" class="btn-back"
				onclick="location.href='<%=request.getContextPath()%>/admin/manage_top.jsp'">表示選択画面へ戻る</button>
			<button type="submit" class="btn-next"
				onclick="location.href='<%=request.getContextPath()%>/SearchItemByName?isAdmin=true&insertItem=true'">商品新規追加</button>
		</div>
	</main>

</body>
</html>