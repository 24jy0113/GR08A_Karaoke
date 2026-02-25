package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import model.CsvReservationRow;

public class CsvUtil {

    public static List<CsvReservationRow> parse(InputStream is) throws Exception {
        List<CsvReservationRow> list = new ArrayList<>();
        try (BufferedReader br =
                new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
            String line;
            boolean isHeader = true;
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] cols = line.split(",");
                boolean allEmpty = true;
                for (String c : cols) {
                    if (!c.trim().isEmpty()) {
                        allEmpty = false;
                        break;
                    }
                }
                if (allEmpty) {
                    continue;
                }
                if (cols.length < 5) {
                    throw new Exception(
                        "CSVファイルの内容に不備があります（" + lineNo + "行目を確認してください）"
                    );
                }
                try {
                    // ★ 日付の正規化：yyyy/MM/dd → yyyy-MM-dd に変換
                    String dateStr = normalizeDate(cols[2].trim(), lineNo);

                    CsvReservationRow row = new CsvReservationRow(
                        Integer.parseInt(cols[0].trim()),
                        Integer.parseInt(cols[1].trim()),
                        Date.valueOf(dateStr),
                        Time.valueOf(cols[3].trim() + ":00"),
                        Time.valueOf(cols[4].trim() + ":00")
                    );
                    list.add(row);
                } catch (Exception e) {
                    throw new Exception(
                        "CSVファイルの内容を正しく読み取れませんでした（" + lineNo + "行目）", e
                    );
                }
            }
        }
        if (list.isEmpty()) {
            throw new Exception("CSVファイルに有効なデータがありません");
        }
        return list;
    }

    /**
     * 日付文字列を yyyy-MM-dd 形式に正規化する
     * 対応形式: yyyy-MM-dd, yyyy/MM/dd, yyyy-M-d, yyyy/M/d 等
     */
    private static String normalizeDate(String raw, int lineNo) throws Exception {
        if (raw == null || raw.isEmpty()) {
            throw new Exception(lineNo + "行目：日付が空です");
        }

        // "/" を "-" に置換
        String normalized = raw.replace('/', '-');

        // yyyy-M-d 等に対応（1〜2桁の月・日を許可）
        if (!normalized.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
            throw new Exception(
                lineNo + "行目：日付の形式が不正です（" + raw
                + "）。yyyy-MM-dd または yyyy/MM/dd で入力してください"
            );
        }

        // LocalDate.parse で正規化（自動的に補零 + 存在チェック）
        try {
            java.time.format.DateTimeFormatter fmt =
                java.time.format.DateTimeFormatter.ofPattern("yyyy-M-d");
            java.time.LocalDate date = java.time.LocalDate.parse(normalized, fmt);
            // yyyy-MM-dd 形式に統一（toString() は必ず零埋め）
            normalized = date.toString();
        } catch (Exception e) {
            throw new Exception(
                lineNo + "行目：存在しない日付です（" + raw + "）"
            );
        }

        return normalized;
    }
}