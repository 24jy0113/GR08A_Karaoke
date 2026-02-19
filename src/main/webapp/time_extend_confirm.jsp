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
<title>延長内容のご確認</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/04.css">
</head>
<body>
	<%@ include file="/shered/cus_header.jsp"%>
	<main>
		<div class="container-base text-center">
			<h2 class="bodymsg">下記の延長内容を確認し、完了ボタンを押してください</h2>
			<table>
				<tr>
					<th>部屋番号</th>
					<th>受付時間</th>
					<th>元の退室時間</th>
					<th>延長後の退室時間</th>
					<th>合計延長時間（分）</th>
				</tr>
				<!-- 1行目 -->
				<tr>
					<td><%=room.getRoomNo()%></td>
					<td><%=room.getReceptionTime()%></td>
					<td><%=request.getAttribute("currentLeaving")%></td>
					<td><%=request.getAttribute("newLeaving")%></td>
					<td><%=request.getAttribute("extendMinutes")%></td>
				</tr>
			</table>
			<div class="action-buttons flex-center">
				<form action="<%=request.getContextPath()%>/ExtendConfirmServlet"
					method="post">
					<button type="button" class="btn-next"
				onclick="location.href='<%=request.getContextPath()%>/ExtendCanServlet'">戻る</button>
					<button type="submit" class="btn-next">延長申請を完了する</button>
				</form>
			</div>
		</div>
	</main>
	<%@ include file="/shered/cus_footer.jsp"%>
</body>
</html>