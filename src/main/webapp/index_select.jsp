<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="model.User"
%>
<%@ taglib prefix="auth" uri="/auth" %>

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
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/index_choice.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
	<!-- Header -->
	<header>
		<div class="header_inner">
			<img class="title_img" src="<%= request.getContextPath() %>/img/logo.png" alt="logo" width="60"
				height="60">
			<h1 class="title_name">七福サウンド</h1>
			<nav class="gnav">
				<ul class="gnav_list">
					 <li><img class="user_img" src="<%= request.getContextPath() %>/img/user.png" alt="cart" width="25" height="25"><%= user.getUserName() %></li>
                    <li><a class="gnav_botton" href="<%= request.getContextPath() %>/LogoutServlet">ログアウト</a></li>
				</ul>
			</nav>
		</div>
	</header>
	<main>
		<div>
			<p class="bodymsg">利用する項目をクリックしてください</p>
			<div class="container">

			     <auth:hasPermission code="VIEW_FRONT">
			        <a class="button" href="${pageContext.request.contextPath}/front/front_top.jsp">
			            フロント用
			        </a>
			    </auth:hasPermission>
			
			    <auth:hasPermission code="VIEW_FLOOR">
			        <a class="button" href="${pageContext.request.contextPath}/FrontOrderReady">
			            フロア用
			        </a>
			    </auth:hasPermission>
			
			</div>

			<div class="container">

			    <auth:hasPermission code="VIEW_KITCHEN">
			    	<form action="${pageContext.request.contextPath}/KitchenOrderList" method="get">
					    <button type="submit" class="button">キッチン用</button>
					</form>
			    </auth:hasPermission>
				<auth:hasPermission code="VIEW_CUS">
			    <a class="button" href="${pageContext.request.contextPath}/room_search.jsp">顧客用</a>
				</auth:hasPermission>
			</div>

			<div class="container">
			
			    <auth:hasPermission code="ADMIN_ALL">
			        <a class="button" href="${pageContext.request.contextPath}/admin/manage_top.jsp">
			            管理者用
			        </a>
			    </auth:hasPermission>
			
			</div>
			
		</div>
	</main>

</body>
</html>