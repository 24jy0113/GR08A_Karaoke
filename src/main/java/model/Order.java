package model;

import java.util.ArrayList;
import java.util.List;

public class Order {
	private int id;
	private List<OrderItem> itemList;
	private int total;
	private int roomId;
	private int roomNo;
	private int receivingNo;
	private int usageHistoryId;
	private String pickupMethod;
	private int itemCreatingStatusId;//飲食注文状況.
	private String status;//部屋状況.

	public Order() {
		this(0, new ArrayList<>(), 0, 0, 0, 0, 0, "", 0, "");
	}

	public Order(int id, List<OrderItem> itemList, int total, int roomId, int roomNo, int receivingNo,
			int usageHistoryId, String pickupMethod, int itemCreatingStatusId, String status) {
		this.id = id;
		this.itemList = itemList;
		this.total = total;
		this.roomId = roomId;
		this.roomNo = roomNo;
		this.receivingNo = receivingNo;
		this.usageHistoryId = usageHistoryId;
		this.pickupMethod = pickupMethod;
		this.itemCreatingStatusId = itemCreatingStatusId;
		this.status = status;
	}

	/*public Order(int id, ArrayList<OrderItem> itemList, int total, int roomId, int roomNo, int receivingNo, int itemCreatingStatusId,
			String status) {
		this.id = id;
		this.itemList = itemList;
		this.total = total;
		this.roomId = roomId;
		this.roomNo = roomNo;
		this.receivingNo = receivingNo;
		this.itemCreatingStatusId = itemCreatingStatusId;
		this.status = status;
	}*/
	public void addItem(OrderItem oi) {
		itemList.add(oi);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Order(List<OrderItem> itemList) {
		this.itemList = itemList;
	}

	public ArrayList<OrderItem> getItemList() {
		return (ArrayList<OrderItem>)itemList;
	}

	public int getTotal() {
		return total;
	}

	public int calculateTotal() {
		int sum = 0;
		for (OrderItem oi : itemList) {
			sum += oi.getTotal();
		}
		return sum;
	}

	public void setReceivingNo(int receivingNo) {
		this.receivingNo = receivingNo;
	}

	public int getReceivingNo() {
		return receivingNo;
	}

	public int getUsageHistoryId() {
		return usageHistoryId;
	}

	public void setUsageHistoryId(int usageHistoryId) {
		this.usageHistoryId = usageHistoryId;
	}

	public void setPickupMethod(String pickupMethod) {
		this.pickupMethod = pickupMethod;
	}

	public String getPickupMethod() {
		return pickupMethod;
	}

	public void setItemCreatingStatusId(int itemCreatingStatusId) {
		this.itemCreatingStatusId = itemCreatingStatusId;
	}

	public int getItemCreatingStatusId() {
		return itemCreatingStatusId;
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

	public void setItemList(List<OrderItem> itemList) {
		this.itemList = itemList;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getRoomNo() {
		return roomNo;
	}

	// 未選択のオプションがあるかを返す.
	public boolean hasOptionUnselected() {
		boolean res = false;
		for (OrderItem item : itemList) {
			if (item.hasOptionUnselected()) {
				return true;
			}
		}
		return res;
	}
}
