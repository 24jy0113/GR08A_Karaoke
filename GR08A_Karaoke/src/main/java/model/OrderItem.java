package model;

public class OrderItem {
	private int id;
	private Item item;
	private int[] option;
	private int count;
	private int total;

	public OrderItem(Item item) {
		this(item, 1, null);
	}

	public OrderItem(Item item, int[] option) {
		this(item, 1, option);
	}

	public OrderItem(Item item, int count) {
		this(item, count, null);
	}

	public OrderItem(Item item, int count, int[] option) {
		this(-1, item, count, option, 0);
		calcTotal();
	}

	public OrderItem(int id, Item item, int count, int[] option, int total) {
		this.id = id;
		this.item = item;
		this.count = count;
		this.option = option;
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		setItem(item, 1);
		calcTotal();
	}

	public void setItem(Item item, int count) {
		this.item = item;
		this.count = count;
		calcTotal();
	}

	public int[] getOptionList() {
		return option;
	}

	public int getOption(int i) {
		return option[i];
	}

	public void setOptionList(int[] option) {
		this.option = option;
		calcTotal();
	}

	public void setOption(int i, int option) {
		this.option[i] = option;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
		calcTotal();
	}

	private void calcTotal() {
		int optionSum = 0;
		for (int i = 0; i < option.length; i++) {
			optionSum += item.getOptionPrice(i);
		}
		total = (item.getPrice() + optionSum) * count;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
