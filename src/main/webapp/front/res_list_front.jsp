<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.*"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
	<%@ include file="/shared/biz_header.jsp"%>
	<main class="container-base">
		<div class="text-center">
			<h2 class="bodytitle">予約一覧</h2>
			<form method="get"
				action="<%=request.getContextPath()%>/ResListFrontServlet"
				class="block">
				<label>部屋番号<input type="text" name="room_num"></label>
				<button type="submit" class="btn-filter">絞り込み</button>
			</form>
			<c:if test="${not empty error}">
			    <p style="color: red;">${error}</p>
			</c:if>
			<form method="post"
				action="<%=request.getContextPath()%>/ResListUpdateServlet">
				<table>
					<tr>
						<th>予約番号</th>
						<th>部屋番号</th>
						<th>日付</th>
						<th>予約受付時間</th>
						<th>予約退室時間</th>
						<th>状態</th>
					</tr>
					<c:forEach var="r" items="${reservationList}">
						<tr>
							<td>${r.reservationNumber}<input type="hidden"
								name="reservationNumber" value="${r.reservationNumber}">
							</td>
							<td>${r.roomNumber}</td>
							<td>${r.date}</td>
							<td><input type="time" name="startTime"
								value="${fn:substring(r.receptionTime,0,5)}"></td>
							<td><input type="time" name="endTime"
								value="${fn:substring(r.leavingTime,0,5)}"></td>
							<td>${r.statusName}</td>
						</tr>
					</c:forEach>
				</table>
				<div class="action-buttons flex-center">
					<button type="button" class="btn-back"
						onclick="location.href='<%=request.getContextPath()%>/front/front_top.jsp'">表示選択画面へ</button>
					<button type="submit" class="btn-next">変更の確定</button>
				</div>
			</form>
		</div>

	</main>

</body>
</html>