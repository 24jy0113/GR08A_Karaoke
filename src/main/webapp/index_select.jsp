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
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>利用する項目を選択</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/select.css">
</head>
<body>
	<%@ include file="/shared/biz_header.jsp"%>
	<main class="text-center">
		<h1 class="bodytitle">利用する項目をクリックしてください</h1>
		<div class="action-buttons">
			<auth:hasPermission code="VIEW_FRONT">
				<button type="button" class="btn-next"
					onclick="location.href='${pageContext.request.contextPath}/front/front_top.jsp'">フロント用</button>
			</auth:hasPermission>
			<auth:hasPermission code="VIEW_FLOOR">
				<button type="button" class="btn-next"
					onclick="location.href='${pageContext.request.contextPath}/FloorOrderReady'">フロア用</button>
			</auth:hasPermission>
			<auth:hasPermission code="VIEW_KITCHEN">
				<button type="button" class="btn-next"
					onclick="location.href='${pageContext.request.contextPath}/KitchenOrderList'">キッチン用</button>
			</auth:hasPermission>
			<auth:hasPermission code="VIEW_CUS">
				<button type="button" class="btn-next"
					onclick="location.href='${pageContext.request.contextPath}/room_search.jsp'">顧客用</button>
			</auth:hasPermission>
			<auth:hasPermission code="ADMIN_ALL">
				<button type="button" class="btn-next"
					onclick="location.href='${pageContext.request.contextPath}/admin/manage_top.jsp'">管理者用</button>
			</auth:hasPermission>
		</div>
	</main>

</body>
</html>