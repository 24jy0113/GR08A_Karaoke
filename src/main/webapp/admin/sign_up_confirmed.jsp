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
    <title>アカウント登録完了画面</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/default.css">
</head>
<body>
    <!-- Header -->
    <%@ include file="/shered/biz_header.jsp" %>
    <main>
        <div>
            <h1 class="bodytitle text-center">アカウント登録完了</h1>
            <p class="bodymsg text-center">アカウント登録が完了しました</p>
            
            <div class="action-buttons flex-center">
                <button type="button" class="btn-back" onclick="location.href='account_search.jsp'">アカウント検索へ戻る</button>
            </div>
        </div>
    </main>
    
</body>
</html>