<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="model.User"%>
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
<title>商品${item.id<1?追加:更新 }入力画面-管理者</title>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/08_03.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
</head>
<body>
	<!-- Header -->
	<header>
		<div class="header_inner">
			<img class="title_img"
				src="<%=request.getContextPath()%>/img/logo.png" alt="logo"
				width="60" height="60">
			<h1 class="title_name">七福サウンド</h1>
			<nav class="gnav">
				<ul class="gnav_list">
					<li><img class="user_img"
						src="<%=request.getContextPath()%>/img/user.png" alt="cart"
						width="25" height="25"><%=user.getUserName()%></li>
					<li><a class="gnav_botton"
						href="<%=request.getContextPath()%>/LogoutServlet">ログアウト</a></li>
				</ul>
			</nav>
		</div>
	</header>
	<main>
		<div class="bodymsg">
			<h1>商品情報${item.id<1?追加:更新 }入力</h1>
		</div>
		<form method="get"
			action="<%=request.getContextPath()%>/ItemEditServlet">
			<div class="container">

				<input type="hidden" name="cmd" value="confirm">
				<div class="right-box">
					<div class="input-row">
						<label>商品名</label> <input class="menuInput" type="text"
							name="name" value="${item.name }" />
					</div>
					<div class="input-row">
						<label>単価</label> <input class="menuInput" type="text"
							name="price" value="${item.price }" />円 ＊税込価格
					</div>
					<div class="input-row">
						<label>商品画像</label> <input type="file" name="image"
							accept=".png, .jpg, .jpeg">
						<p>＊JPG, JPEG, PNGのみ</p>
					</div>
					<div class="input-row">

						<label>注文番号</label> <input class="menuInput" type="text"
							name="order_number" value="${item.itemNo }" readonly />
					</div>
					<div class="input-row">
						<label>カテゴリー</label> <select id="category-select"
							class="category-select" name="category">
							<c:forEach var="category" items="${categoryList}">
								<option value="${category.key }"
									${item.categoryId == category.key ? "selected" : ""}>${category.value}</option>
							</c:forEach>
						</select>

					</div>
					<div class="input-row">
						<label>在庫</label> <label><input type="radio" name="stock"
							value="あり" ${item.isStock() ? "checked" : ""}> あり</label> <label><input
							type="radio" name="stock" value="なし"
							${!item.isStock() ? "checked" : ""}> なし</label>
					</div>
					<div class="input-row">
						<label>オプション</label> <label><input type="radio"
							id="radioOption" value="あり" ${item.hasOption() ? "checked" : ""}>
							あり</label> <label><input type="radio" id="radioNonOption"
							value="なし" ${!item.hasOption() ? "checked" : ""}> なし</label>
					</div>
					<!-- オプション全体 -->
					<div id="optionArea"></div>
					<script type="text/javascript">
						const resMap = JSON.parse('${optionList}');
						const initialSelectedIds = [
							<c:forEach items="${item.getOptionList()}" var="o" varStatus="s">
					            ${o.id}${!s.last ? ',' : ''}
					        </c:forEach>
					        ];
						let radio1 = document.getElementById("radioOption");
						let radio2 = document.getElementById("radioNonOption");
						let optionArea = document.getElementById("optionArea");
						let category = document.getElementById("category-select");

						function updateDisplay() {
							let checkboxes = optionArea.querySelectorAll('input[type="checkbox"]');
							if (radio1.checked) {
								optionArea.style.display = "block";
								checkboxes.forEach(cb => cb.disabled = false);
							} else {
								optionArea.style.display = "none";
								checkboxes.forEach(cb => cb.disabled = true);
							}

						}

						function updateOptionArea(categoryId) {
						    
							optionArea.innerHTML = '';
						    const options = resMap[String(categoryId).trim()];

						    if (!options || options.length === 0) {
						    	optionArea.innerHTML = '<div class="option-block">オプションはありません</div>';
						        return;
						    }

						    let html = '<div class="option-block">オプションを選択してください<br>';
						    
						    options.forEach(opt => {
						        const isChecked = initialSelectedIds.includes(opt.id) ? 'checked' : '';

						        html += `
						            <label>
						                <input type="checkbox" name="option[]" value="\${opt.id}" \${isChecked}>
						                \${opt.name}
						            </label>
						        `;
						    });

						    html += '</div>';

						    optionArea.insertAdjacentHTML('afterbegin', html);
						}
						
						// クリックイベントの設定.
						radio1.addEventListener("change", () => { if(radio1.checked) radio2.checked = false; updateDisplay(); });
						radio2.addEventListener("change", () => { if(radio2.checked) radio1.checked = false; updateDisplay(); });
						category.addEventListener("change", () => { updateOptionArea(category.value); });

						// ページ読み込み時の初期状態を反映.
						updateDisplay();
						updateOptionArea(${item.categoryId});
					</script>
				</div>
			</div>
			<div class="action-buttons">
				<button type="button" class="btn-back" onclick="history.back()">該当商品一覧へ戻る</button>
				<input type="submit" class="btn-next" value="確認する">
			</div>
		</form>
	</main>

</body>
</html>