package model;

import java.sql.Date;
import java.sql.Time;

public class CsvReservationRow {

    private int reservationNumber;
    private int roomId;
    private Date date;
    private Time startTime;
    private Time endTime;


    public CsvReservationRow(int reservationNumber, int roomId,
                             Date date, Time startTime, Time endTime) {
        this.reservationNumber = reservationNumber;
        this.roomId = roomId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;

    }


    public int getReservationNumber() { return reservationNumber; }
    public int getRoomId() { return roomId; }
    public Date getDate() { return date; }
    public Time getStartTime() { return startTime; }
    public Time getEndTime() { return endTime; }

}
