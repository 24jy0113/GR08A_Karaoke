package model;

import java.util.ArrayList;

public class Option {
	private Integer id;
	private String name;
	private ArrayList<Selection> selections;

	public Option(int id, String name, ArrayList<Selection> selections) {
		this.id = id;
		this.name = name;
		this.selections = selections;
	}

	public Option(int id, String name) {
		this(id, name, new ArrayList<>());
	}

	public record Selection(int id, String name, int price) {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addSelection(int id, String name, int price) {
		selections.add(new Selection(id, name, price));
	}

	public void delSelection(int id) {
		for (int i = 0; i < selections.size(); i++) {
			if (id == selections.get(i).id)
				selections.remove(i);
		}
	}

}
