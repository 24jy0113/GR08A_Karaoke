package action;

import java.time.LocalTime;

import dao.RoomDao;
import model.Room;

// ※延長可否チェックはConfirm時に行う.
public class ExtendPreviewAction {
	public ExtendPreviewResult preview(int roomId, int extendMinutes) throws Exception {
		// DBから最新Roomを取得.
		Room room = RoomDao.getRoomById(roomId);

		// 現在の退室時間を計算.
		LocalTime currentLeaving = room.getLeavingTime().toLocalTime();

		// 延長後の退室時間を計算.
		LocalTime newLeaving = currentLeaving.plusMinutes(extendMinutes);

		return new ExtendPreviewResult(currentLeaving, newLeaving);
	}
}
