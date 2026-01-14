package model;

import java.util.ArrayList;

public class Item {
	private int id;
	private String itemName;
	private int categoryId;
	private String category;
	private int itemNo;
	private int price;
	private String image;
	private boolean stock;
	private ArrayList<Option> options;

	public Item() {
	}

	public Item(String name, int categoryId, String category, int itemNo, int price, String image,
			boolean stock) {
		this(-1, name, categoryId, category, itemNo, price, image, stock);
	}

	public Item(int id, String name, int categoryId, String category, int itemNo, int price, String image,
			boolean stock) {
		this.id = id;
		itemName = name;
		this.categoryId = categoryId;
		this.category = category;
		this.itemNo = itemNo;
		this.price = price;
		this.image = image;
		this.stock = stock;
	}

	public ArrayList<Option> getOptions() {
		return options;
	}

	public void setOptions(ArrayList<Option> options) {
		this.options = options;
	}

	public void addOptions(Option option) {
		options.add(option);
	}

	public void delOptions(int id) {
		for (int i = 0; i < options.size(); i++) {
			if (id == options.get(i).getId())
				options.remove(i);
		}
	}
	public void addSelection(int index,int id, String name, int price) {
		options.get(index).addSelection(id, name, price);
	}
	public void delSelection(int index,int id) {
		options.get(index).delSelection(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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