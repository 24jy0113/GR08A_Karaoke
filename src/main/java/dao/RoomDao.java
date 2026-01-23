package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import model.Room;

public class RoomDao {

	// roomIdから部屋状況を取得する.
	public static Room getRoomById(int roomId) throws Exception {
		Room room = null;
		// SQL文作成.
		String sql = "SELECT rus.room_id,r.room_number,alcohol_provision,reception_time,leaving_time,"
				+ "rus.status_id,st.status_name,reservation_reception_time,reservation_leaving_time"
				+ " FROM room r"
				+ " JOIN room_usage_status rus ON r.room_id = rus.room_id"
				+ " JOIN reservation res ON rus.reservation_number = res.reservation_number"
				+ " JOIN status st ON rus.status_id = st.status_id"
				+ " WHERE rus.room_id = ?;";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {
			// プリペアードステートメントを使用.
			preState.setInt(1, roomId);
			// 検索結果からRoomインスタンスを生成.
			try (ResultSet resSet = preState.executeQuery()) {
				room = new Room(resSet.getInt("room_id"), resSet.getInt("room_number"),
						resSet.getBoolean("alcohol_provision"), resSet.getTime("reception_time"),
						resSet.getTime("leaving_time"), resSet.getInt("status_id"), resSet.getString("status_name"),
						resSet.getTime("reservation_reception_time"), resSet.getTime("reservation_leaving_time"));
			} catch (Exception e) {
				// デバッグ用のスタックトレース.
				e.printStackTrace();

				// フロントエンド用のエラーメッセージ.
				String errMsg = "DB接続に失敗しました！<br>管理者に連絡してください。";

				// 例外を投げる.
				throw new Exception(errMsg);
			}
		}
		return room;

	}

	// room_usage_statusテーブルの退室時間を更新する.
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

	// 受付時間更新.
	public static void updateReceptionTime(int roomId, Time newReceptionTime) throws Exception {
		// SQL文作成.
		String sql = "UPDATE room_usage_status SET reception_time = ? WHERE room_id = ?";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {
			// プリペアードステートメントを使用.
			preState.setTime(1, newReceptionTime);
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
