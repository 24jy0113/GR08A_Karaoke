<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
int roomNo = Integer.parseInt(request.getParameter("roomNumber"));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>利用開始画面</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
</head>
<body>
	<button type="button" class="btn-back"
		onclick="location.href='<%=request.getContextPath()%>/IdleStartServlet?roomNumber=<%=roomNo%>'">利用を開始する</button>
</body>
</html>