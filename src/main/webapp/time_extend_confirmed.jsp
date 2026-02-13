<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.Room"%>

<%
Room room = (Room) session.getAttribute("room");
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>延長完了画面</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/04.css">
</head>
<body>
	<%@ include file="/shered/cus_header.jsp" %>
	<main>
		<div class="container text-center">
			<h2 class="bodymsg">延長申請が完了し、ご利用可能時間が更新されました</h2>
			<h2 class="bodymsg">引き続き、お楽しみください！</h2>
			<div class="action-buttons">
				<button type="button" class="btn-back"
					onclick="location.href='<%=request.getContextPath()%>/cus_top.jsp'">トップページへ戻る</button>
			</div>
		</div>
	</main>
<%@ include file="/shered/cus_footer.jsp" %>
</body>
</html>