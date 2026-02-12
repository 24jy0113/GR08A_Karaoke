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

            while ((line = br.readLine()) != null) {

                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                if (line.trim().isEmpty()) continue;

                String[] cols = line.split(",");

                CsvReservationRow row = new CsvReservationRow(
                    Integer.parseInt(cols[0].trim()), // reservation_number
                    Integer.parseInt(cols[1].trim()), // room_id
                    Date.valueOf(cols[2].trim()),     // date
                    Time.valueOf(cols[3].trim() + ":00"), // start_time
                    Time.valueOf(cols[4].trim() + ":00") // end_time
                    
                );

                list.add(row);
            }
        }

        return list;
    }
}
