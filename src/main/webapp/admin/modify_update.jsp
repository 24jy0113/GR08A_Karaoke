<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="model.User"
%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
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
            
            
            <div class="right-box">
                <div class="input-row">
                    <label>商品名</label>
                    <input id="menuInput" type="text" value="${item.name }" />
                </div>
                <div class="input-row">
                    <label>単価</label>
                    <input id="menuInput" type="text" value="${item.price }" />円　　　＊税込価格
                </div>
                <div class="input-row">
                    <label>商品画像</label>
                    <input type="file" name="image" accept=".png, .jpg, .jpeg">
                    <button type="submit"><img src="./img/upload.png" alt="upload" style="border: none;"></button>
                    <p>＊JPG, JPEG, PNGのみ</p>
                </div>
                <div class="input-row">
                
                    <label>注文番号</label>
                    <input id="menuInput" type="text" value="${item.itemNo }" />
                </div>
                <div class="input-row">
                    <label>カテゴリー</label>
                    <select class="category-select">
                    <c:forEach var="category" items="${categoryList}">
                        <option value="${category.key }">${category.value}</option>                    
                    </c:forEach>
                    </select>
                
                </div>
                <div class="input-row">
                    <label>在庫</label>
                    <label><input type="radio" name="stock" value="あり" ${item.isStock() ? "checked" : ""}> あり</label>
                    <label><input type="radio" name="stock" value="なし" ${!item.isStock() ? "checked" : ""}> なし</label>
                </div>
                <div class="input-row">
                    <label>オプション</label>
                    <label><input type="radio" name="option" value="あり"  ${item.options.isEmpty() ? "checked" : ""}> あり</label>
                    <label><input type="radio" name="option" value="なし"  ${!item.options.isEmpty() ? "checked" : ""}> なし</label>
                </div>
                <c:if test="item.options.isEmpty()">
                <!-- オプション全体 -->
                <div id="optionArea">
                <c:forEach items="item.getOptionList()" var="item_option">
                    <div class="option-block">
                        オプションを選択してください
                        <c:forEach items="optionlist" var="option">
	                        <label><input type="checkbox" value="${option.key }">${option.value }</label>
                        </c:forEach>
                    	 <button class="delete-btn" type="button"><img src="../img/delete.svg" alt="delete"></button>
                    </div>
                </c:forEach>
                </div>
                <!-- ▼ オプション追加ボタン ▼ -->
                <button id="addOptionBtn" class="add-option-btn">オプションを追加</button>
                </c:if>
            </div>
            </div>
        </div>
        <div class="action-buttons">
            <button type="button" class="btn-back" onclick="history.back()">該当商品一覧へ戻る</button>
            <button type="submit" class="btn-next" onclick="location.href='modify_update_confirm.jsp'">確認する</button>
        </div>
    </main>
    
</body>
</html>