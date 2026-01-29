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
    <title>商品更新入力画面-管理者</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/08_03.css">
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
            <h1>商品情報更新入力</h1>
        </div>
        <div class="container">
            
            <!-- 右側：検索欄＋テンキー -->
            <div class="right-box">
                <div class="input-row">
                    <label>商品名</label>
                    <input id="menuInput" type="text" />
                </div>
                <div class="input-row">
                    <label>単価</label>
                    <input id="menuInput" type="text" />円　　　＊税込価格
                </div>
                <div class="input-row">
                    <label>商品画像</label>
                    <input type="file" name="image" accept=".png, .jpg, .jpeg">
                    <button type="submit"><img src="./img/upload.png" alt="upload" style="border: none;"></button>
                    <p>＊JPG, JPEG, PNGのみ</p>
                </div>
                <div class="input-row">
                    <label>注文番号</label>
                    <input id="menuInput" type="text" />
                </div>
                <div class="input-row">
                    <label>カテゴリー</label>
                    <select class="category-select">
                    <c:foreach var="category" items="${modify_update}">
                        <option value="${category.key }">${category.value}</option>                    
                    </c:foreach>
                    </select>
                
                </div>
                <div class="input-row">
                    <label>在庫</label>
                    <label><input type="radio" name="stock" value="あり" checked> あり</label>
                    <label><input type="radio" name="stock" value="なし"> なし</label>
                </div>
                <div class="input-row">
                    <label>オプション</label>
                    <label><input type="radio" name="option" value="あり" checked> あり</label>
                    <label><input type="radio" name="option" value="なし"> なし</label>
                </div>
            </div>
        </div>
        <div class="action-buttons">
            <button type="button" class="btn-back" onclick="location.href='modify_list.jsp'">該当商品一覧へ戻る</button>
            <button type="submit" class="btn-next" onclick="location.href='modify_update_confirm.jsp'">確認する</button>
        </div>
    </main>
    
</body>
</html>