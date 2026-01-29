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
    <title>商品更新完了画面-キッチン</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/08_15.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
    <!-- Header -->
    <header>
        <div class="header_inner">
            <img class="title_img" src="<%= request.getContextPath() %>/img/logo.png" alt="logo" width="60" height="60">
            <h1 class="title_name">七福サウンド</h1>
            <nav class="gnav">
                <ul class="gnav_list">
                     <li><img class="user_img" src="<%= request.getContextPath() %>/img/user.png" alt="cart" width="25" height="25"><%= user.getUserName() %></li>
                    <li><a class="gnav_botton" href="<%= request.getContextPath() %>/LogoutServlet">ログアウト</a></li>
                </ul>
            </nav>
        </div>
    </header>
    <main>
        <div class="bodymsg">
            <div class="msg">
                <h2>商品情報更新完了</h2>
                <p>商品情報の更新が完了しました。</p>
                <table>
                   
                    <tr>
                        <th>商品画像</th>
                        <th>商品名</th>
                        <th>単価</th>
                        <th>在庫</th>
                        <th></th>
                        <th></th>
                    </tr>
                    <!-- 1行目 -->
                    <tr>
                        <td><img src="" alt="salad"></td>
                        <td>サラダ</td>
                        <td>300円（税込）</td>
                        <td>あり</td>
                                  
                    </tr>
                    
                </table>
        
                <div class="action-buttons">                
                    <button type="submit" class="btn-next" onclick="location.href='<%= request.getContextPath() %>/modify_search_kitchen.jsp'">商品検索画面へ戻る</button>
                    <button type="button" class="btn-back" onclick="location.href='<%= request.getContextPath() %>/kitchen_order_list.jsp'">注文情報画面へ戻る</button>
                </div>
            </div>
           
        </div>
    </main>
    
</body>
</html>