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
			ORDER BY reservation_date, rus.reception_time,rus.room_id ASC;
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
			ORDER BY reservation_date, rus.reception_time,rus.room_id ASC;
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
        }
    }

    // 部屋に予約を取り込む.
//    private void upsertRoomUsageStatus(Connection con, CsvReservationRow row) throws Exception {
//
//        String sql = """
//            INSERT INTO room_usage_status
//			(
//			    room_id,
//			    reservation_number,
//			    reception_time,
//			    leaving_time,
//			    status_id,
//        		alcohol_provision
//			)
//			VALUES (?, ?, ?, ?, ?, 2, 0)
//			ON DUPLICATE KEY UPDATE
//			    reservation_number = VALUES(reservation_number),
//			    reception_time     = VALUES(reception_time),
//			    leaving_time       = VALUES(leaving_time),
//			    status_id          = 2,
//        		alcohol_provision  = 0;
//
//        """;
//
//        try (PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setInt(1, row.getRoomId());
//            ps.setDate(2, row.getDate());
//            ps.setInt(3, row.getReservationNumber());
//            ps.setTime(4, row.getStartTime());
//            ps.setTime(5, row.getEndTime());
//            ps.executeUpdate();
//        }
//    }
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
        }
    }
 // キャンセル（行削除 or 状態変更、ここでは削除の例）
    public void cancelReservation(int reservationNumber) throws Exception {
        String sql = "DELETE FROM reservation WHERE reservation_number = ?";
        try (Connection con = DatabaseManager.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, reservationNumber);
            ps.executeUpdate();
        }
    }
}
