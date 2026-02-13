<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*"%>
<%
Item item = (Item) request.getAttribute("item");
%>
<%
Room room = (Room) session.getAttribute("room");
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
%>
<% OrderItem oi = (OrderItem) session.getAttribute("buildingItem"); %>
<!DOCTYPE html>
<html lang=ja>
<head>
    <meta charset="UTF-8">
    <title>個数選択</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/06_05.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
    <!-- Header -->
    <%@ include file="/shered/cus_header.jsp" %>
    <main>
        <div class="bodymsg">
            <div class="container">
                <!-- 左側：画像領域 -->
                <div>
                    <div class="left-box"><img class="item-img" src="<%=request.getContextPath()%>/img/<%=item.getImage()%>" alt="product"></div>
                    <h1><%= item.getName() %></h1>
               		<h2><%= item.getPrice() %>　円(税込)</h2>
                </div>
                <!-- 右側 -->
                <div class="right-box">
                    <h2>注文個数をお選びください</h2>
                    <form action="CartAddServlet" method="post">
					  	<input type="number" name="count" min="1" value="1" class="quantity-input" required>
						<div class="action-buttons">
				            <button type="button" class="btn-back" onclick="history.back()">戻る</button>
				            <button type="submit" class="btn-next">次へ</button>
				        </div>
			        </form>
                </div>
            </div>
        </div>
        
    </main>
    <%@ include file="/shered/cus_footer.jsp" %>
    
</body>
</html>