<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.Item,model.Room"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%
ArrayList<Item> itemList = (ArrayList<Item>) request.getAttribute("itemList");
%>
<%
Integer currentPageObj = (Integer) request.getAttribute("currentPage");
Integer totalPagesObj = (Integer) request.getAttribute("totalPages");
Integer categoryId = (Integer) request.getAttribute("categoryId");
int currentPage = (currentPageObj != null) ? currentPageObj : 1;
int totalPages = (totalPagesObj != null) ? totalPagesObj : 1;

String catParam = categoryId != null ? "&category=" + categoryId : "";
%>
<%
Room room = (Room) session.getAttribute("room");
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
%>
<!doctype html>
<html lang="ja">

<head>
<meta charset="utf-8" />
<title>商品一覧画面</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/06_02.css">
</head>

<body>
	<%@ include file="/shered/cus_header.jsp"%>
	<main>
		<!-- ▼ カテゴリタブ -->
		<div class="tabs">
			<c:forEach var="category" items="${categoryMap }">
				<a
					href="<%=request.getContextPath()%>/item_list?category=${category.key }"
					class="tab">${category.value }</a>
			</c:forEach>
		</div>
		<!-- ▼ メニュー表示部分 -->
		<div class="wrapper">
			<%
			if (currentPage > 1) {
			%>
			<a class="arrow"
				href="<%=request.getContextPath()%>/item_list?page=<%=currentPage - 1%><%=catParam%>">
				❮ </a>
			<%
			} else {
			%>
			<span class="arrow disabled">❮</span>
			<%
			}
			%>

			<div class="grid">
				<%
				if (itemList != null) {
					for (Item item : itemList) {
				%>

				<div class="item">
					<a
						href="<%=request.getContextPath()%>/item_detail?id=<%=item.getId()%>">
						<div class="item-img">
							<img src="<%=request.getContextPath()%>/img/<%=item.getImage()%>"
								alt="productImage">
						</div>
						<div class="item-name"><%=item.getName()%></div>
						<div class="item-price"><%=item.getPrice()%>円(税込)
						</div>
					</a>
				</div>

				<%
				}
				}
				%>

			</div>
			<span><%=currentPage%> / <%=totalPages%></span>
			<%
			if (currentPage < totalPages) {
			%>
			<a class="arrow"
				href="<%=request.getContextPath()%>/item_list?page=<%=currentPage + 1%><%=catParam%>">
				❯ </a>
			<%
			} else {
			%>
			<span class="arrow disabled">❯</span>
			<%
			}
			%>

		</div>
	</main>
	<%@ include file="/shered/cus_footer.jsp"%>

</body>

</html>