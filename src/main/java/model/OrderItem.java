package model;

import java.util.ArrayList;
import java.util.List;

public class OrderItem {
	private int id;
	//注文前
	private Item item;
	
	//注文後
	private int itemId;
    private String itemName;
    private int itemPrice;
    
	private List<SelectedOption> selectedOptions;
	private int count;
	private int total;

	public OrderItem(Item item) {
		this(item, 1);
	}

	public OrderItem(Item item, int count) {
		this(item, count, new ArrayList<>());
		for (Option option : item.getOptionList()) {
			setSelectedOption(option.getId(), -1);
		}
	}

	public OrderItem(Item item, List<SelectedOption> selectedOptions) {
		this(item, 1, selectedOptions);
	}

	public OrderItem(Item item, int count, List<SelectedOption> selectedOptions) {
		this(-1, item, count, selectedOptions, 0);
		calcTotal();
	}

	public OrderItem(int id, Item item, int count, List<SelectedOption> selectedOptions, int total) {
		this.id = id;
		this.item = item;
		this.count = count;
		this.selectedOptions = selectedOptions;
		this.total = total;
	}

	// クラス内のデータ保持用のオプションの選択状況を持つレコード.
	public record SelectedOption(int optId, int selectionId) {
		public boolean isUnselected() {
			return selectionId < 0;
		}
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

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}

	public List<SelectedOption> getSelectedOptions() {
		return selectedOptions;
	}

	public void setSelectedOptions(List<SelectedOption> selectedOptions) {
		this.selectedOptions = selectedOptions;
	}

	public List<SelectedOption> getSelectedOptionList() {
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
	public List<SelectedOptionDetail> getSelectedOptionDetailList() {
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

	public void setSelectedOptionList(List<SelectedOption> option) {
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

	// 小計を計算する
	private void calcTotal() {
	    if (item == null) return;

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

	// 未選択のオプションがあるかを返す.
	public boolean hasOptionUnselected() {
		boolean res = false;
		for (SelectedOption selectedOption : selectedOptions) {
			if (selectedOption.isUnselected()) {
				return true;
			}
		}
		return res;
	}
	public boolean isSameItemAndOption(OrderItem other) {
		if (this.item.getId() != other.item.getId()) {
			return false;
		}

		for (SelectedOption so : this.selectedOptions) {
			SelectedOption otherSo = other.findSelectedOptionById(so.optId());
			if (otherSo == null || so.selectionId() != otherSo.selectionId()) {
				return false;
			}
		}
		return true;
	}
	
	private void calcTotalBySnapshot() {
	    int optionPriceSum = 0;
	    for (SelectedOptionDetail d : getSelectedOptionDetailList()) {
	        optionPriceSum += d.price();
	    }
	    total = (itemPrice + optionPriceSum) * count;
	}

	// 注文確定時に呼ぶ
	public void freezeFromItem() {
	    if (item == null) return;

	    this.itemId = item.getId();
	    this.itemName = item.getName();
	    this.itemPrice = item.getPrice();

	    calcTotalBySnapshot();
	}

}
