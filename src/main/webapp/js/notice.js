const roomIdInput = document.getElementById("roomId"); // 部屋IDが入っているhidden input
const roomId = roomIdInput ? roomIdInput.value : "";

function fetchNotice() {
    fetch("/NoticeServlet?roomId=" + roomId)
        .then(res => res.json())
        .then(data => {
            if (data.sessionExpired) {
                alert("セッションが切れました。初期画面に戻ります。");
                location.href = "/init.jsp";
                return;
            }

            if (data.notice) {
                showNotice(data.minutes);
            }
        })
        .catch(err => console.error("通知取得エラー:", err));
}

// 1分ごとに通知チェック
setInterval(fetchNotice, 60000);

// 初回チェック
fetchNotice();

function showNotice(min) {
    const modal = document.getElementById("noticeModal");
    const text = document.getElementById("noticeText");
    text.innerText = min + "分前になりました";
    modal.classList.remove("hidden");
}

function closeNotice() {
    const modal = document.getElementById("noticeModal");
    modal.classList.add("hidden");
}
