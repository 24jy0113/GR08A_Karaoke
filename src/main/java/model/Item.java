package model;

import java.util.ArrayList;

public class Item {
	private int id;
	private String name;
	private int categoryId;
	private String category;
	private int itemNo;
	private int price;
	private String image;
	private boolean stock;
	private ArrayList<Option> options;

	public Item(String name, int categoryId, String category, int itemNo, int price, String image,
			boolean stock) {
		this(-1, name, categoryId, category, itemNo, price, image, stock);
	}

	public Item(int id, String name, int categoryId, String category, int itemNo, int price, String image,
			boolean stock) {
		this.id = id;
		this.name = name;
		this.categoryId = categoryId;
		this.category = category;
		this.itemNo = itemNo;
		this.price = price;
		this.image = image;
		this.stock = stock;
		options = new ArrayList<>();
	}

	public ArrayList<Option> getOptionList() {
		return options;
	}

	public void setOptionList(ArrayList<Option> options) {
		this.options = options;
	}

	// オプションIDに対応するOptionクラスを返す。見つからないとnullが出るのでnullチェックをすること.
	public Option findOptionById(int optId) {
		Option resOpt = null;
		for (Option option : options) {
			if (optId == option.getId()) {
				resOpt = option;
				break;
			}
		}
		return resOpt;
	}

	public void setOption(Option option) {
		// レコードはフィールドがfinalなので一度消してから追加してます.
		delOption(option.getId());
		options.add(option);
	}

	// オプションIDに対応するOptionを削除する.
	public void delOption(int optId) {
		for (int i = 0; i < options.size(); i++) {
			if (optId == options.get(i).getId())
				options.remove(i);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isStock() {
		return stock;
	}

	public void setStock(boolean stock) {
		this.stock = stock;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getItemNo() {
		return itemNo;
	}

	public void setItemNo(int itemNo) {
		this.itemNo = itemNo;
	}

}