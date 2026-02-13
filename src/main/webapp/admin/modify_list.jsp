<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.User"%>
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
<title>該当商品詳細画面-${sessionScope.admin ? "管理者" : "キッチン" }</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/08_02.css">
</head>

<body>
	<%@ include file="/shered/biz_header.jsp"%>
	<main class="text-center">
		<h1 class="bodytitle">該当商品一覧</h1>
		<div class="container">
			<form method="get"
				action="${pageContext.request.contextPath}/ItemEditServlet">
				<table>
					<tr>
						<th>商品画像</th>
						<th>商品名</th>
						<th>単価</th>
						<th>在庫</th>
						<th></th>
					</tr>
					<c:forEach var="item" items="${searchResult}">
						<tr>
							<td><img width=200
								src="${pageContext.request.contextPath}/img/${item.image}"
								alt="${item.name }"></td>
							<td>${item.name }</td>
							<td>${item.price }円（税込）</td>
							<td>${item.isStock() ? "あり" : "なし"}</td>
							<td><button type="submit" name="id" value="${item.id }"
									class="btn-next">変更</button></td>
						</tr>
					</c:forEach>
				</table>
				<div class="action-buttons flex-center">
					<button type="button" class="btn-back"
						onclick="location.href='${pageContext.request.contextPath}/SearchItemByName?go_top=1'">商品検索画面へ戻る</button>
					<c:if test="${sessionScope.admin }">
						<button type="submit" class="btn-next">商品新規追加</button>
					</c:if>
				</div>
			</form>
		</div>
	</main>


</body>
</html>