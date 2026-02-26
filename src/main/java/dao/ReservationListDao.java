package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import model.CsvReservationRow;
import model.ReservationView;
public class ReservationListDao {

	// 全予約を取得する.
    public static List<ReservationView> findAll() throws Exception {

        List<ReservationView> list = new ArrayList<>();

        String sql = """
            SELECT reservation.reservation_number,
				r.room_number,
				reservation_date,
				reservation_reception_time,
				reservation_leaving_time,
				st.status_name
			FROM room_usage_status rus
			JOIN `status` st ON rus.status_id = st.status_id
			RIGHT JOIN reservation ON rus.reservation_number = reservation.reservation_number
			JOIN room r ON reservation.room_id = r.room_id
			WHERE reservation.reservation_date >= CURDATE()
			ORDER BY reservation_date ASC, r.room_number ASC, reservation_reception_time ASC;
        """;

        try (Connection con = DatabaseManager.connect();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new ReservationView(
                    rs.getInt("reservation_number"),
                    rs.getInt("room_number"),
                    rs.getDate("reservation_date"),
                    rs.getTime("reservation_reception_time"),
                    rs.getTime("reservation_leaving_time"),
                    rs.getString("status_name")
                ));
            }
        } catch (Exception e) {
        	// デバッグ用のスタックトレース.
        	e.printStackTrace();
        	// フロントエンド用のエラーメッセージ.
        	String errMsg = "予約一覧の取得に失敗しました！<br>管理者に連絡してください。";
        	// 例外を投げる.
        	throw new Exception(errMsg);
        }
        return list;
    }
    // 部屋番号で予約を探す.
    public static List<ReservationView> findByRoom(int roomNo) throws Exception {

        List<ReservationView> list = new ArrayList<>();

        String sql = """
            SELECT reservation.reservation_number,
				r.room_number,
				reservation_date,
				reservation_reception_time,
				reservation_leaving_time,
				st.status_name
			FROM room_usage_status rus
			JOIN `status` st ON rus.status_id = st.status_id
			RIGHT JOIN reservation ON rus.reservation_number = reservation.reservation_number
			JOIN room r ON reservation.room_id = r.room_id
			WHERE r.room_number = ?
        	AND reservation.reservation_date >= CURDATE()
			ORDER BY reservation_date ASC, r.room_number ASC, reservation_reception_time ASC;
        """;

        try (Connection con = DatabaseManager.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, roomNo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ReservationView(
                        rs.getInt("reservation_number"),
                        rs.getInt("room_number"),
                        rs.getDate("reservation_date"),
                        rs.getTime("reservation_reception_time"),
                        rs.getTime("reservation_leaving_time"),
                        rs.getString("status_name")
                    ));
                }
            }
        } catch (Exception e) {
        	// デバッグ用のスタックトレース.
        	e.printStackTrace();
        	// フロントエンド用のエラーメッセージ.
        	String errMsg = "部屋別予約の取得に失敗しました！<br>管理者に連絡してください。";
        	// 例外を投げる.
        	throw new Exception(errMsg);
        }
        return list;
    }

    public void importFromCsv(List<CsvReservationRow> rows) throws Exception {

        try (Connection con = DatabaseManager.connect()) {
            con.setAutoCommit(false);

            for (CsvReservationRow row : rows) {
                upsertReservation(con, row);
            }

            con.commit();
        } catch (Exception e) {
            throw new Exception("CSV取込処理に失敗しました", e);
        }
    }
    private void upsertReservation(Connection con, CsvReservationRow row) throws Exception {

        String sql = """
            INSERT INTO reservation
            (reservation_number, room_id, reservation_date,
             reservation_reception_time, reservation_leaving_time)
            VALUES (?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                room_id = VALUES(room_id),
                reservation_date = VALUES(reservation_date),
                reservation_reception_time = VALUES(reservation_reception_time),
                reservation_leaving_time = VALUES(reservation_leaving_time)
        """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, row.getReservationNumber());
            ps.setInt(2, row.getRoomId());
            ps.setDate(3, row.getDate());
            ps.setTime(4, row.getStartTime());
            ps.setTime(5, row.getEndTime());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new Exception("予約データの登録／更新に失敗しました", e);
        }
    }

   
    public void updateFrontOperation(
            int reservationNumber,
            Time start,
            Time end,
            int statusId
        ) throws Exception {

            try (Connection con = DatabaseManager.connect()) {

                // ① reservation
                String sql1 = """
                    UPDATE reservation
                    SET reservation_reception_time = ?,
                        reservation_leaving_time = ?
                    WHERE reservation_number = ?
                """;

                try (PreparedStatement ps = con.prepareStatement(sql1)) {
                    ps.setTime(1, start);
                    ps.setTime(2, end);
                    ps.setInt(3, reservationNumber);
                    ps.executeUpdate();
                }

                // ② room_usage_status
                String sql2 = """
                    UPDATE room_usage_status
                    SET reception_time = ?,
                        leaving_time = ?,
                        status_id = ?
                    WHERE reservation_number = ?
                """;

                try (PreparedStatement ps = con.prepareStatement(sql2)) {
                    ps.setTime(1, start);
                    ps.setTime(2, end);
                    ps.setInt(3, statusId);
                    ps.setInt(4, reservationNumber);
                    ps.executeUpdate();
                } catch (Exception e) {
                	// デバッグ用のスタックトレース.
                	e.printStackTrace();
                	// フロントエンド用のエラーメッセージ.
                	String errMsg = "フロント操作の更新に失敗しました。";
                	// 例外を投げる.
                	throw new Exception(errMsg);
                }
            }
        }
    public void updateFrontOperation(int reservationNumber, Time start, Time end) 
            throws Exception {
        String sql = "UPDATE reservation SET reservation_reception_time = ?, reservation_leaving_time = ? "
                   + "WHERE reservation_number = ?";
        try (Connection con = DatabaseManager.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setTime(1, start);
            ps.setTime(2, end);
            ps.setInt(3, reservationNumber);
            ps.executeUpdate();
        } catch (Exception e) {
        	// デバッグ用のスタックトレース.
        	e.printStackTrace();
        	// フロントエンド用のエラーメッセージ.
        	String errMsg = "予約時間の更新に失敗しました。";
        	// 例外を投げる.
        	throw new Exception(errMsg);
        }
    }
 // キャンセル（行削除 or 状態変更、ここでは削除の例）
    public void cancelReservation(int reservationNumber) throws Exception {
        String sql = "DELETE FROM reservation WHERE reservation_number = ?";
        try (Connection con = DatabaseManager.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, reservationNumber);
            ps.executeUpdate();
        } catch (Exception e) {
        	// デバッグ用のスタックトレース.
        	e.printStackTrace();
        	// フロントエンド用のエラーメッセージ.
        	String errMsg = "予約キャンセルに失敗しました";
        	// 例外を投げる.
        	throw new Exception(errMsg);
        }
    }
    /**
     * 予約番号で1件取得（バリデーション用：部屋ID・日付を含む）
     */
    public ReservationView findByReservationNumber(int reservationNumber) throws Exception {
        String sql = """
            SELECT reservation.reservation_number,
                   reservation.room_id,
                   r.room_number,
                   reservation_date,
                   reservation_reception_time,
                   reservation_leaving_time,
                   st.status_name
            FROM reservation
            JOIN room r ON reservation.room_id = r.room_id
            LEFT JOIN room_usage_status rus
                ON rus.reservation_number = reservation.reservation_number
            LEFT JOIN `status` st ON rus.status_id = st.status_id
            WHERE reservation.reservation_number = ?
        """;
        try (Connection con = DatabaseManager.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, reservationNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ReservationView v = new ReservationView(
                        rs.getInt("reservation_number"),
                        rs.getInt("room_number"),
                        rs.getDate("reservation_date"),
                        rs.getTime("reservation_reception_time"),
                        rs.getTime("reservation_leaving_time"),
                        rs.getString("status_name")
                    );
                    v.setRoomId(rs.getInt("room_id"));
                    return v;
                }
            }
        } catch (Exception e) {
        	// デバッグ用のスタックトレース.
        	e.printStackTrace();
        	// フロントエンド用のエラーメッセージ.
        	String errMsg = "予約情報の取得に失敗しました";
        	// 例外を投げる.
        	throw new Exception(errMsg);
        }
        return null;
    }

    /**
     * 同一部屋・同一日で時間帯が重複する予約があるか確認
     * excludeNumbers: 今回の更新対象の予約番号リスト（全て除外する）
     */
    public boolean hasOverlap(int reservationNumber, java.sql.Date date,
                               int roomId, Time start, Time end,
                               List<Integer> excludeNumbers) throws Exception {

        // IN句用のプレースホルダを生成
        String placeholders = excludeNumbers.stream()
            .map(n -> "?")
            .collect(java.util.stream.Collectors.joining(","));

        String sql = "SELECT COUNT(*) FROM reservation"
                   + " WHERE room_id = ?"
                   + " AND reservation_date = ?"
                   + " AND reservation_number NOT IN (" + placeholders + ")"
                   + " AND reservation_reception_time < ?"
                   + " AND reservation_leaving_time > ?";

        try (Connection con = DatabaseManager.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int idx = 1;
            ps.setInt(idx++, roomId);
            ps.setDate(idx++, date);
            for (int num : excludeNumbers) {
                ps.setInt(idx++, num);
            }
            ps.setTime(idx++, end);
            ps.setTime(idx++, start);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
        	// デバッグ用のスタックトレース.
        	e.printStackTrace();
        	// フロントエンド用のエラーメッセージ.
        	String errMsg = "重複チェックに失敗しました";
        	// 例外を投げる.
        	throw new Exception(errMsg);
        }
    }
}
