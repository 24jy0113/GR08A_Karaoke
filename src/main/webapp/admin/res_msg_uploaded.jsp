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
    <title>予約情報登録完了画面</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/10_04.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>

<body>
    <!-- Header -->
    <%@ include file="/shered/biz_header.jsp" %>
    <main>
        <div class="bodymsg">
            <div>
                
                <h2>予約情報取得完了</h2>
                <div class="msg">
                    <h3>予約情報の読み込みが完了しました！</h3>
                </div>     
                <div class="action-buttons">
                    <button type="button" class="btn-back" onclick="location.href='<%= request.getContextPath() %>/ResListManagerServlet'">予約情報画面へ戻る</button>
                </div>
            </div>
        </div>
        
    </main>
    
</body>
</html>