<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=ja>
<head>
    <meta charset="UTF-8">
    <title>アカウント登録</title>
    <link rel="stylesheet" type="text/css" href="./css/01_06.css">
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
        <div class="main-container">
            <h1 class="bodymsg">アカウント情報の変更</h1>
            <p style="text-align: center;margin-bottom: 30px;">変更するアカウント情報を入力してください</p>
            <h1>アカウント情報</h1>
            <form action="account_update_confirm.jsp" method="get" onsubmit="return validateForm()">
                <div class="form-row">
                  <label>権限</label>
                  <div class="required-badge required">必須</div>
                    <div class="checkbox-group">
                        <label><input type="checkbox"> フロント</label>
                        <label><input type="checkbox"> フロア</label>
                        <label><input type="checkbox"> キッチン</label>
                    </div>
                </div>
            
               
                <div class="form-row">
                  <label>アカウント名</label>
                  <div class="required-badge required">必須</div>
                  <input type="text">
                </div>
                <div class="form-row">
                    <label>新しいパスワード</label>
                    <div class="required-badge required">必須</div>
                    <input
                        type="password"
                        id="password"
                        name="newPassword"
                        placeholder="半角英数字のみ、8 桁以上、12 桁以下"
                    >
                </div>
                <div class="form-row">
                    <label>新しいパスワード（再入力）</label>
                    <div class="required-badge required">必須</div>
                    <input
                        type="password"
                        id="confirmPassword"
                        name="newPasswordConfirm"
                        placeholder="確認のため、再度ご入力ください"
                    >
                </div>
                <div class="form-row">
                    <label></label>
                    <input type="checkbox" onclick="togglePassword()"> パスワードを表示
                </div>
                <span id="errorMessage" style="text-align: center;color: chocolate;"></span><br>
                <div class="action-buttons">
                    <button type="button" class="btn-back" onclick="location.href='account_search_result.jsp'">キャンセル</button>
                    <button type="submit" class="btn-next">確認する</button>
                </div>
            </form>
        </div>
    </main>
    <script>

      function togglePassword() {
          var passwordField = document.getElementById("password");
          var confirmPasswordField = document.getElementById("confirmPassword");
          if (passwordField.type === "password") {
              passwordField.type = "text";
              confirmPasswordField.type = "text";
          } else {
              passwordField.type = "password";
              confirmPasswordField.type = "password";
          }
      }

      function validateForm() {

          const pwd1 = document.getElementById("password").value;
          const pwd2 = document.getElementById("confirmPassword").value;
          const error = document.getElementById("errorMessage");

          // 半角英数字のみ、8桁以上、12桁以下
          const pwdPattern = /^[a-zA-Z0-9]+$/;
          if (!pwdPattern.test(pwd1) || pwd1.length < 8 || pwd1.length > 12) {
              error.textContent = "パスワードは半角英数字のみ、8桁以上、12桁以下で入力してください";
              return false;
          }

          if (pwd1 !== pwd2) {
              error.textContent = "入力されたパスワードが不一致です";
              return false;
          }
      }
  </script>
</body>
</html>