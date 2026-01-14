package model;

import java.sql.Time;

public class Room {
	private int id;
	private int roomNo;
	private int statusId;
	private String status;
	private Time receptionTime;
	private Time leavingTime;
	private boolean alcohol;
	private Time res_receptionTime;
	private Time res_leavingTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public boolean isAlcohol() {
		return alcohol;
	}

	public void setAlcohol(boolean alcohol) {
		this.alcohol = alcohol;
	}

	public Time getRes_receptionTime() {
		return res_receptionTime;
	}

	public void setRes_receptionTime(Time res_receptionTime) {
		this.res_receptionTime = res_receptionTime;
	}

	public Time getRes_leavingTime() {
		return res_leavingTime;
	}

	public void setRes_leavingTime(Time res_leavingTime) {
		this.res_leavingTime = res_leavingTime;
	}
}
