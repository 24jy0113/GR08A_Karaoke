package model;

import java.sql.Date;
import java.sql.Time;

public class CsvReservationRow {

    private int reservationNumber;
    private int roomId;
    private Date date;
    private Time startTime;
    private Time endTime;
    private int statusId;
    private boolean alcohol;

    public CsvReservationRow(int reservationNumber, int roomId,
                             Date date, Time startTime, Time endTime,
                             int statusId, boolean alcohol) {
        this.reservationNumber = reservationNumber;
        this.roomId = roomId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.statusId = statusId;
        this.alcohol = alcohol;
    }


    public int getReservationNumber() { return reservationNumber; }
    public int getRoomId() { return roomId; }
    public Date getDate() { return date; }
    public Time getStartTime() { return startTime; }
    public Time getEndTime() { return endTime; }
    public int getStatusId() { return statusId; }
    public boolean isAlcohol() { return alcohol; }
}
