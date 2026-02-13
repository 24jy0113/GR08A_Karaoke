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
    <title>商品削除完了画面</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/08_07.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
    <!-- Header -->
    <%@ include file="/shered/biz_header.jsp" %>
    <main>
        <div class="bodymsg">
            <div class="msg">
                <h2>商品削除完了</h2>
                <h3>商品情報の削除が完了しました</h3>
                <div class="action-buttons">
                    
                    <button type="button" class="btn-back" onclick="location.href='modify_search.jsp'">商品検索画面へ戻る</button>
                   
                </div>
            </div>
           
        </div>
    </main>
    
</body>
</html>