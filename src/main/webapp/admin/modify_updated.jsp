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
<title>商品${editItem.id<1 ? "追加" : "更新" }完了画面-${isAdmin ? "管理者" : "キッチン" }</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/08_04.css">
</head>

<body>
	<%@ include file="/shared/biz_header.jsp"%>
	<main class="text-center">
		<h1 class="bodytitle">商品情報${editItem.id<1 ? "追加" : "更新" }完了</h1>
		<p class="bodymsg">下記の内容で商品を${editItem.id<1 ? "追加" : "更新" }しました</p>
		<div class="container">
			<table>
				<tr>
					<th>商品名</th>
					<td>${editItem.name }</td>
				</tr>
				<tr>
					<th>価格(税込)</th>
					<td>${editItem.price }円（税込）</td>
				</tr>
				<tr>
					<th>商品画像</th>
					<td><img
						src="${pageContext.request.contextPath}/img/${editItem.image}"
						alt="${editItem.name }" width="30px" height="30px"></td>
				</tr>
				<c:if test='${isAdmin}'>
					<tr>
						<th>メニュー番号</th>
						<td>${editItem.itemNo }</td>
					</tr>
					<tr>
						<th>カテゴリー</th>
						<td>${editItem.category }</td>
					</tr>
				</c:if>
				<tr>
					<th>在庫</th>
					<td>${editItem.stock ? "あり":"なし" }</td>
				</tr>
				<c:if test='${isAdmin}'>
					<tr>
						<th>オプション</th>
						<td>${editItem.hasOption() ? "あり":"なし" }</td>
					</tr>
					<c:forEach var="option" items="${editItem.optionList}">
						<tr>
							<th></th>
							<td>${option.name }</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
		</div>
		<div class="action-buttons flex-center">
			<c:if test="${editItem.id>1 }">
				<button type="submit" class="btn-back"
					onclick="location.href='${pageContext.request.contextPath}/SearchItemByName';return false;">該当商品一覧へ戻る</button>
			</c:if>
			<button type="button" class="btn-back"
				onclick="location.href='${pageContext.request.contextPath}/SearchItemByName?go_top=1'">商品検索画面へ戻る</button>
		</div>

	</main>

</body>

</html>