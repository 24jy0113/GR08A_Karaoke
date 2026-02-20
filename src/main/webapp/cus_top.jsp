<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.User,model.Room"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%
User user = (User) session.getAttribute("loginUser");
%>
<%
Room room = (Room) session.getAttribute("room");
if (room == null) {
	response.sendRedirect(request.getContextPath() + "/RoomListServlet");
	return;
}
%>
<!DOCTYPE html>
<html lang="ja">

<head>
<meta charset="UTF-8">
<title>顧客側トップ画面-フロント操作時</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/03_01.css">
</head>

<body>
	<%@ include file="/shared/cus_header.jsp"%>
	<main class="text-center">
		<h1 class="bodytitle">サービス一覧</h1>
		<p class="bodymsg">ご利用するサービスをお選びください</p>
		<div class="action-buttons row-2">
			<button type="button" class="btn-next"
				onclick="location.href='<%=request.getContextPath()%>/item_list'">一覧から商品を探す</button>
			<button type="button" class="btn-next"
				onclick="location.href='<%=request.getContextPath()%>/item_search.jsp'">メニュー番号から商品を探す</button>
		</div>
		<div class="action-buttons row-3">
			<button type="button" class="btn-next"
				onclick="location.href='<%=request.getContextPath()%>/cusPurchaseHistory'">注文履歴</button>
			<button type="button" class="btn-next"
				onclick="location.href='<%=request.getContextPath()%>/ExtendCanServlet'">利用時間の延長申請</button>
			<button type="button" class="btn-next"
				onclick="location.href='<%=request.getContextPath()%>/cart_detail.jsp'">カート内容を確認</button>
		</div>

	</main>
	<%@ include file="/shared/cus_footer.jsp"%>

</body>

</html>