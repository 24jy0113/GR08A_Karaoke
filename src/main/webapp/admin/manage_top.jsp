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
<title>利用する項目を選択</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/manage_top.css">
</head>

<body>
	<%@ include file="/shared/biz_header.jsp"%>
	<main class="text-center">
		<h1 class="bodytitle">利用する項目を選択してください</h1>
		<div class="action-buttons">
			<button type="button" class="btn-next"
				onclick="location.href='modify_search.jsp'">商品情報</button>
			<button type="button" class="btn-next"
				onclick="location.href='<%=request.getContextPath()%>/ResListManagerServlet'">予約情報</button>
			<button type="button" class="btn-next"
				onclick="location.href='<%=request.getContextPath()%>/AccountSecureServlet'">アカウント情報</button>
			<button type="button" class="btn-back"
				onclick="location.href='<%=request.getContextPath()%>/index_select.jsp'">担当選択画面に戻る</button>
		</div>
	</main>

</body>
</html>