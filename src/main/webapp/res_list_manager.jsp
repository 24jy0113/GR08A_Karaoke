<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>予約情報画面</title>
    <link rel="stylesheet" type="text/css" href="./css/10_03.css">
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
                    <li><img class="user_img" src="./img/user.png" alt="cart" width="25" height="25">佐藤 花子</li>
                    <li><a class="gnav_botton" href="./index.jsp">ログアウト</a></li>
                </ul>
            </nav>
        </div>
    </header>
    <main>
        <div class="bodymsg">
            <div>
                <div></div>
                <h2>予約一覧</h2>
                <div class="block">
                    <select name="extend">
                        <option value="30" selected>空き</option>
                        <option value="60">予約</option>
                        <option value="90">受付済み</option>
                        <option value="120">キャンセル</option>
                    </select>
                    <button type="submit">絞り込み</button>
                </div>
                <table>
                    <tr>
                        <th>予約番号</th>
                        <th>部屋番号</th>
                        <th>日付</th>
                        <th>予約受付時間</th>
                        <th>予約退室時間</th>
                        <th>状態</th>
                    </tr>
                    <!-- 1行目 -->
                    <tr>
                        <td>SF20251108</td>
                        <td>101</td>
                        <td>01/11</td>
                        <td>10:30</td>
                        <td>11:30</td>
                        <td>予約</td>
                    </tr>
                    <tr>
                        <td>SF20251109</td>
                        <td>102</td>
                        <td>01/11</td>
                        <td>10:30</td>
                        <td>11:30</td>
                        <td>受付済み</td>
                    </tr>
                    <tr>
                        <td>SF20251110</td>
                        <td>103</td>
                        <td>01/11</td>
                        <td>10:30</td>
                        <td>11:30</td>
                        <td>キャンセル</td>
                    </tr>
                </table>
                <div class="link">
                    <a href="">次のページへ</a>
                </div>
                <div class="action-buttons">
                    
                    <button type="button" class="btn-back" onclick="location.href='manage_top.jsp'">表示選択画面へ</button>
                   
                   <button type="button" class="btn-next" onclick="location.href='res_msg_upload.jsp'">予約情報読み込み</button> 
                    
                </div>
            </div>
        </div>
        
    </main>
    
</body>
</html>