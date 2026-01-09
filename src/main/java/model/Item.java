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
	private ArrayList<Integer> optionId;
	private ArrayList<ArrayList<String>> optionName;
	private ArrayList<ArrayList<Integer>> optionPrice;

	public Item() {
	}

	public Item(int id, String name, int categoryId, String category, int itemNo, int price, String image,
			boolean stock) {
		this(id, name, categoryId, category, itemNo, price, image, stock, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>());
	}

	public Item(int id, String name, int categoryId, String category, int itemNo, int price, String image,
			boolean stock,
			ArrayList<Integer> optionId, ArrayList<ArrayList<String>> optionName,
			ArrayList<ArrayList<Integer>> optionPrice) {
		this.id = id;
		itemName = name;
		this.categoryId = categoryId;
		this.category = category;
		this.itemNo = itemNo;
		this.price = price;
		this.image = image;
		this.stock = stock;
		this.optionId = optionId;
		this.optionName = optionName;
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

	public ArrayList<Integer> getOptionIdList() {
		return optionId;
	}

	public int getOptionId(int i) {
		return optionId.get(i).intValue();
	}

	public void setOptionIdList(ArrayList<Integer> optionId) {
		this.optionId = optionId;
	}

	public ArrayList<ArrayList<String>> getOptionNameList() {
		return optionName;
	}

	public ArrayList<String> getOptionNameList(int i) {
		return optionName.get(i);
	}

	public String getOptionName(int i, int j) {
		return optionName.get(i).get(j);
	}

	public void setOptionNameList(ArrayList<ArrayList<String>> optionName) {
		this.optionName = optionName;
	}

	public ArrayList<ArrayList<Integer>> getOptionPriceList() {
		return optionPrice;
	}

	public ArrayList<Integer> getOptionPriceList(int i) {
		return optionPrice.get(i);
	}

	public int getOptionPrice(int i, int j) {
		return optionPrice.get(i).get(j).intValue();
	}

	public void setOptionPriceList(ArrayList<ArrayList<Integer>> optionPrice) {
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