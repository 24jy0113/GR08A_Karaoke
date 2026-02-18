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
<title>延長時間選択画面</title>
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
			<h1 class="bodymsg">申し訳ありませんが延長はできません</h1>
			<h1 class="bodymsg">お時間になりましたら、ご退室ください</h1>
			<table>
				<tr>
					<th>部屋番号</th>
					<th>受付時間</th>
					<th>退室時間</th>
				</tr>
				<!-- 1行目 -->
				<tr>
					<td><%=room.getRoomNo()%></td>
					<td><%=room.getReceptionTime()%></td>
					<td><%=room.getLeavingTime()%></td>

				</tr>
			</table>
			<div class="action-buttons">
				<button type="button" class="btn-back"
					onclick="location.href='<%=request.getContextPath()%>/cus_top.jsp'">トップページへ戻る</button>

			</div>
		</div>
	</main>
	<%@ include file="/shered/cus_footer.jsp"%>
</body>
</html>