document.addEventListener("DOMContentLoaded", () => {
	console.log("notice.js loaded");

	let leaveDateTime = null;
	let countdownInterval = null;

	// サーバーから退室時刻を取得.
	function fetchLeaveTime() {
		fetch(`${CONTEXT_PATH}/RemainingTimeServlet`)
			.then(res => res.json())
			.then(data => {
				if (data.sessionExpired) {
					alert("セッションが切れました。初期画面に戻ります。");
					location.href = `${CONTEXT_PATH}/room_search.jsp`;
					return;
				}
				if (data.leaveTime) {
					const today = new Date();
					const parts = data.leaveTime.split(":");
					const h = parseInt(parts[0], 10);
					const m = parseInt(parts[1], 10);
					const s = parts[2] ? parseInt(parts[2], 10) : 0; // ←秒がなければ0にする
					leaveDateTime = new Date(today.getFullYear(), today.getMonth(), today.getDate(), h, m, s);
					// カウントダウンがまだ始まっていなければ開始.
					if (!countdownInterval) {
						startCountdown();
					}
				}
			})
			.catch(err => console.error("退室時刻取得エラー:", err));
	}

	// 秒ごとのカウントダウン開始.
	function startCountdown() {
		countdownInterval = setInterval(() => {
			if (!leaveDateTime) return;
			const now = new Date();
			let diffSec = Math.floor((leaveDateTime - now) / 1000);
			if (diffSec <= 0) {
				diffSec = 0;
				clearInterval(countdownInterval); // タイマー停止
				countdownInterval = null;
			}
			const hours = Math.floor(diffSec / 3600);
			const minutes = Math.floor((diffSec % 3600) / 60);
			const seconds = diffSec % 60;
			const el = document.getElementById("remainingTime");
			if (el) {
				// 常に HH:MM:SS 形式.
				el.innerText =
					String(hours).padStart(2, "0") + ":" +
					String(minutes).padStart(2, "0") + ":" +
					String(seconds).padStart(2, "0");
			}
		}, 1000);
	}

	// 通知取得.
	function fetchNotice() {
		fetch(`${CONTEXT_PATH}/NoticeServlet`)
			.then(res => res.json())
			.then(data => {
				// セッション切れ時の処理.
				if (data.sessionExpired) {
					alert("セッションが切れました。初期画面に戻ります。");
					location.href = `${CONTEXT_PATH}/room_search.jsp`;
					return;
				}

				// 通知がある場合、モーダル表示.
				if (data.notice) {
					showNotice(data.minutes);
				}
			})
			.catch(err => console.error("通知取得エラー:", err));
	}

	// モーダル表示関数.
	function showNotice(minutes) {
		const modal = document.getElementById("noticeModal");
		const text = document.getElementById("noticeText");
		const note = document.getElementById("noticeNote");
		text.innerText = minutes + "分前になりました";
		if (note) {
			note.style.display = "block";
		}
		modal.classList.remove("hidden");
	}

	// モーダルを閉じる関数（確認ボタン用）.
	window.closeNotice = function() {
		const modal = document.getElementById("noticeModal");
		const note = document.getElementById("noticeNote");
		modal.classList.add("hidden");
		if (note) note.style.display = "none";
	}

	// 初回実行.
	fetchLeaveTime();
	fetchNotice();

	// 1分ごとにサーバー再同期.
	setInterval(() => {
		fetchLeaveTime();
		fetchNotice();
	}, 60000);
});