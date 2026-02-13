<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.*"%>
<%
Room room = (Room) session.getAttribute("room");
Integer remainingMinutes = (Integer) session.getAttribute("remainingMinutes");
%>
<%
Integer orderNo = (Integer) session.getAttribute("orderNo");
if (orderNo == null) {
    response.sendRedirect(request.getContextPath() + "/cus_top.jsp");
    return;
}
%>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>注文完了画面</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/07_01.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
    <!-- Header -->
    <%@ include file="/shered/cus_header.jsp" %>
    <main>
        <div class="bodymsg">
            <div class="msg">
                <h2>ご注文の完了</h2>
                <p>ご注文いただき、誠にありがとうございました</p>
                <h1 style="color: rgb(17, 106, 223);">受取番号：<%= String.format("%04d", session.getAttribute("orderNo")) %></h1>
                <p>商品を受け取る際に、受取番号をスタッフにお伝えください</p>
                
                <div class="action-buttons">
                    
                    <button type="button" class="btn-back" onclick="location.href='<%= request.getContextPath() %>/cus_top.jsp'">トップページへ戻る</button>
        
                </div>
                <%
				session.removeAttribute("orderNo");
				%>
            </div>
           
        </div>
        
        <div class="footer-wrap">
            <% if (room != null) { %>
  				<h1>部屋番号　<%= room.getRoomNo() %></h1>
      　	　　<% } %>
      	　　<% if (remainingMinutes != null) { %>
        	 	<h1>残り時間　<%= remainingMinutes %>　分</h1>
         　<% } %>
        </div>
    </main>
    
    <%@ include file="/shered/cus_footer.jsp" %>
</body>
</html>