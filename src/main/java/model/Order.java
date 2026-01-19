package model;

import java.util.ArrayList;

public class Order {
	private int id;
	private ArrayList<OrderItem> items;
	private int total;
	private int roomId;
	private int room;
	private int receivingNo;
	private int statusId;
	private String status;

	public Order(int id, ArrayList<OrderItem> item, int total, int roomId, int room, int receivingNo, int statusId,
			String status) {
		this.id = id;
		this.items = item;
		this.total = total;
		this.room = room;
		this.receivingNo = receivingNo;
		this.statusId = statusId;
		this.status = status;
	}

	public Order(ArrayList<OrderItem> item, int roomId, int room, int receivingNo, int statusId, String status) {
		this(-1, item, 0, roomId, room, receivingNo, statusId, status);
		int sum = 0;
		for (int i = 0; i < item.size(); i++) {
			sum += item.get(i).getTotal();
		}
		total = sum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<OrderItem> getItemList() {
		return items;
	}

	public int getReceivingNo() {
		return receivingNo;
	}
	
	public int getStatusId() {
		return statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getRoomId() {
		return roomId;
	}

	public int getRoom() {
		return room;
	}

	public int getTotal() {
		return total;
	}
}
