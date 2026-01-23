<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="model.User"
%>
<%@ taglib prefix="auth" uri="/auth" %>
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
<title>アカウント登録</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/01_01.css">
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
	    <% String error = (String) request.getAttribute("error"); %>

		<% if (error != null) { %>
		    <div style="
		        color: red;
		        text-align: center;
		    ">
		        <%= error %>
		    </div>
		<% } %>
	<main>
		<div class="main-container">
			<h1 class="bodymsg">アカウント登録</h1>
			<p style="text-align: center; margin-bottom: 30px;">下記の情報を入力してください</p>
			<h1>アカウント情報</h1>
			<form action="<%=request.getContextPath()%>/SignUpConfirmServlet" method="post"  onsubmit="return validateForm()">

				<div class="form-row">
					<label>役割</label>
					<div class="required-badge required">必須</div>
					<div class="radio-group">
					  <label><input type="radio" name="roleName" value="キッチン"> キッチン</label>
					  <label><input type="radio" name="roleName" value="フロント"> フロント</label>
					  <label><input type="radio" name="roleName" value="フロア"> フロア</label>
					  <label><input type="radio" name="roleName" value="管理者"> 管理者</label>
					</div>


				</div>

				<div class="form-row">
					<label>アカウント名</label>
					<div class="required-badge required">必須</div>
					<input type="text" name="userName">

				</div>
				<div class="form-row">
					<label>パスワード</label>
					<div class="required-badge required">必須</div>
					<input type="password" id="password" name="password" placeholder="半角英数字のみ、8 桁以上、12 桁以下">
				</div>
				<div class="form-row">
					<label>パスワード（再入力）</label>
					<div class="required-badge required">必須</div>
					<input type="password" id="confirmPassword"
						name="newPasswordConfirm" placeholder="確認のため、再度ご入力ください">
				</div>
				<div class="form-row">
					<label></label> <input type="checkbox" onclick="togglePassword()">
					パスワードを表示
				</div>
				<span id="errorMessage"
					style="text-align: center; color: chocolate;"></span><br>
				<div class="action-buttons">
					<button type="button" class="btn-back"
						onclick="history.back()">キャンセル</button>
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
          const roleChecked = document.querySelector('input[name="roleName"]:checked');
          if (!roleChecked) {
              error.textContent = "役割を選択してください";
              return false;
          }

          return true;
      }
  </script>
</body>
</html>