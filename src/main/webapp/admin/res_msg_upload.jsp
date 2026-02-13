<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.*"%>
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
<title>予約情報取得画面</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/10.css">
</head>

<body>
	<%@ include file="/shered/biz_header.jsp"%>
	<main class="text-center">
		<form action="<%=request.getContextPath()%>/ResMsgUploadServlet"
			method="post" enctype="multipart/form-data">
			<h1 class="bodytitle">予約情報取得</h1>
			<h3 class="bodymsg">読み込む予約情報のデータをアップロードしてください</h3>
			<div class="input-row">
				<label>予約データ</label> <label class="csv-box"> CSV <input
					type="file" name="csvFile" class="hidden-file" required>
				</label>
			</div>
			<div class="action-buttons flex-center">
				<button type="button" class="btn-back"
					onclick="location.href='<%=request.getContextPath()%>/ResListManagerServlet'">予約情報画面へ戻る</button>
				<button type="submit" class="btn-next">予約情報を取得する</button>
			</div>
		</form>
	</main>

</body>
</html>