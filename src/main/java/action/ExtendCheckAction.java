package action;

import java.sql.Time;
import java.time.LocalTime;

import dao.RoomDao;
import model.Room;
import service.RoomTimeService;

public class ExtendCheckAction {
	public boolean canExtend(int roomId) throws Exception {
		// 延長時間の最小値.
		int extendMinutes = 30;

		// DBから最新の部屋情報を取得.
		Room room = RoomDao.getRoomById(roomId);

		// 現在の退室時間.
		LocalTime actualLeaving = RoomTimeService.calcActualLeavingTime(room);

		// 次の予約受付時間.
		Time nextRecTime = RoomDao.getNextReceptionTime(roomId);

		LocalTime nextReception = nextRecTime != null ? nextRecTime.toLocalTime() : null;

		return RoomTimeService.canExtend(actualLeaving, nextReception, extendMinutes);
	}
}
