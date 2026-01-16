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

	public String getName() {
		return name;
	}

	public Selection getSelection(int id) {
		Selection resSel = null;
		for (Selection selection : selections) {
			if (id == selection.id)
				resSel = selection;
		}
		return resSel;
	}

	public ArrayList<Selection> getSelectionList() {
		return selections;
	}

	public void setSelection(int id, String name, int price) {
		delSelection(id);
		selections.add(new Selection(id, name, price));
	}

	public void delSelection(int id) {
		for (int i = 0; i < selections.size(); i++) {
			if (id == selections.get(i).id)
				selections.remove(i);
		}
	}

}
