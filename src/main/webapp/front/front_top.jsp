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
<title>表示選択画面ーフロントのトップ画面</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/select.css">
</head>
<body>
	<%@ include file="/shered/biz_header.jsp"%>
	<main class="text-center">
		<h1 class="bodytilte">利用する項目を選択してください</h1>
		<div class="action-buttons">
			<button type="button" class="btn-next"
				onclick="location.href='room_list.jsp'">部屋状況</button>
			<button type="button" class="btn-next"
				onclick="location.href='<%=request.getContextPath()%>/ResListFrontServlet'">予約情報</button>
			<button type="button" class="btn-next"
				onclick="location.href='<%=request.getContextPath()%>/FrontOrderReady'">伝票一覧</button>
			<button type="button" class="btn-next"
				onclick="location.href='front_room_search.jsp'">部屋番号</button>
			<button type="button" class="btn-back"
				onclick="location.href='<%=request.getContextPath()%>/index_select.jsp'">担当選択画面に戻る</button>
		</div>
	</main>
</body>
</html>