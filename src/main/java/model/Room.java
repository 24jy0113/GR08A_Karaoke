package model;

import java.sql.Time;

public class Room {
	private int id; // 部屋ID
	private int roomNo; // 部屋番号
	private boolean alcohol; // 酒類の提供の有無
	private Time receptionTime; // 受付時間
	private Time leavingTime; // 退室時間
	private int statusId; // 状態ID
	private String status; // 状態名
	private Time res_receptionTime; // 予約受付時間
	private Time res_leavingTime; // 予約退室時間

	public Room(int id, int roomNo, boolean alcohol, Time receptionTime, Time leavingTime, int statusId, String status,
			Time res_receptionTime, Time res_leavingTime) {
		this.id = id;
		this.roomNo = roomNo;
		this.statusId = statusId;
		this.status = status;
		this.receptionTime = receptionTime;
		this.leavingTime = leavingTime;
		this.alcohol = alcohol;
		this.res_receptionTime = res_receptionTime;
		this.res_leavingTime = res_leavingTime;
	}

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
