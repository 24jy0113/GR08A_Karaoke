<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">

<head>
    <meta charset="UTF-8">
    <title>料金設定表</title>
    <link rel="stylesheet" type="text/css" href="./css/04_01.css">
    <link rel="stylesheet" type="text/css" href="./css/header.css">
</head>

<body>
    <!-- Header -->
    <header>
        <div class="header_inner">
            <img class="title_img" src="./img/logo.png" alt="logo" width="60" height="60">
            <h1 class="title_name">七福サウンド</h1>
            <nav class="gnav">
                <ul class="gnav_list">
                    <li><a href="cus_top.jsp">トップへ</a></li>
                    <li><a href="time_extend.jsp">延長申請</a></li>
                    <li><a href="item_search.jsp">メニューを番号で探す</a></li>
                    <li><a href="item_list.jsp">フード・ドリンク</a></li>
                    <li><a href="cus_purchase_history.jsp">注文履歴</a></li>
                    <li><a class="gnav_botton" href="cart_detail.jsp">
                            <img class="cart_img" src="./img/cart.png" alt="cart" width="20" height="20">カート内容を確認
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    </header>
    <main>
        <div class="bodymsg">
            <div class="msg">
                <h1>料金設定表</h1>
                <table class="table_border">
                    <tr>
                        <th></th>
                        <th>時間帯</th>
                        <th>一般料金</th>
                        <th>学割・シニア割</th>
                    </tr>
                    <tr>
                        <th>月～金・祝日前</th>
                        <td>9時～19時</td>
                        <td>240円</td>
                        <td>190円</td>
                    </tr>
                    <tr>
                        <th>土・日・祝日</th>
                        <td>9時～19時</td>
                        <td>230円</td>
                        <td>280円</td>
                    </tr>
                    <tr>
                        <th>月～木・日・祝日</th>
                        <td>19時～22時</td>
                        <td>490円</td>
                        <td>400円</td>
                    </tr>
                    <tr>
                        <th>金・土・祝日前</th>
                        <td>19時～22時</td>
                        <td>540円</td>
                        <td>450円</td>
                    </tr>
                </table>
                <div class="action-buttons">
                    <button type="button" class="btn-back" onclick="location.href='time_extend.jsp'">延長申請へ戻る</button>
                </div>
            </div>
           
        </div>
        <div class="footer-wrap"><h1>部屋番号　101</h1><h1>残り時間　10分</h1></div>
    </main>

</body>

</html>