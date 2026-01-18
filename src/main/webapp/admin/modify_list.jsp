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
    <title>該当商品詳細画面-管理者</title>
    <link rel="stylesheet" type="text/css" href="../css/08_02.css">
    <link rel="stylesheet" type="text/css" href="../css/header.css">
</head>

<body>
    <!-- Header -->
    <header>
        <div class="header_inner">
            <img class="title_img" src="../img/logo.png" alt="logo" width="60" height="60">
            <h1 class="title_name">七福サウンド</h1>
            <nav class="gnav">
                <ul class="gnav_list">
                     <li><img class="user_img" src="../img/user.png" alt="cart" width="25" height="25"><%= user.getUserName() %></li>
                    <li><a class="gnav_botton" href="<%= request.getContextPath() %>/LogoutServlet">ログアウト</a></li>
                </ul>
            </nav>
        </div>
    </header>
    <main>
        <div class="bodymsg">
            <div class="msg">
                <h2>該当商品一覧</h2>
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
                        <td><a  type="button" class="update-link" href='modify_update.jsp'>変更</a></td>
                        <td><a type="button" class="delete-link" href='modify_delete.jsp'>削除</a></td>             
                    </tr>
                    <!-- 2行目 -->
                    <tr>
                        <td><img src="" alt="salad"></td>
                        <td>サラダ</td>
                        <td>300円（税込）</td>
                        <td>なし</td>
                        <td><a  type="button" class="update-link" href='modify_update.jsp'>変更</a></td>
                        <td><a type="button" class="delete-link" href='modify_delete.jsp'>削除</a></td>               
                    </tr>
                </table>
        
                <div class="action-buttons">
                    <button type="button" class="btn-back" onclick="location.href='modify_search.jsp'">商品検索画面へ戻る</button>
                    <button type="submit" class="btn-next" onclick="location.href='modify_add.jsp'">商品新規追加</button>
        
                </div>
            </div>
           
        </div>
    </main>
    
    
</body>
</html>