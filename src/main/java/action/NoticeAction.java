package action;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import jakarta.servlet.http.HttpSession;

import model.NoticeResult;
import model.Room;

public class NoticeAction {
	public NoticeResult execute(Room room, HttpSession session) {

		LocalTime now = LocalTime.now(); // 現在時刻
		LocalTime leaving = room.getLeavingTime().toLocalTime(); // Roomの予定退室時間
		long minutesLeft = ChronoUnit.MINUTES.between(now, leaving); // 今から退室まで何分あるか計算

		// 通知フラグと通知時間を初期化
		boolean notice = false;
		int minutes = 0;

		// 残り15分
		if (minutesLeft <= 15 && session.getAttribute("notice15Shown") == null) {
			notice = true;
			minutes = 15;
			session.setAttribute("notice15Shown", true);
		}

		// 残り10分
		if (minutesLeft <= 10 && session.getAttribute("notice10Shown") == null) {
			notice = true;
			minutes = 10;
			session.setAttribute("notice10Shown", true);
		}
		return new NoticeResult(notice, minutes);
	}
}
