package action;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import dao.RoomDao;
import model.Room;

public class ExtendCheckAction {
	private static final int CLEANING_MINUTES = 30;
	private static final int[] EXTEND_OPTIONS = { 30, 60, 90, 120, 150, 180 };

	public List<Integer> getAvailableExtendMinutes(int roomId) throws Exception {
		// DBから最新の部屋情報を取得.
		Room room = RoomDao.getRoomById(roomId);
		if (room == null) {
			throw new IllegalStateException("部屋情報が存在しません");
		}
		// 現在の退室時間.
		LocalTime actualLeaving = room.getLeavingTime().toLocalTime();

		// 次の予約受付時間.
		Time nextRecTime = RoomDao.getNextReceptionTime(roomId, Time.valueOf(actualLeaving));

		LocalTime nextReception = nextRecTime != null ? nextRecTime.toLocalTime() : null;
		List<Integer> result = new ArrayList<>();
		LocalTime extendedLeaving;
		LocalTime limitTime;
		for (int minutes : EXTEND_OPTIONS) {

			// 次予約がないなら全部OK
			if (nextReception == null) {
				result.add(minutes);
				continue;
			}

			extendedLeaving = actualLeaving.plusMinutes(minutes);

			limitTime = nextReception.minusMinutes(CLEANING_MINUTES);

			if (!extendedLeaving.isAfter(limitTime)) {
				result.add(minutes);
			}
		}
		return result;
	}
}
