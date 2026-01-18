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
    <title>部屋状況画面</title>
    <link rel="stylesheet" type="text/css" href="../css/11_01.css">
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
            <div>
                <div></div>
                <h2>部屋状況一覧</h2>
                <div class="block">
                    <select name="extend">
                        <option value="30" selected>空き</option>
                        <option value="60">予約</option>
                        <option value="90">受付済み</option>
                        <option value="120">キャンセル</option>
                    </select>
                    <button type="submit">絞り込み</button>
                    <button id="reloadButton">更新</button>

                </div>
                <table>
                    <tr>
                        <th></th>
                        <th>部屋番号</th>
                        <th>酒類提供</th>
                        <th>受付時間</th>
                        <th>退室時間</th>
                        <th>状態</th>
                        <th>予約受付時間</th>
                    </tr>
                    <!-- 1行目 -->
                    <tr>
                        <td><button type="button" onclick="location.href='front_cus_top.jsp'" style="background-color: black;color: aliceblue;">延長・注文</button></td>
                        <td>101</td>
                        <td>
                            <select>
                                <option selected>可能</option>
                                <option>-</option>
                                <option>不可</option>
                            </select>
                        </td>
                        <td><input
                            type="time"
                            id="appointment"
                            name="appointment"
                            value="14:00"
                            required />
                        </td>
                        <td><input
                            type="time"
                            id="appointment"
                            name="appointment"
                            value="15:30"
                            required />
                        </td>
                        <td>
                            <select name="extend">
                                <option>空き</option>
                                <option>予約</option>
                                <option selected>受付済み</option>
                                <option>キャンセル</option>
                            </select>
                        </td>
                        <td>14:30</td>
                    </tr>
                    <tr>
                        <td><button type="button" onclick="location.href='#'">延長・注文</button></td>
                        <td>102</td>
                        <td>
                            <select>
                                <option>可能</option>
                                <option selected>-</option>
                                <option>不可</option>
                            </select>
                        </td>
                        <td><input
                            type="time"
                            id="appointment"
                            name="appointment"
                            required />
                        </td>
                        <td><input
                            type="time"
                            id="appointment"
                            name="appointment"
                            required />
                        </td>
                        <td>
                            <select>
                                <option selected>空き</option>
                                <option>予約</option>
                                <option>受付済み</option>
                                <option>キャンセル</option>
                            </select>
                        </td>
                        <td>14:30</td>
                    </tr>
                    <tr>
                        <td><button type="button" onclick="location.href='front_cus_top.jsp'"style="background-color: black;color: aliceblue;">延長・注文</button></td>
                        <td>103</td>
                        <td>
                            <select>
                                <option>可能</option>
                                <option>-</option>
                                <option selected>不可</option>
                            </select>
                        </td>
                        <td><input
                            type="time"
                            id="appointment"
                            name="appointment"
                            value="14:00"
                            required />
                        </td>
                        <td><input
                            type="time"
                            id="appointment"
                            name="appointment"
                            value="15:30"
                            required />
                        </td>
                        <td>
                            <select>
                                <option>空き</option>
                                <option selected>予約</option>
                                <option>受付済み</option>
                                <option>キャンセル</option>
                            </select>
                        </td>
                        <td>14:30</td>
                    </tr>
                </table>
                <div class="link">
                    <a href="">次のページへ</a>
                </div> 
                <div class="action-buttons">
                    
                    <button type="button" class="btn-back" onclick="location.href='front_top.jsp'">表示選択画面へ</button>
                    <button type="submit" class="btn-next" onclick="location.href='room_updated.jsp'">変更の確定</button>
                </div>
            </div>
        </div>
        
    </main>
    <script>
        // ページ更新ボタンのクリックイベントを設定
        document.getElementById('reloadButton').addEventListener('click', function() {
            location.reload(); // ページをリロード
        });
    </script>
</body>
</html>