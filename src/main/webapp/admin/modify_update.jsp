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
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>商品${editItem.id < 1 ? "追加" : "更新" }入力画面-${isAdmin ? "管理者" : "キッチン" }</title>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/header.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/default.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/css/08_03.css">
</head>
<body>
	<%@ include file="/shared/biz_header.jsp"%>
	<main>
	<c:choose>
				<c:when test="${isAdmin }">
		<h1 class="bodytitle text-center">商品情報${editItem.id < 1 ? "追加" : "更新" }入力</h1>
				</c:when>
				<c:otherwise>
				<h1 class="bodytitle text-center">商品在庫情報更新入力</h1>
				</c:otherwise>
				</c:choose>
		<c:if test="${!param.e.isEmpty() }">
		<p class="errormsg">${param.e}</p>
		</c:if>
		<form method="post" enctype="multipart/form-data">
			<div class="container">
				<div class="right-box">
				<c:choose>
				<c:when test="${isAdmin }">
					<div class="input-row">
						
						<label>商品名
						<c:if test="${editItem.id < 1 }">
						<span class="required-badge required">必須</span>
						</c:if>
						<input class="menuInput" type="text"
							value="${editItem.name }" name="name"></label> 
					</div>
					<div class="input-row">
						<label>単価
						<c:if test="${editItem.id < 1 }"> <span class="required-badge required">必須</span>
						</c:if>
						<input class="menuInput" type="text"
							value="${editItem.price }" name="price">円＊税込価格</label>
					</div>
				</c:when>
				<c:otherwise>
				<div class="input-row">
				<h3 class="bodymsg">商品名</h3><p>${editItem.name }</p>
				</div>
				<div class="input-row">
				<h3 class="bodymsg">単価</h3><p>${editItem.price }円＊税込価格</p>
				</div>
				</c:otherwise>
				</c:choose>
					<div class="input-row">
						<label>商品画像</label>
						<c:if test="${isAdmin }">
							<input type="file" name="image" accept=".png, .jpg, .jpeg">
						</c:if>
						<p>
							＊JPG, JPEG, PNGのみ<br>
							現在の商品画像:${editItem.image.replace("items/", "")}
						</p>
					</div>
					<c:if test='${isAdmin }'>
						<div class="input-row">
							<label>メニュー番号
						<c:if test="${editItem.id < 1 }">
						<span class="required-badge required">必須</span>
						</c:if>
						 <input class="menuInput" type="text"
								name="order_number" value="${editItem.itemNo == 0 ? '未登録' : editItem.itemNo }" />
						</label>
						</div> 
						<div class="input-row">
							<label>カテゴリー</label> <select id="category-select" name="category">
								<c:forEach var="category" items="${categoryList}">
									<option value="${category.key }"
										${editItem.categoryId == category.key ? "selected" : ""}>${category.value}</option>
								</c:forEach>
							</select>

						</div>
					</c:if>
					<div class="input-row">
						<label>在庫</label> <label><input type="radio" name="stock"
							value="true" ${editItem.isStock() ? "checked" : ""}> あり</label> <label><input
							type="radio" name="stock" value="false"
							${!editItem.isStock() ? "checked" : ""}> なし</label>
					</div>
					<c:if test='${isAdmin }'>
						<div class="input-row">
							<label>オプション</label> <label><input type="radio"
								id="radioOption" ${editItem.hasOption() ? "checked" : ""}>
								あり</label> <label><input type="radio" id="radioNonOption"
								${!editItem.hasOption() ? "checked" : ""}> なし</label>
						</div>
						<!-- オプション全体 -->
						<div id="optionArea"></div>
						<script type="text/javascript">
							const resMap = JSON.parse('${optionList}');
							const initialSelectedIds = [
								<c:forEach items="${editItem.optionList}" var="opt" varStatus="s">
						            ${opt.id}${!s.last ? ',' : ''}
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
							                <input type="checkbox" name="option" value="\${opt.id}" \${isChecked}>
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
							updateOptionArea(${editItem.categoryId});
						</script>
					</c:if>
				</div>
			</div>
			<div class="action-buttons flex-center">
				<c:choose>
					<c:when test="${editItem.id<1 }">
						<button type="button" class="btn-back"
							onclick="location.href='${pageContext.request.contextPath}/SearchItemByName?go_top=1';return false;">商品検索画面へ戻る</button>
					</c:when>
					<c:otherwise>
						<button type="button" class="btn-back"
							onclick="location.href='${pageContext.request.contextPath}/SearchItemByName';return false;">該当商品一覧へ戻る</button>
					</c:otherwise>
				</c:choose>
				<button name="cmd" value="confirm" type="submit" class="btn-next"
					formaction="${pageContext.request.contextPath}/ItemEditServlet">確認する</button>
			</div>
		</form>
	</main>

</body>
</html>