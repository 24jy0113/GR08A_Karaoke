package controller;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.ReservationListDao;
import model.ReservationView;

@WebServlet("/ResListUpdateServlet")
public class ResListUpdateServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            String[] reservationNums = req.getParameterValues("reservationNumber");
            String[] startTimes      = req.getParameterValues("startTime");
            String[] endTimes        = req.getParameterValues("endTime");

            if (reservationNums == null) {
                throw new ServletException("更新対象がありません");
            }

            ReservationListDao dao = new ReservationListDao();
            List<String> errors = new ArrayList<>();

         // 今回更新対象の全予約番号リスト
            List<Integer> allReservationNumbers = new ArrayList<>();
            for (String num : reservationNums) {
                allReservationNumbers.add(Integer.parseInt(num));
            }

            for (int i = 0; i < reservationNums.length; i++) {
                int reservationNumber = Integer.parseInt(reservationNums[i]);
                Time start = parseTime(startTimes[i]);
                Time end   = parseTime(endTimes[i]);

                // 開始 < 終了 チェック
                if (start != null && end != null && !end.after(start)) {
                    errors.add("予約番号 " + reservationNumber
                             + "：退室時間は受付時間より後にしてください。");
                    continue;
                }

                // 利用時間チェック
                String durationError = validateDuration(reservationNumber, start, end);
                if (durationError != null) {
                    errors.add(durationError);
                    continue;
                }

                // DB重複チェック（今回更新する全予約を除外）
                ReservationView rv = dao.findByReservationNumber(reservationNumber);
                if (rv != null) {
                    boolean overlap = dao.hasOverlap(
                        reservationNumber,
                        rv.getDate(),
                        rv.getRoomId(),
                        start, end,
                        allReservationNumbers  // ★ 全更新対象を除外
                    );
                    if (overlap) {
                        errors.add("予約番号 " + reservationNumber
                                 + "（部屋" + rv.getRoomNumber() + " / "
                                 + rv.getDate() + "）：時間帯が他の予約と重複しています。");
                    }
                }
            }

            // 同一リクエスト内の行同士の重複チェック
            checkIntraRequestOverlap(reservationNums, startTimes, endTimes, dao, errors);

            // エラーがあれば一覧に戻す
            if (!errors.isEmpty()) {
                req.setAttribute("errors", errors);
                // 一覧を再取得して表示
                List<ReservationView> list = ReservationListDao.findAll();
                for (ReservationView v : list) {
                    if (v.getStatusName() == null) v.setStatusName("予約");
                }
                req.setAttribute("reservationList", list);
                req.getRequestDispatcher("/front/res_list_front.jsp").forward(req, res);
                return;
            }

            // --- バリデーション通過 → 更新実行 ---
            for (int i = 0; i < reservationNums.length; i++) {
                int reservationNumber = Integer.parseInt(reservationNums[i]);
                Time start = parseTime(startTimes[i]);
                Time end   = parseTime(endTimes[i]);
                dao.updateFrontOperation(reservationNumber, start, end);
            }

            res.sendRedirect(req.getContextPath() + "/front/res_list_updated.jsp");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * 利用時間チェック：最低1時間、以降30分単位
     * 有効な利用時間：60分, 90分, 120分, 150分 ...
     */
    private String validateDuration(int reservationNumber, Time start, Time end) {
        if (start == null || end == null) {
            return "予約番号 " + reservationNumber + "：時間を入力してください。";
        }
        long diffMinutes = (end.getTime() - start.getTime()) / (1000 * 60);

        if (diffMinutes < 60) {
            return "予約番号 " + reservationNumber
                 + "：利用時間は最低1時間必要です。（現在 " + diffMinutes + " 分）";
        }
        long over60 = diffMinutes - 60;
        if (over60 % 30 != 0) {
            return "予約番号 " + reservationNumber
                 + "：利用時間は1時間＋30分単位で指定してください。"
                 + "（例：1:00, 1:30, 2:00 …）現在 " + diffMinutes + " 分";
        }
        return null;
    }
    
    /**
     * 同一リクエスト内で同じ部屋・同じ日の行同士が重複していないかチェック
     */
    private void checkIntraRequestOverlap(
            String[] reservationNums, String[] startTimes, String[] endTimes,
            ReservationListDao dao, List<String> errors) throws Exception {

        // 各行の部屋・日付情報を取得
        int[] resNums = new int[reservationNums.length];
        ReservationView[] views = new ReservationView[reservationNums.length];
        Time[] starts = new Time[reservationNums.length];
        Time[] ends   = new Time[reservationNums.length];

        for (int i = 0; i < reservationNums.length; i++) {
            resNums[i] = Integer.parseInt(reservationNums[i]);
            views[i] = dao.findByReservationNumber(resNums[i]);
            starts[i] = parseTime(startTimes[i]);
            ends[i]   = parseTime(endTimes[i]);
        }

        // 2行ずつ比較
        for (int i = 0; i < reservationNums.length; i++) {
            for (int j = i + 1; j < reservationNums.length; j++) {
                if (views[i] == null || views[j] == null) continue;
                if (starts[i] == null || ends[i] == null) continue;
                if (starts[j] == null || ends[j] == null) continue;

                // 同じ部屋・同じ日かどうか
                if (views[i].getRoomId() == views[j].getRoomId()
                    && views[i].getDate().equals(views[j].getDate())) {

                    // 時間重複判定: startA < endB && startB < endA
                    if (starts[i].before(ends[j]) && starts[j].before(ends[i])) {
                        errors.add("予約番号 " + resNums[i] + " と "
                                 + resNums[j] + "（部屋" + views[i].getRoomNumber()
                                 + " / " + views[i].getDate()
                                 + "）：変更後の時間帯が互いに重複しています。");
                    }
                }
            }
        }
    }

    private Time parseTime(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        if (value.length() == 5) {
            value = value + ":00";
        }
        return Time.valueOf(value);
    }
}