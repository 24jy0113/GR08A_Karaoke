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
		let tapCount = 0;
		let resetTimer = null;
		document.body
				.addEventListener(
						"click",
						function(e) {
							if (e.clientX < 50 && e.clientY < 50) {
								tapCount++;
								// 最初のタップなら3秒タイマー開始
								if (tapCount === 1) {
									resetTimer = setTimeout(function() {
										tapCount = 0;
									}, 3000);
								}
								if (tapCount >= 5) {
									clearTimeout(resetTimer);
									window.location.href = "${pageContext.request.contextPath}/room_search.jsp";
								}
							}
						});
	</script>
</body>
</html>