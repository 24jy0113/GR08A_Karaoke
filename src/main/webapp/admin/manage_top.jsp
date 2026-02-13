<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="model.User"
%>
<%
User user = (User) session.getAttribute("loginUser");
if (user == null) {
    response.sendRedirect(request.getContextPath() + "/index.jsp");
    return;
}
%>

<!DOCTYPE html>
<html lang=ja>
<head>
<meta charset="UTF-8">
<title>利用する項目を選択</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/manage_top.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>

<body>
	<!-- Header -->
	<%@ include file="/shered/biz_header.jsp" %>
	<main>
		<div>
			<p class="bodymsg">利用する項目を選択してください</p>
			<div class="container">
				<a class="button" href="modify_search.jsp">商品情報</a>

			</div>
			<div class="container">
				<a class="button" href="<%= request.getContextPath() %>/ResListManagerServlet">
				    予約情報
				</a>
			</div>
			<div class="container">

				<a class="button" href="<%= request.getContextPath() %>/AccountSecureServlet">アカウント情報</a>
			</div>
		</div>
	</main>

</body>
</html>