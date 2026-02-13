package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import model.Room;

public class RoomDao {

	// 部屋状況一覧を取得.
	public static List<Room> getAllRooms() throws Exception {
		List<Room> list = new ArrayList<>();
		// SQL作成.
		String sql = "SELECT room_id, room_number FROM room;";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {
			try (ResultSet resSet = preState.executeQuery()) {
				while (resSet.next()) {
					Room r = getRoomById(resSet.getInt("room_id"));
					if (r == null) {
						r = new Room(resSet.getInt("room_id"), resSet.getInt("room_number"));
					}
					list.add(r);
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
		return list;
	}

	// roomIdから部屋情報を取得する.
	public static Room getRoomById(int roomId) throws Exception {
		Room room = null;
		// SQL文作成.
		String sql = "SELECT r.room_id,r.room_number,alcohol_provision,reception_time,leaving_time,"
				+ "rus.status_id,st.status_name,reservation_reception_time,reservation_leaving_time"
				+ " FROM room r"
				+ " LEFT JOIN room_usage_status rus ON r.room_id = rus.room_id"
				+ " LEFT JOIN reservation res ON rus.reservation_number = res.reservation_number"
				+ " LEFT JOIN status st ON rus.status_id = st.status_id"
				+ " WHERE rus.room_id = ?;";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {
			// プリペアードステートメントを使用.
			preState.setInt(1, roomId);
			// 検索結果からRoomインスタンスを生成.
			try (ResultSet resSet = preState.executeQuery()) {
				if (resSet.next()) {
					room = new Room(resSet.getInt("room_id"), resSet.getInt("room_number"),
							resSet.getBoolean("alcohol_provision"), resSet.getTime("reception_time"),
							resSet.getTime("leaving_time"), resSet.getInt("status_id"), resSet.getString("status_name"),
							resSet.getTime("reservation_reception_time"), resSet.getTime("reservation_leaving_time"));
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
		return room;
	}

	// room_numberから部屋情報を取得する.
	public static Room getRoomByRoomNumber(int roomNumber) throws Exception {
		Room room = null;
		// SQL作成.
		String sql = "SELECT rus.room_id, r.room_number, alcohol_provision, reception_time, leaving_time,"
				+ " rus.status_id, st.status_name, reservation_reception_time, reservation_leaving_time"
				+ " FROM room r"
				+ " JOIN room_usage_status rus ON r.room_id = rus.room_id"
				+ " JOIN reservation res ON rus.reservation_number = res.reservation_number"
				+ " JOIN status st ON rus.status_id = st.status_id"
				+ " WHERE r.room_number = ?";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {
			// プリペアードステートメントを使用.
			preState.setInt(1, roomNumber);
			try (ResultSet resSet = preState.executeQuery()) {
				if (resSet.next()) {
					room = new Room(resSet.getInt("room_id"), resSet.getInt("room_number"),
							resSet.getBoolean("alcohol_provision"), resSet.getTime("reception_time"),
							resSet.getTime("leaving_time"), resSet.getInt("status_id"), resSet.getString("status_name"),
							resSet.getTime("reservation_reception_time"), resSet.getTime("reservation_leaving_time"));
				}
			}
		}
		return room;
	}

	// statusIdで絞り込み検索.
	public static List<Room> getRoomsByStatus(int statusId) throws Exception {
		List<Room> list = new ArrayList<>();
		// SQL文作成.
		String sql = "SELECT r.room_id,r.room_number,alcohol_provision,reception_time,leaving_time,"
				+ " rus.status_id,st.status_name,reservation_reception_time,reservation_leaving_time"
				+ " FROM room r"
				+ " LEFT JOIN room_usage_status rus ON r.room_id = rus.room_id"
				+ " LEFT JOIN reservation res ON rus.reservation_number = res.reservation_number"
				+ " LEFT JOIN status st ON rus.status_id = st.status_id"
				+ " WHERE COALESCE(rus.status_id, 1) = ?;";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {
			// プリペアードステートメントを使用.
			preState.setInt(1, statusId);
			try (ResultSet resSet = preState.executeQuery()) {
				while (resSet.next()) {
					list.add(new Room(resSet.getInt("room_id"), resSet.getInt("room_number"),
							resSet.getBoolean("alcohol_provision"), resSet.getTime("reception_time"),
							resSet.getTime("leaving_time"), resSet.getInt("status_id"), resSet.getString("status_name"),
							resSet.getTime("reservation_reception_time"), resSet.getTime("reservation_leaving_time")));
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
		return list;
	}

	// 特定のルームの予約のうち一番近い予約受付時間を取得する.
	public static Time getNextReceptionTime(int roomId, Time leavingTime) throws Exception {
		Time time = null;
		// SQL文作成.
		String sql = "SELECT reservation_reception_time"
				+ " FROM reservation"
				+ " WHERE room_id = ? AND reservation_reception_time > ?"
				+ " ORDER BY reservation_reception_time"
				+ " LIMIT 1;";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {
			// プリペアードステートメントを使用.
			preState.setInt(1, roomId);
			preState.setTime(2, leavingTime);
			// 検索結果からRoomインスタンスを生成.
			try (ResultSet resSet = preState.executeQuery()) {
				if (resSet.next()) {
					time = resSet.getTime("reservation_reception_time");
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
		return time;
	}

	// room_usage_statusにroom_idがあるかチェックする.
	public static boolean existsRoomUsageStatus(int roomId) throws Exception {
		// SQL文作成.
		String sql = "SELECT COUNT(*) FROM room_usage_status WHERE room_id = ?;";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {
			// プリペアードステートメントを使用.
			preState.setInt(1, roomId);
			try (ResultSet resSet = preState.executeQuery()) {
				if (resSet.next()) {
					return resSet.getInt(1) > 0;
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
		return false;
	}

	// existsRoomUsageStatus()でroom_idがなかったらINSERTする.
	public static void insertRoomUsageStatus(int roomId) throws Exception {
		// SQL文作成.
		String sql = "INSERT INTO room_usage_status"
				+ "(room_id, alcohol_provision, reception_time, leaving_time, status_id, reservation_number)"
				+ "VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {
			// プリペアードステートメントを使用.
			preState.setInt(1, roomId);
			preState.setBoolean(2, false);
			preState.setTime(3, null);
			preState.setTime(4, null);
			preState.setInt(5, 1);
			preState.setObject(6, null);
			preState.executeUpdate();
		} catch (Exception e) {
			// デバッグ用のスタックトレース.
			e.printStackTrace();

			// フロントエンド用のエラーメッセージ.
			String errMsg = "DB接続に失敗しました！<br>管理者に連絡してください。";

			// 例外を投げる.
			throw new Exception(errMsg);
		}
	}

	// room_usage_statusテーブルの退室時間を更新する.
	public static void updateLeavingTime(int roomId, Time newLeavingTime) throws Exception {
		// SQL文作成.
		String sql = "UPDATE room_usage_status SET leaving_time = ? WHERE room_id = ?;";

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
		String sql = "UPDATE room_usage_status SET reception_time = ? WHERE room_id = ?;";

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

	// 酒類提供更新.
	public static void updateAlcohol(int roomId, boolean alcohol) throws Exception {
		// SQL作成.
		String sql = "UPDATE room_usage_status SET alcohol_provision = ? WHERE room_id = ?;";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {
			// プリペアードステートメントを使用.
			preState.setBoolean(1, alcohol);
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

	// 状態更新.
	public static void updateStatus(int roomId, int statusId) throws Exception {
		// SQL作成.
		String sql = "UPDATE room_usage_status SET status_id = ? WHERE room_id = ?;";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {
			// プリペアードステートメントを使用.
			preState.setInt(1, statusId);
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
	
	// 利用を利用履歴に移動する.
	public void archiveRoomUsage() {
		// SQL作成.
		
	}
}
