<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=ja>
<head>
    <meta charset="UTF-8">
    <title>カート追加確認画面-変更時</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/06_06.css">
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
                    <li><a href="<%= request.getContextPath() %>/cus_top.jsp">トップへ</a></li>
                    <li><a href="<%= request.getContextPath() %>/time_extend.jsp">延長申請</a></li>
                    <li><a href="<%= request.getContextPath() %>/item_search.jsp">メニューを番号で探す</a></li>
                    <li><a href="<%= request.getContextPath() %>/item_list.jsp">フード・ドリンク</a></li>
                    <li><a href="<%= request.getContextPath() %>/cusPurchaseHistory">注文履歴</a></li>
                    <li><a class="gnav_botton" href="cart_detail.jsp">
                            <img class="cart_img" src="<%= request.getContextPath() %>/img/cart.png" alt="cart" width="20" height="20">カート内容を確認
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    </header>
    <main>
        <div class="bodymsg">
            
            <div class="container">
                <!-- 左側：画像領域 -->
                <div>
                    <div class="left-box">画像</div>
                    <h1>生ビール　　600円(税込)</h1>
                </div>
                <!-- 右側 -->
                <div class="right-box">
                    <h2>オプション</h2>
                    <h4>Mサイズ</h4>
                    <h2>個数</h2>
                    <h4>1</h4>
                    
                </div>
            </div>
        </div>
        <div class="action-buttons">
            <button type="button" class="btn-back" onclick="history.back()">戻る</button>
            <button type="submit" class="btn-next" onclick="location.href='cart_detail.jsp'">変更を確定する</button>
        </div>
        <div class="footer-wrap">
            <h1>部屋番号　101</h1>
            <h1>残り時間　50分</h1>
        </div>
    </main>
    
</body>
</html>