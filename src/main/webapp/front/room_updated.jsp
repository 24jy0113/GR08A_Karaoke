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
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>部屋状況更新完了画面</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/10_04.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
    <!-- Header -->
    <%@ include file="/shered/biz_header.jsp" %>
    <main>
        <div class="bodymsg">
            <div>
                
                <h2>部屋状況更新完了</h2>
                <div class="msg">
                    <h3>部屋状況の更新が完了しました</h3>
                </div>
                <div class="action-buttons">
                    <button type="button" class="btn-back" onclick="location.href='<%= request.getContextPath() %>/RoomListServlet'">部屋状況画面へ戻る</button>
                </div>
            </div>
        </div>
        
    </main>
    
</body>
</html>