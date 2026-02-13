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
    <title>オプション選択-変更時</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/06_04.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
    <!-- Header -->
   <%@ include file="/shered/cus_header.jsp" %>
    <main>
        <div class="bodymsg">
            <div class="msg">
                
                <h2>ドリンクのサイズをお選びください</h2>
            </div>
            <div class="container">
                <!-- 左側：画像領域 -->
                <div>
                    <div class="left-box">画像</div>
                    <h1>生ビール</h1>
                    <h2>600円(税込)</h2>
                </div>
                <!-- 右側 -->
                <div class="right-box">  
                    <div class="pad">
                        <h3>Sサイズ</h3>
                        <h3>Mサイズ+¥30</h3>
                        <h3>Lサイズ+¥50</h3> 
                    </div>
                </div>
            </div>
            
        </div>
        <div class="action-buttons">
            <button type="button" class="btn-back" onclick="location.href='item_detail_change.jsp'">戻る</button>
            <button type="submit" class="btn-next" onclick="location.href='item_num_change.jsp'">次へ</button>
        </div>
        
    </main>
    <%@ include file="/shered/cus_footer.jsp" %>
</body>
</html>