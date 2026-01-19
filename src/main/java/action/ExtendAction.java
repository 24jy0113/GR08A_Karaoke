package action;

import java.sql.Time;
import java.time.LocalTime;

import jakarta.servlet.http.HttpSession;

import dao.RoomDao;
import model.Room;

public class ExtendAction {

	public void execute(int extendMinutes, HttpSession session) {

		Room room = (Room) session.getAttribute("room");

		LocalTime newLeaving = room.getLeavingTime().toLocalTime().plusMinutes(extendMinutes);

		room.setLeavingTime(Time.valueOf(newLeaving));

		RoomDao.updateLeavingTime(room.getId(), room.getLeavingTime());

		// ★ 通知状態リセット
		session.removeAttribute("notice15Shown");
		session.removeAttribute("notice10Shown");

		session.setAttribute("room", room);
	}
}