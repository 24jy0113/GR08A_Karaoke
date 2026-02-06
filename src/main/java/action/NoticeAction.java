package action;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import jakarta.servlet.http.HttpSession;

import dao.RoomDao;
import model.NoticeResult;
import model.Room;

public class NoticeAction {

	public NoticeResult execute(Room sessionRoom, HttpSession session) {

		if (sessionRoom == null) {
			// セッション切れ時.
			return new NoticeResult(true);
		}

		try {
			// DBから最新の部屋情報を取得.
			Room room = RoomDao.getRoomById(sessionRoom.getId());
			if (room == null) {
				return new NoticeResult(true);
			}

			// 実際の退室時間を計算.
			LocalTime leaving = room.getLeavingTime().toLocalTime();

			// 現在時刻.
			LocalTime now = LocalTime.now();

			// 今から退室まで何分あるか.
			long minutesLeft = ChronoUnit.MINUTES.between(now, leaving);

			boolean notice = false;
			int minutes = 0;

			if (minutesLeft <= 15 && minutesLeft > 10 && session.getAttribute("notice15Shown") == null) {
				// 残り15分通知(15〜11分のどこかで1回だけ表示).
				notice = true;
				minutes = 15;
				session.setAttribute("notice15Shown", true);
			} else if (minutesLeft <= 10 && minutesLeft > 5 && session.getAttribute("notice10Shown") == null) {
				// 残り10分通知(10〜6分のどこかで1回だけ表示).
				notice = true;
				minutes = 10;
				session.setAttribute("notice10Shown", true);
			}
			return new NoticeResult(notice, minutes);
		} catch (Exception e) {
			// DBエラー等が発生した場合は安全側に倒す.
			e.printStackTrace();
			return new NoticeResult(true);
		}
	}
}
