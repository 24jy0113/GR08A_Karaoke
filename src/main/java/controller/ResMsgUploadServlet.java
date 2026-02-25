package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import dao.ReservationListDao;
import model.CsvReservationRow;
import util.CsvUtil;

@WebServlet("/ResMsgUploadServlet")
@MultipartConfig
public class ResMsgUploadServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            Part filePart = req.getPart("csvFile");
            List<CsvReservationRow> rows =
                CsvUtil.parse(filePart.getInputStream());

            ReservationListDao dao = new ReservationListDao();
            List<String> errors = new ArrayList<>();

            // ① CSV内の行同士の重複チェック
            checkIntraCsvOverlap(rows, errors);

            // ② CSV行 vs DB既存データの重複チェック
            for (CsvReservationRow row : rows) {
                // 今回CSV内の全予約番号を除外リストに入れる
                List<Integer> csvReservationNumbers = new ArrayList<>();
                for (CsvReservationRow r : rows) {
                    csvReservationNumbers.add(r.getReservationNumber());
                }

                boolean overlap = dao.hasOverlap(
                    row.getReservationNumber(),
                    row.getDate(),
                    row.getRoomId(),
                    row.getStartTime(),
                    row.getEndTime(),
                    csvReservationNumbers
                );
                if (overlap) {
                    errors.add("予約番号 " + row.getReservationNumber()
                             + "（部屋ID " + row.getRoomId()
                             + " / " + row.getDate()
                             + " " + row.getStartTime() + "〜" + row.getEndTime()
                             + "）：既存の予約と時間帯が重複しています。");
                }
            }

            // エラーがあればアップロード画面に戻す
            if (!errors.isEmpty()) {
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("/admin/res_msg_upload.jsp")
                   .forward(req, res);
                return;
            }

            // バリデーション通過 → 取込実行
            dao.importFromCsv(rows);

            req.getRequestDispatcher("/admin/res_msg_uploaded.jsp")
               .forward(req, res);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * CSV内の行同士で同一部屋・同一日の時間帯重複をチェック
     */
    private void checkIntraCsvOverlap(List<CsvReservationRow> rows, List<String> errors) {
        for (int i = 0; i < rows.size(); i++) {
            for (int j = i + 1; j < rows.size(); j++) {
                CsvReservationRow a = rows.get(i);
                CsvReservationRow b = rows.get(j);

                // 同じ部屋・同じ日か
                if (a.getRoomId() == b.getRoomId()
                    && a.getDate().equals(b.getDate())) {

                    // 時間重複: startA < endB && startB < endA
                    if (a.getStartTime().before(b.getEndTime())
                        && b.getStartTime().before(a.getEndTime())) {

                        errors.add("CSV内で重複：予約番号 " + a.getReservationNumber()
                                 + " と " + b.getReservationNumber()
                                 + "（部屋ID " + a.getRoomId()
                                 + " / " + a.getDate() + "）の時間帯が重複しています。");
                    }
                }
            }
        }
    }
}