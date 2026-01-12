<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang=ja>

<head>
    <meta charset="UTF-8">
    <title>商品更新入力画面-フロント</title>
    <link rel="stylesheet" type="text/css" href="./css/08_08.css">
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
            <h1>追加商品情報入力</h1>
        </div>
        <div class="container">

            <!-- 右側：検索欄＋テンキー -->
            <div class="right-box">
                <div class="input-row">
                    <label>商品名</label>
                    <input id="productName" type="text" />
                </div>
                <div class="input-row">
                    <label>単価</label>
                    <input id="price" type="text" />　円　　＊税込価格
                </div>
                <div class="input-row">
                    <label>商品画像</label>
                    <input type="file" name="image" accept=".png, .jpg, .jpeg">
                    <button type="button">　<img src="./img/upload.png" alt="upload" style="border: none;"></button>
                    <p>＊JPG, JPEG, PNGのみ</p>
                </div>
                <div class="input-row">
                    <label>注文番号</label>
                    <input id="orderNo" type="text" />
                </div>
                <div class="input-row">
                    <label>カテゴリー</label>
                    <select class="category-select">
                        <option value="food">フードメニュー</option>
                        <option value="drink">ドリンクメニュー</option>
                        <option value="dessert">デザートメニュー</option>
                    </select>

                </div>
                <div class="input-row">
                    <label>在庫</label>
                    <label><input type="radio" name="stock" value="あり" checked> あり</label>
                    <label><input type="radio" name="stock" value="なし"> なし</label>
                </div>
                <div class="input-row" id="option">
                    <label>オプション</label>
                    <label><input type="radio" name="option" value="あり" checked> あり</label>
                    <label><input type="radio" name="option" value="なし"> なし</label>
                </div>
                <!-- オプション全体 -->
                <div id="optionArea">
                    <!-- ★最初のオプションブロック（コピー元テンプレート） -->
                    <div class="option-block">
                        <label>オプションを選択してください</label>
                        <select class="category-select">
	                        <option value="drinkSize">ドリンクサイズ</option>
	                        <option value="S">S</option>
	                        <option value="M">M</option>
	                        <option value="L">L</option>
                    	</select>
                    	 <button class="delete-btn" type="button"><img src="./img/delete.svg" alt="delete"></button>
                    </div>
                    
                </div>
                <!-- ▼ オプション追加ボタン ▼ -->
                <button id="addOptionBtn" class="add-option-btn">オプションを追加</button>
            </div>
        </div>
        <div class="action-buttons">
            <button type="button" class="btn-back" onclick="location.href='modify_search.jsp'">商品検索画面へ戻る</button>
            <button type="submit" class="btn-next" onclick="location.href='modify_add_confirm.jsp'">確認する</button>
        </div>
    </main>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const radios = document.querySelectorAll('input[name="option"]');
            const optionArea = document.getElementById("optionArea");
            function toggleOptionArea() {
                const selected = document.querySelector('input[name="option"]:checked').value;
                if (selected === "なし") {
                    optionArea.style.display = "none";
                    // 入力値をクリアしたい場合（不要なら消す）
                    optionArea.querySelectorAll("input").forEach(el => el.value = "");
                } else {
                    optionArea.style.display = "block";
                }
            }
            // ラジオボタン変更時
            radios.forEach(r => r.addEventListener("change", toggleOptionArea));
            // 初期表示
            toggleOptionArea();
        });
        // 2. オプション追加
        // -------------------------
        const addOptionBtn = document.getElementById("addOptionBtn");
        const optionArea = document.getElementById("optionArea");

        addOptionBtn.addEventListener("click", function () {
        const firstBlock = document.querySelector(".option-block");
        const newBlock = firstBlock.cloneNode(true);

        newBlock.querySelectorAll("input").forEach(el => el.value = "");
        newBlock.querySelectorAll("select").forEach(el => el.selectedIndex = 0);

        newBlock.querySelector(".delete-btn").addEventListener("click", function () {
        if (document.querySelectorAll(".option-block").length > 1) {
            newBlock.remove();
        } else {
            alert("最後のオプションは削除できません。");
        }
    });

    optionArea.appendChild(newBlock);
});

        // -------------------------
        // 3. 最初の削除ボタンにも動作を付ける
        // -------------------------
        document.querySelectorAll(".delete-btn").forEach(btn => {
            btn.addEventListener("click", function () {
                if (document.querySelectorAll(".option-block").length > 1) {
                    this.parentElement.remove();
                } else {
                    alert("最後のオプションは削除できません。");
                }
            });
        });

        // 選択肢追加ボタン（動的対応）
        document.addEventListener("click", function (event) {

            if (!event.target.classList.contains("add-select-btn")) return;

            // 押されたボタンの親要素（option-block）
            const optionBlock = event.target.closest(".option-block");

            // 既存の選択肢行を取得（div のみを数える）
            const choiceRows = optionBlock.querySelectorAll(".choice-row");
            const nextIndex = choiceRows.length + 1;  // 次は 3,4,5...

            // 新しい選択肢行を作成
            const newRow = document.createElement("div");
            newRow.classList.add("choice-row");
            newRow.innerHTML = `
    選択肢${nextIndex}：
    <input type="text" class="choice${nextIndex}">
    価格(税込)：
    <input type="number" class="price${nextIndex}">
`;

            // 選択肢追加ボタンの前に挿入
            event.target.before(newRow);
        });

    </script>
</body>

</html>