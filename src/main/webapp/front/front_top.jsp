<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.User"%>
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
<title>表示選択画面ーフロントのトップ画面</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/09_01.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
</head>
<body>
<%@ include file="/shered/biz_header.jsp" %>
	
	<main>
		<div>
			<p class="bodymsg">利用する項目を選択してください</p>
			<div class="container">
				<a class="button" href="room_list.jsp">部屋状況</a> <a class="button"
					href="<%= request.getContextPath() %>/ResListFrontServlet">予約情報</a>

			</div>
			<div class="container">
				<a class="button"
					href="<%=request.getContextPath()%>/FrontOrderReady">伝票一覧</a> <a
					class="button" href="../room_search.jsp">部屋番号</a>
			</div>
		</div>
	</main>

</body>
</html>