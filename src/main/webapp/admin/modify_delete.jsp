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
    <title>商品削除確認画面</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/08_06.css">
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
    <!-- Header -->
    <header>
        <div class="header_inner">
            <img class="title_img" src="<%= request.getContextPath() %>/img/logo.png" alt="logo" width="60"
				height="60">
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
                <h2>商品削除警告</h2>
                <h3>本当にこの商品情報を削除しますか？</h3>
                <h3 style="color: chocolate;">※この操作は元に戻せません。</h3>
               
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
                    
                    <button type="button" class="btn-back" onclick="modify_list.jsp'">キャンセル</button>
                    <button type="submit" class="btn-next" onclick="location.href='modify_deleted.jsp'">削除を確定する</button>
                </div>
            </div>
           
        </div>
    </main>
    
</body>
</html>