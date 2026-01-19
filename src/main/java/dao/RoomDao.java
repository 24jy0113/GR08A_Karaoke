package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import model.Room;

public class RoomDao {

	public static Room getRoomById(int roomId) throws Exception {
		String sql = "SELECT r.room_id, r.room_number, rus.reception_time, rus.leaving_time, rus.status_id, rus.alcohol_provision, "
				+ "res.reservation_reception_time, res.reservation_leaving_time "
				+ "FROM room r "
				+ "JOIN room_usage_status rus ON r.room_id = rus.room_id "
				+ "LEFT JOIN reservation res ON rus.reservation_number = res.reservation_number "
				+ "WHERE r.room_id = ?";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {
			// プリペアードステートメントを使用.
			preState.setInt(1, roomId);
			try (ResultSet resSet = preState.executeQuery()) {
				if (resSet.next()) {
					Room room = new Room();
					room.setId(resSet.getInt("room_id"));
					room.setRoomNo(resSet.getInt("room_number"));
					room.setReceptionTime(resSet.getTime("reception_time"));
					room.setLeavingTime(resSet.getTime("leaving_time"));
					room.setStatusId(resSet.getInt("status_id"));
					room.setAlcohol(resSet.getBoolean("alcohol_provision"));
					room.setRes_receptionTime(resSet.getTime("reservation_reception_time"));
					room.setRes_leavingTime(resSet.getTime("reservation_leaving_time"));
					return room;
				} else {
					return null;
				}
			} catch (Exception e) {
				// デバッグ用のスタックトレース.
				e.printStackTrace();

				// フロントエンド用のエラーメッセージ.
				String errMsg = "DB接続に失敗しました！<br>管理者に連絡してください。";

				// 例外を投げる.
				throw new Exception(errMsg);
			}
		}

	}

	//room_usage_statusテーブルの退室時間を更新する
	public static void updateLeavingTime(int roomId, Time newLeavingTime) throws Exception {
		// SQL文作成.
		String sql = "UPDATE room_usage_status"
				+ " SET leaving_time = ? WHERE room_id = ?;";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {
			// プリペアードステートメントを使用.
			preState.setTime(1, newLeavingTime);
			preState.setInt(2, roomId);
			preState.executeUpdate();
		} catch (SQLException e) {
			// デバッグ用のスタックトレース.
			e.printStackTrace();

			// フロントエンド用のエラーメッセージ.
			String errMsg = "DB接続に失敗しました！<br>管理者に連絡してください。";

			// 例外を投げる.
			throw new Exception(errMsg);
		}
	}
}
