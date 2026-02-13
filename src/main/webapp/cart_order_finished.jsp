<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<%
Room room = (Room) session.getAttribute("room");
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
%>
<%
Integer orderNo = (Integer) session.getAttribute("orderNo");
if (orderNo == null) {
	response.sendRedirect(request.getContextPath() + "/cus_top.jsp");
	return;
}
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>注文完了画面</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/07.css">
</head>
<body>
	<%@ include file="/shered/cus_header.jsp"%>
	<main class="text-center">
		<h1 class="bodytitle">ご注文の完了</h1>
		<p class="bodymsg">ご注文いただき、誠にありがとうございました</p>
		<h1 class="order-number">
			<c:if test="${not empty orderNo}">
			受取番号：<fmt:formatNumber value="${orderNo}" pattern="0000" />
			</c:if>
			<c:if test="${empty orderNo}">部屋までお届けします</c:if>
		</h1>
		<p class="bodymsg">商品を受け取る際に、受取番号をスタッフにお伝えください</p>
		<div class="action-buttons flex-center">
			<button type="button" class="btn-back"
				onclick="location.href='<%=request.getContextPath()%>/cus_top.jsp'">トップページへ戻る</button>
		</div>
		<%
		session.removeAttribute("orderNo");
		%>
	</main>
	<%@ include file="/shered/cus_footer.jsp"%>
</body>
</html>