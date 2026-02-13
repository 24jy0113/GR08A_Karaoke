<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.Item,model.Room, model.Option"%>
<%
Item item = (Item) request.getAttribute("item");
%>
<%
Room room = (Room) session.getAttribute("room");
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
%>
<!DOCTYPE html>
<html lang=ja>
<head>
    <meta charset="UTF-8">
    <title>個数選択-変更時</title>
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
                    <input type="number" value="1" min="1" class="quantity-input">
                    
                </div>
            </div>
        </div>
        <div class="action-buttons">
            <button type="button" class="btn-back" onclick="history.back()">戻る</button>
            <button type="submit" class="btn-next" onclick="location.href='item_cart_confirm_change.jsp'">次へ</button>
        </div>
    </main>
    <%@ include file="/shered/cus_footer.jsp" %>
    
</body>
</html>