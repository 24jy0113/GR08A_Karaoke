<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header>
		<div class="header_inner">
			<img class="title_img"
				src="<%=request.getContextPath()%>/img/logo.png" alt="logo"
				width="60" height="60">
			<h1 class="title_name">七福サウンド</h1>
			<nav class="gnav">
				<ul class="gnav_list">
					<li><img class="user_img"
						src="<%=request.getContextPath()%>/img/user.png" alt="cart"
						width="25" height="25"><%=user.getUserName()%></li>
					<li><a class="gnav_button"
						href="<%=request.getContextPath()%>/LogoutServlet">ログアウト</a></li>
				</ul>
			</nav>
		</div>
	</header>