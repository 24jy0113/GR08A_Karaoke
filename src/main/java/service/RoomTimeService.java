package service;

import java.sql.Time;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import dao.RoomDao;
import model.Room;

public class RoomTimeService {
	// 受け取ったRoomオブジェクト情報で時間を計算し返す.
	public static LocalTime calcActualLeavingTime(Room room) {
		// 予約受付時間、予約退室時間、実際受付時間を取得
		LocalTime plannedReception = room.getRes_receptionTime().toLocalTime();
		LocalTime plannedLeaving = room.getRes_leavingTime().toLocalTime();
		LocalTime actualReception = room.getReceptionTime().toLocalTime();

		// 差分計算
		long diffMinutes = ChronoUnit.MINUTES.between(plannedReception, actualReception);

		// 実際の退室時間を計算
		return plannedLeaving.plusMinutes(diffMinutes);
	}

	// 受付時間＆退室時間(予約ありの場合) 更新.
	public void updateRoomTimes(int roomId, String receptionTimeStr) throws Exception {
		Room room = RoomDao.getRoomById(roomId);
		if (room == null)
			throw new IllegalStateException("部屋情報が存在しません");

		LocalTime plannedReception = room.getRes_receptionTime().toLocalTime();
		LocalTime plannedLeaving = room.getRes_leavingTime().toLocalTime();

		LocalTime actualReception = LocalTime.parse(receptionTimeStr);

		// 差分を計算（実際受付時刻 - 予約受付時刻）
		long diffMinutes = ChronoUnit.MINUTES.between(plannedReception, actualReception);

		// 差分を加算して退室時間を決定
		LocalTime adjustedLeaving = plannedLeaving.plusMinutes(diffMinutes);

		// DBに更新
		room.setReceptionTime(Time.valueOf(actualReception));
		room.setLeavingTime(Time.valueOf(adjustedLeaving));
		RoomDao.updateReceptionTime(roomId, Time.valueOf(actualReception)); // DAOに追記が必要
		RoomDao.updateLeavingTime(roomId, Time.valueOf(adjustedLeaving));
	}

	// 受付時間＆退室時間(予約なしの場合) 更新.
	public void updateRoomTimes(int roomId, String receptionTimeStr, String leavingTimeStr) throws Exception {
		Room room = RoomDao.getRoomById(roomId);
		if (room == null)
			throw new IllegalStateException("部屋情報が存在しません");

		LocalTime actualReception = LocalTime.parse(receptionTimeStr);
		LocalTime actualLeaving = null;

		if (leavingTimeStr != null && !leavingTimeStr.isEmpty()) {
			actualLeaving = LocalTime.parse(leavingTimeStr);
		}

		// DBに更新
		room.setReceptionTime(Time.valueOf(actualReception));
		if (actualLeaving != null) {
			room.setLeavingTime(Time.valueOf(actualLeaving));
			RoomDao.updateLeavingTime(roomId, Time.valueOf(actualLeaving));
		}
		RoomDao.updateReceptionTime(roomId, Time.valueOf(actualReception));
	}

	// セッション内のRoom情報を最新のDB情報で更新（再取得）するメソッド.
	public Room refreshRoomFromDB(int roomId) throws Exception {
		// RoomDaoのgetRoomByIdを呼び、DBから最新の部屋情報を取得して返す.
		return RoomDao.getRoomById(roomId);
	}

	// 延長の可否を確認.
	public static boolean canExtend(LocalTime currentLeaving, LocalTime nextReception, int extendMinutes) {

		// 次予約がないなら延長OK
		if (nextReception == null) {
			return true;
		}

		LocalTime extendedLeaving = currentLeaving.plusMinutes(extendMinutes);

		// 掃除時間30分を確保できるか
		LocalTime limitTime = nextReception.minusMinutes(30);

		return !extendedLeaving.isAfter(limitTime);
	}

}
