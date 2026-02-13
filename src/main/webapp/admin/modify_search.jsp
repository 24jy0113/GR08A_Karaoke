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
<html lang=ja>
<head>
<meta charset="UTF-8">
<title>商品変更検索画面-管理者</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/08_01.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>

<body>
	<!-- Header -->
	<%@ include file="/shered/biz_header.jsp" %>
	<main>
		<div class="bodymsg">
			<div class="msg">
				<h1>商品検索</h1>
				<h3>変更する商品名を入力してください</h3>
			</div>
			<form action="<%=request.getContextPath()%>/SearchItemByName"
				method="GET"><input type="hidden" name="admin" value=""> <input type="search" name="q"> <input
				type="submit" class="search" value="検索">
			</form>
			
			<c:if test="${!e.isEmpty }">
			<p class="errormsg">${param.e }</p>
			</c:if>

			<div class="action-buttons">
				<button type="button" class="btn-back"
					onclick="location.href='<%= request.getContextPath() %>/admin/manage_top.jsp'">表示選択画面へ戻る</button>
				<button type="submit" class="btn-next"
					onclick="location.href='modify_add.jsp'">商品新規追加</button>
			</div>
		</div>
	</main>

</body>
</html>