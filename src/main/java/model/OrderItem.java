package model;

import java.util.ArrayList;

public class OrderItem {
	private int id;
	private Item item;
	private ArrayList<SelectedOption> selectedOptions;
	private int count;
	private int total;

	public OrderItem(Item item) {
		this(item, 1, new ArrayList<>());
	}

	public OrderItem(Item item, ArrayList<SelectedOption> selectedOptions) {
		this(item, 1, selectedOptions);
	}

	public OrderItem(Item item, int count) {
		this(item, count, new ArrayList<>());
	}

	public OrderItem(Item item, int count, ArrayList<SelectedOption> selectedOptions) {
		this(-1, item, count, selectedOptions, 0);
		calcTotal();
	}

	public OrderItem(int id, Item item, int count, ArrayList<SelectedOption> selectedOptions, int total) {
		this.id = id;
		this.item = item;
		this.count = count;
		this.selectedOptions = selectedOptions;
		this.total = total;
	}

	public record SelectedOption(int optId, int selectionId) {
	};

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

	public ArrayList<SelectedOption> getOptionList() {
		return selectedOptions;
	}

	public SelectedOption getOption(int optId) {
		SelectedOption resSelectedOption = null;
		for (SelectedOption selectedOption : selectedOptions) {
			if (optId == selectedOption.optId) {
				resSelectedOption = selectedOption;
				break;
			}
		}
		return resSelectedOption;
	}

	public void setOptionList(ArrayList<SelectedOption> option) {
		this.selectedOptions = option;
		calcTotal();
	}

	public void setOption(int optId, int selectionId) {
		delSelectedOption(optId);
		selectedOptions.add(new SelectedOption(optId, selectionId));
	}

	public void delSelectedOption(int optId) {
		for (int i = 0; i < selectedOptions.size(); i++) {
			if (optId == selectedOptions.get(i).optId) {
				selectedOptions.remove(i);
				break;
			}
		}
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
		calcTotal();
	}

	private void calcTotal() {
		int optionPriceSum = 0;
		for (SelectedOption selectedOption : selectedOptions) {
			optionPriceSum += item.getOption(selectedOption.optId).getSelection(selectedOption.selectionId).price();
		}
		total = (item.getPrice() + optionPriceSum) * count;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
