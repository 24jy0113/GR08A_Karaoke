package model;

import java.util.ArrayList;
import java.util.List;

public class Option {
	private int id;
	private String name;
	private List<Selection> selections;

	public Option(int id, String name, List<Selection> selections) {
		this.id = id;
		this.name = name;
		this.selections = selections;
	}

	public Option(int id, String name) {
		this(id, name, new ArrayList<>());
	}

	public record Selection(int id, String name, int price) {
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	// 選択肢IDに対応するSelectionを返す。見つからないとnullが出るのでnullチェックをすること.
	public Selection findSelectionById(int selectionId) {
		Selection resSel = null;
		for (Selection selection : selections) {
			if (selectionId == selection.id) {
				resSel = selection;
				break;
			}
		}
		return resSel;
	}

	public List<Selection> getSelectionList() {
		return selections;
	}

	public void setSelection(int id, String name, int price) {
		// レコードはフィールドがfinalなので一度消してから追加してます.
		delSelection(id);
		selections.add(new Selection(id, name, price));
	}

	// 選択肢IDに対応するSelectionを削除する.
	public void delSelection(int id) {
		for (int i = 0; i < selections.size(); i++) {
			if (id == selections.get(i).id) {
				selections.remove(i);
				break;
			}
		}
	}

}
