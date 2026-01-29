<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>カート内容</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/07_01.css">
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
                    <li><a href="<%= request.getContextPath() %>/cus_purchase_history.jsp">注文履歴</a></li>
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
            <div class="msg">
                <h2>カート内容</h2>
                <p>カートの内容を確認し、注文へ進む場合は「注文へ進む」を押してください</p>
                <table>
                   
                    <tr>
                        <th>サラダ</th>
                        <th>個数</th>
                        <th>小計</th>
                        <th><a href="item_detail_change.jsp" type="button" class="update-link">個数・オプションを変更する</a></th>
                    </tr>
                    
                    <!-- 1行目 -->
                    <tr>
                        <td>300円（税込）</td>
                        <td>1</td>
                        <td>300円（税込）</td>             
                    </tr>
                    <tr>
                        <td><a href="#">削除する</a></td></a>
                    </tr>
                    <tr>
                       
                        <th>生ビール　M</th>
                        <th>個数</th>
                        <th>小計</th>
                        <th><a href="item_detail_change.jsp" type="button" class="update-link">個数・オプションを変更する</a></th>
                    </tr>
                    
                    <!-- 2行目 -->
                    <tr>
                        <td>300円（税込）</td>
                        <td>2</td>
                        <td>600円（税込）</td>             
                    </tr>
                    <tr>
                        <td><a href="#">削除する</a></td></a>
                    </tr>
                    <tr>
                       
                        <th>チキン</th>
                        <th>個数</th>
                        <th>小計</th>
                        <th><a href="<%= request.getContextPath() %>/item_detail_change.jsp" type="button" class="update-link">個数・オプションを変更する</a></th>
                    </tr>
                    
                    <!-- 3行目 -->
                    <tr>
                        <td>1000円（税込）</td>
                        <td>1</td>
                        <td>1000円（税込）</td>             
                    </tr>
                    <tr>
                        <td><a href="#">削除する</a></td></a>
                    </tr>
                </table>
                <section class="order-summary">
                    <div class="summary-row">
                        <div class="summary-label">商品合計金額:</div>
                        <div class="summary-value">1,960円(税込)</div>
                    </div>
                </section>
                <div class="action-buttons">
                    <button type="button" class="btn-back" onclick="history.back()">トップページへ戻る</button>
                    <button type="submit" class="btn-next" onclick="location.href='cart_detail_confirm.jsp'">注文へ進む</button>        
                </div>
            </div>
           
        </div>
        
        <div class="footer-wrap"><h1>部屋番号　101</h1><h1>残り時間　50分</h1></div>
    </main>
    
    
</body>
</html>