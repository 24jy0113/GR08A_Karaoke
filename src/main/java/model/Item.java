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
	private ArrayList<String> option;
	private ArrayList<Integer> optionPrice;

	public Item(int id, String name, String category, int itemNo, int price, String image, boolean stock) {
		this(id, name, category, itemNo, price, image, stock, new ArrayList<>(), new ArrayList<>());
	}

	public Item(int id, String name, String category, int itemNo, int price, String image, boolean stock,
			ArrayList<String> option,
			ArrayList<Integer> optionPrice) {
		this.id = id;
		itemName = name;
		this.category = category;
		this.itemNo = itemNo;
		this.price = price;
		this.image = image;
		this.stock = stock;
		this.option = option;
		this.optionPrice = optionPrice;
	}
	
	public void isOption() {
		
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

	public ArrayList<String> getOptionList() {
		return option;
	}

	public String getOption(int i) {
		return option.get(i);
	}

	public void setOptionList(ArrayList<String> option) {
		this.option = option;
	}

	public ArrayList<Integer> getOptionPriceList() {
		return optionPrice;
	}

	public int getOptionPrice(int i) {
		return optionPrice.get(i);
	}

	public void setOptionPriceList(ArrayList<Integer> optionPrice) {
		this.optionPrice = optionPrice;
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