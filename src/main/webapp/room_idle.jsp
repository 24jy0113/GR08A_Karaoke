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
	<main class="container-idle">
		<button type="button" class="btn-start"
			onclick="location.href='<%=request.getContextPath()%>/IdleStartServlet?roomNumber=<%=roomNo%>'">利用を開始する</button>
	</main>
	<script>
		const contextPath = "${pageContext.request.contextPath}/room_search.jsp"
	</script>
	<script src="<%=request.getContextPath()%>/js/staff-exit.js"></script>
</body>
</html>