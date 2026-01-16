package action;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import jakarta.servlet.http.HttpSession;

import model.NoticeResult;
import model.Room;

public class NoticeAction {
	public NoticeResult execute(Room room, HttpSession session) {

		LocalTime now = LocalTime.now();
		LocalTime leaving = room.getLeavingTime().toLocalTime();
		long minutesLeft = ChronoUnit.MINUTES.between(now, leaving);

		boolean notice = false;
		int minutes = 0;

		if (minutesLeft == 15 && session.getAttribute("notice15Shown") == null) {
			notice = true;
			minutes = 15;
			session.setAttribute("notice15Shown", true);
		}

		if (minutesLeft == 10 && session.getAttribute("notice10Shown") == null) {
			notice = true;
			minutes = 10;
			session.setAttribute("notice10Shown", true);
		}

		return new NoticeResult(notice, minutes);
	}

}
