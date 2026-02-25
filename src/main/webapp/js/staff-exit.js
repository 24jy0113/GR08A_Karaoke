let tapCount = 0;
let resetTimer = null;
document.body
	.addEventListener(
		"click",
		function(e) {
			if (e.clientX < 50 && e.clientY < 50) {
				tapCount++;
				// 最初のタップなら3秒タイマー開始
				if (tapCount === 1) {
					resetTimer = setTimeout(function() {
						tapCount = 0;
					}, 3000);
				}
				if (tapCount >= 5) {
					clearTimeout(resetTimer);
					window.location.href = contextPath;
				}
			}
		});