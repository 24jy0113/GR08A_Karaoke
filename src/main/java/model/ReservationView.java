package model;

import java.sql.Date;
import java.sql.Time;

public class ReservationView {

    private int reservationNumber;
    private int roomNumber;
    private Date date;
    private Time receptionTime;
    private Time leavingTime;
    private String statusName;

    public ReservationView(int reservationNumber, int roomNumber,
                           Date date, Time receptionTime,
                           Time leavingTime, String statusName) {
        this.reservationNumber = reservationNumber;
        this.roomNumber = roomNumber;
        this.date = date;
        this.receptionTime = receptionTime;
        this.leavingTime = leavingTime;
        this.statusName = statusName;
    }

	public int getReservationNumber() {
		return reservationNumber;
	}

	public void setReservationNumber(int reservationNumber) {
		this.reservationNumber = reservationNumber;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getReceptionTime() {
		return receptionTime;
	}

	public void setReceptionTime(Time receptionTime) {
		this.receptionTime = receptionTime;
	}

	public Time getLeavingTime() {
		return leavingTime;
	}

	public void setLeavingTime(Time leavingTime) {
		this.leavingTime = leavingTime;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

    
}
