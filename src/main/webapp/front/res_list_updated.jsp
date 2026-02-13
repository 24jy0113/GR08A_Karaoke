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
    <title>予約情報更新完了画面</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/10_02.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
    <!-- Header -->
    <%@ include file="/shered/biz_header.jsp" %>
    <main>
        <div class="bodymsg">
            <div>
                <div></div>
                <h2>予約更新完了</h2>
                <h3>予約情報の更新が完了しました</h3>

                <div class="action-buttons">
                    <button type="button" class="btn-back" onclick="location.href='<%= request.getContextPath() %>/ResListFrontServlet'">予約情報画面へ戻る</button>
                </div>
            </div>
        </div>
        
    </main>
    
</body>
</html>