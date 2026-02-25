package model;

import java.util.ArrayList;
import java.util.List;

public class Item {
	private int id;
	private String name;
	private int categoryId;
	private String category;
	private int itemNo;
	private int price;
	private String image;
	private boolean stock;
	private List<Option> options;

	public Item() {
		this(-1, "", 1, "アルコール", 0, 0, "default.png", false);
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

	public boolean hasOption() {
		return !options.isEmpty();
	}

	public List<Option> getOptionList() {
		return options;
	}

	public void setOptionList(List<Option> options) {
		this.options = options;
	}

	// オプションIDに対応するOptionクラスを返す.
	public Option findOptionById(int optId) {
		Option resOpt = new Option(-1, "なし");
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

	public boolean hasId() {
		return id < 1;
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

	// サーバー側の商品画像の保存場所を付ける.
	// ルートはimgファイル.
	public String getImage() {
		return "items/" + image;
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