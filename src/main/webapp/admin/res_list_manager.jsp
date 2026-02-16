<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.*"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

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
<title>予約情報画面</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/10.css">
</head>

<body>
	<%@ include file="/shered/biz_header.jsp"%>
	<main class="container">
		<div class="text-center">
			<h1 class="bodytitle">予約一覧</h1>
			<form method="get"
				action="<%=request.getContextPath()%>/ResListManagerServlet"
				class="block">
				<label>部屋番号<input type="text" name="room_num"></label>
				<button type="submit" class="btn-filter">絞り込み</button>
			</form>
			<table>
				<tr>
					<th>予約番号</th>
					<th>部屋番号</th>
					<th>日付</th>
					<th>予約受付時間</th>
					<th>予約退室時間</th>
				</tr>
				<c:forEach var="r" items="${reservationList}">
					<tr>
						<td>${r.reservationNumber}</td>
						<td>${r.roomNumber}</td>
						<td>${r.date}</td>
						<td>${r.receptionTime}</td>
						<td>${r.leavingTime}</td>
					</tr>
				</c:forEach>
			</table>
			<div class="action-buttons flex-center">
				<button type="button" class="btn-back"
					onclick="location.href='<%=request.getContextPath()%>/admin/manage_top.jsp'">表示選択画面へ</button>
				<button type="button" class="btn-next"
					onclick="location.href='<%=request.getContextPath()%>/admin/res_msg_upload.jsp'">予約情報読み込み</button>
			</div>
		</div>
	</main>
</body>
</html>