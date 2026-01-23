// 部屋IDを取得（hidden input などから）
const roomIdInput = document.getElementById("roomId");
const roomId = roomIdInput ? roomIdInput.value : "";

// サーバーに通知を問い合わせる関数
function fetchNotice() {
	fetch("/NoticeServlet")
		.then(res => res.json())
		.then(data => {
			// セッション切れ時の処理
			if (data.sessionExpired) {
				alert("セッションが切れました。初期画面に戻ります。");
				location.href = "/room_search.jsp";
				return;
			}

			// 通知がある場合、モーダル表示
			if (data.notice) {
				showNotice(data.minutes);
			}
		})
		.catch(err => console.error("通知取得エラー:", err));
}

// 1分ごとに通知チェック
setInterval(fetchNotice, 60000);

// ページ読み込み時に初回チェック
fetchNotice();

// モーダル表示関数
function showNotice(minutes) {
	const modal = document.getElementById("noticeModal");
	const text = document.getElementById("noticeText");
	text.innerText = minutes + "分前になりました";
	modal.classList.remove("hidden");
}

// モーダルを閉じる関数（確認ボタン用）
function closeNotice() {
	const modal = document.getElementById("noticeModal");
	modal.classList.add("hidden");
}
