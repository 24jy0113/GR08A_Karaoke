<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.User"%>
<%@ taglib prefix="auth" uri="/auth"%>
<%
User user = (User) session.getAttribute("loginUser");
if (user == null) {
	response.sendRedirect(request.getContextPath() + "/index.jsp");
	return;
}
%>
<%
String signupUserName = (String) session.getAttribute("SIGNUP_USER_NAME");
String signupRoleName = (String) session.getAttribute("SIGNUP_ROLE_NAME");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>アカウント登録</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/01.css">

</head>
<body>
	<%@ include file="/shared/biz_header.jsp"%>
	<main>
		<div class="text-center">
			<h1 class="bodytitle">アカウント登録</h1>
			<p class="bodymsg">下記の情報を入力してください</p>
			<c:if test="${ error != null}">
				<p class="errormsg">${error}</p>
			</c:if>
			<h2 class="text-left">アカウント情報</h2>
			<form action="<%=request.getContextPath()%>/SignUpConfirmServlet"
				method="POST" onsubmit="return validateForm()">
				<div class="form-row">
					<label>役割</label>
					<div class="required-badge required">必須</div>
					<div class="radio-group">
						<label>
						  <input type="radio" name="roleName" value="キッチン"
						    <%= "キッチン".equals(signupRoleName) ? "checked" : "" %>>
						  キッチン
						</label>
						
						<label>
						  <input type="radio" name="roleName" value="フロント"
						    <%= "フロント".equals(signupRoleName) ? "checked" : "" %>>
						  フロント
						</label>
						
						<label>
						  <input type="radio" name="roleName" value="フロア"
						    <%= "フロア".equals(signupRoleName) ? "checked" : "" %>>
						  フロア
						</label>
						
						<label>
						  <input type="radio" name="roleName" value="管理者"
						    <%= "管理者".equals(signupRoleName) ? "checked" : "" %>>
						  管理者
						</label>

					</div>
				</div>
				<div class="form-row">
					<label>アカウント名</label>
					<div class="required-badge required">必須</div>
					<input type="text" name="userName"value="<%= signupUserName != null ? signupUserName : "" %>">

				</div>
				<div class="form-row">
					<label>パスワード</label>
					<div class="required-badge required">必須</div>
					<input type="password" id="password" name="password"
						placeholder="半角英数字のみ、8 桁以上、12 桁以下">
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
				<span id="errorMessage" class="text-center"></span><br>
				<div class="action-buttons flex-center">
					<button type="button" class="btn-back"
						onclick="location.href='<%=request.getContextPath()%>/admin/account_search.jsp'">キャンセル</button>
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
    	  const userName = document.querySelector('input[name="userName"]').value.trim();
          const pwd1 = document.getElementById("password").value;
          const pwd2 = document.getElementById("confirmPassword").value;
          const error = document.getElementById("errorMessage");

          if (userName === "") {
              error.textContent = "アカウント名を入力してください";
              return false;
          }

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