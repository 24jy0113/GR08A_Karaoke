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
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>部屋番号入力画面</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
</head>
<body>
	<%@ include file="/shered/biz_header.jsp"%>
	<main>
		<div class="text-center">
			<h1 class="bodytitle">部屋番号を入力してください</h1>
			<form action="<%=request.getContextPath()%>/RoomSearchServlet"
				method="get">
				<input class="searchbox"  type="search" name="roomNumber" required />
				<div class="action-buttons flex-center">
					<button type="button" class="btn-back"
						onclick="location.href='<%=request.getContextPath()%>/index_select.jsp'">担当選択画面に戻る</button>
					<button type="submit" class="btn-next">次へ</button>
				</div>
			</form>
		</div>
	</main>
</body>
</html>