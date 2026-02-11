package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.ReservationView;
import model.CsvReservationRow;
public class ReservationListDao {

    public static List<ReservationView> findAll() throws Exception {

        List<ReservationView> list = new ArrayList<>();

        String sql = """
            SELECT
                res.reservation_number,
                r.room_number,
                res.reservation_date,
                res.reservation_reception_time,
                res.reservation_leaving_time,
                st.status_name
            FROM reservation res
            JOIN room r ON res.room_id = r.room_id
            LEFT JOIN room_usage_status rus
                ON res.reservation_number = rus.reservation_number
            LEFT JOIN status st
                ON rus.status_id = st.status_id
            ORDER BY res.reservation_date, res.reservation_reception_time
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
    public static List<ReservationView> findByStatus(int statusId) throws Exception {

        List<ReservationView> list = new ArrayList<>();

        String sql = """
            SELECT
                res.reservation_number,
                r.room_number,
                res.reservation_date,
                res.reservation_reception_time,
                res.reservation_leaving_time,
                st.status_name
            FROM reservation res
            JOIN room r ON res.room_id = r.room_id
            LEFT JOIN room_usage_status rus
                ON res.reservation_number = rus.reservation_number
            LEFT JOIN status st
                ON rus.status_id = st.status_id
            WHERE rus.status_id = ?
            ORDER BY res.reservation_date, res.reservation_reception_time
        """;

        try (Connection con = DatabaseManager.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, statusId);

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
                upsertRoomUsageStatus(con, row);
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

    private void upsertRoomUsageStatus(Connection con, CsvReservationRow row) throws Exception {

        String sql = """
            INSERT INTO room_usage_status
            (room_id, usage_date, alcohol_provision, status_id,
             reservation_number, reception_time, leaving_time)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                alcohol_provision = VALUES(alcohol_provision),
                status_id = VALUES(status_id),
                reservation_number = VALUES(reservation_number),
                reception_time = VALUES(reception_time),
                leaving_time = VALUES(leaving_time)
        """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, row.getRoomId());
            ps.setDate(2, row.getDate());
            ps.setBoolean(3, row.isAlcohol());
            ps.setInt(4, row.getStatusId());
            ps.setInt(5, row.getReservationNumber());
            ps.setTime(6, row.getStartTime());
            ps.setTime(7, row.getEndTime());
            ps.executeUpdate();
        }
    }
}
