<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>注文履歴一覧画面</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/05_01.css">
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
                <h2>注文履歴一覧</h2>
                <h3>受取番号_S1101101</h3>
                <table>
                    <tr>
                        <th></th>
                        <th>単価</th>
                        <th>オプション価格</th>
                        <th>個数</th>
                        <th>小計</th>
                    </tr>
                    
                    <!-- 1行目 -->
                    <tr>
                        <td>サラダ</td>
                        <td>300円（税込）</td>
                        <td>-</td>
                        <td>1</td>
                        <td>300円（税込）</td>             
                    </tr>
                    <tr>
                        <th></th>
                        <th>単価</th>
                        <th>オプション価格</th>
                        <th>個数</th>
                        <th>小計</th>
                    </tr>
                    
                    <!-- 2行目 -->
                    <tr>
                        <td>生ビール　M</td>
                        <td>300円（税込）</td>
                        <td>＋30円</td>
                        <td>2</td>
                        <td>600円（税込）</td>             
                    </tr>
                    <tr>
                        <th></th>
                        <th>単価</th>
                        <th>オプション価格</th>
                        <th>個数</th>
                        <th>小計</th>
                    </tr>
                    
                    <!-- 3行目 -->
                    <tr>
                        <td>チキン</td>
                        <td>1000円（税込）</td>
                        <td>-</td>
                        <td>1</td>
                        <td>1000円（税込）</td>             
                    </tr>
                </table>
                <h3>スタッフがお部屋までお届け</h3>
                <table>
                    <tr>
                        <th></th>
                        <th>単価</th>
                        <th>オプション価格</th>
                        <th>個数</th>
                        <th>小計</th>
                    </tr>
                    
                    <!-- 1行目 -->
                    <tr>
                        <td>サラダ</td>
                        <td>300円（税込）</td>
                        <td>-</td>
                        <td>1</td>
                        <td>300円（税込）</td>             
                    </tr>
                    <tr>
                        <th></th>
                        <th>単価</th>
                        <th>オプション価格</th>
                        <th>個数</th>
                        <th>小計</th>
                    </tr>
                    
                    <!-- 2行目 -->
                    <tr>
                        <td>生ビール　M</td>
                        <td>300円（税込）</td>
                        <td>＋30円</td>
                        <td>2</td>
                        <td>600円（税込）</td>             
                    </tr>
                    <tr>
                        <th></th>
                        <th>単価</th>
                        <th>オプション価格</th>
                        <th>個数</th>
                        <th>小計</th>
                    </tr>
                    
                    <!-- 3行目 -->
                    <tr>
                        <td>チキン</td>
                        <td>1000円（税込）</td>
                        <td>-</td>
                        <td>1</td>
                        <td>1000円（税込）</td>             
                    </tr>
                </table>
                <h4>合計3,920円（税込）＋ 室料他</h4>
                <div class="action-buttons">
                    
                    <button type="button" class="btn-back" onclick="history.back()">トップページへ戻る</button>
                </div>
            </div>
        </div>
        <div class="footer-wrap"><h1>部屋番号　101</h1><h1>残り時間　50分</h1></div>
    </main>
    
</body>
</html>