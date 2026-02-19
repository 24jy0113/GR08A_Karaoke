<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- ===== 通知モーダル ===== -->
<div id="sseNoticeModal" class="sse-hidden">
	<div class="sse-notice-modal-content">
		<p id="sseNoticeText"></p>
		<small id="sseNoticeNote"> （注文通知は多少前後することがあります） </small>
		<button type="button" id="sseCloseBtn" onclick="sseCloseNotice()">確認</button>
	</div>
</div>

<script src="<%=request.getContextPath()%>/js/sse-receiver.js"></script>

<script>
    // JSPの式言語(EL)などでroomIdを取得
    const currentRoomId = "${room.id }"; 

    // ページ読み込み完了時にJSの関数を実行
    document.addEventListener("DOMContentLoaded", () => {
        initializeNotification(currentRoomId);
    });
</script>


<style>
#sseNoticeModal {
	display: none;
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0.5);
	z-index: 9999;
}

.sse-notice-modal-content {
	background: #fff;
	width: 300px;
	margin: 30% auto;
	padding: 20px;
	text-align: center;
	border-radius: 8px;
}

#sseNoticeNote {
	color: #555;
	font-size: 0.8em;
}
</style>