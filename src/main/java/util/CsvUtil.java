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
	                CsvReservationRow row = new CsvReservationRow(
	                    Integer.parseInt(cols[0].trim()),
	                    Integer.parseInt(cols[1].trim()),
	                    Date.valueOf(cols[2].trim()),
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
}
