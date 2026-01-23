package model;

import java.util.ArrayList;

public class OrderItem {
	private int id;
	private Item item;
	private ArrayList<SelectedOption> selectedOptions;
	private int count;
	private int total;

	public OrderItem(Item item) {
		this(item, 1);
	}

	public OrderItem(Item item, int count) {
		this(item, count, new ArrayList<>());
		ArrayList<SelectedOption> selOptList = new ArrayList<>();
		for (Option option : item.getOptionList()) {
			selOptList.add(new SelectedOption(option.getId(), -1));
		}
		selectedOptions = selOptList;
	}

	public OrderItem(Item item, ArrayList<SelectedOption> selectedOptions) {
		this(item, 1, selectedOptions);
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

	// クラス内のデータ保持用のオプションの選択状況を持つレコード.
	public record SelectedOption(int optId, int selectionId) {
	};

	// クラス自身のフィールドと持ってるItemクラス（とその下のOptionクラス）内の情報を統合して持つためのレコード.
	public record SelectedOptionDetail(int optId, String optName, int selectionId, String selectionName, int price) {
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
		this.item = item;
		calcTotal();
	}

	public ArrayList<SelectedOption> getSelectedOptionList() {
		return selectedOptions;
	}

	// オプションIDに対応するSelectedOptionを返す。見つからないとnullが出るのでnullチェックをすること.
	public SelectedOption findSelectedOptionById(int optId) {
		SelectedOption resSelectedOption = null;
		for (SelectedOption selectedOption : selectedOptions) {
			if (optId == selectedOption.optId()) {
				resSelectedOption = selectedOption;
				break;
			}
		}
		return resSelectedOption;
	}

	// SelectedOptionDetailのリストを返す.
	public ArrayList<SelectedOptionDetail> getSelectedOptionDetailList() {
		ArrayList<SelectedOptionDetail> resSelectedOptionDetailList = new ArrayList<>();
		for (SelectedOption selectedOption : selectedOptions) {
			resSelectedOptionDetailList.add(findSelectedOptionDetailById(selectedOption.optId()));
		}
		return resSelectedOptionDetailList;
	}

	// オプションIDに対応するSelectedOptionDetailを返す。見つからないとnullが出るのでnullチェックをすること.
	public SelectedOptionDetail findSelectedOptionDetailById(int optId) {
		SelectedOptionDetail resSelectedOptionDetail = null;
		int id = findSelectedOptionById(optId).selectionId();
		resSelectedOptionDetail = new SelectedOptionDetail(optId, item.findOptionById(optId).getName(),
				item.findOptionById(optId).findSelectionById(id).id(),
				item.findOptionById(optId).findSelectionById(id).name(),
				item.findOptionById(optId).findSelectionById(id).price());
		return resSelectedOptionDetail;

	}

	public void setSelectedOptionList(ArrayList<SelectedOption> option) {
		this.selectedOptions = option;
		calcTotal();
	}

	public void setSelectedOption(int optId, int selectionId) {
		// レコードはフィールドがfinalなので一度消してから追加してます.
		delSelectedOption(optId);
		selectedOptions.add(new SelectedOption(optId, selectionId));
	}

	// オプションIDに対応するSelectedOptionを削除する.
	private void delSelectedOption(int optId) {
		for (int i = 0; i < selectedOptions.size(); i++) {
			if (optId == selectedOptions.get(i).optId()) {
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
		for (SelectedOptionDetail selectedOptionDetail : getSelectedOptionDetailList()) {
			optionPriceSum += selectedOptionDetail.price();
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
