package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.Item;
import model.Option;

public class ItemDao {

	// 商品を追加する.
	public void addItem(Item item) throws Exception {
		// SQL文作成.
		String sql1 = "INSERT INTO item(item_name,category_id,order_number,price,item_image,stock) "
				+ "VALUES(?,?,?,?,?,?);";
		String sql2 = "INSERT INTO item_option(item_id,option_id) "
				+ "VALUES(?,?);";

		try (Connection con = DatabaseManager.connect();
				PreparedStatement preState1 = con.prepareStatement(sql1, PreparedStatement.RETURN_GENERATED_KEYS);
				PreparedStatement preState2 = con.prepareStatement(sql2);) {

			// 複数テーブルに挿入する必要があるので自動コミットを無効.
			con.setAutoCommit(false);

			// プリペアードステートメントを使用.
			preState1.setString(1, item.getName());
			preState1.setInt(2, item.getCategoryId());
			preState1.setInt(3, item.getItemNo());
			preState1.setInt(4, item.getPrice());
			preState1.setString(5, item.getImage().replace("items/", ""));
			preState1.setBoolean(6, item.isStock());

			try {
				// 商品テーブルに商品を登録.
				preState1.executeUpdate();

				if (!item.getOptionList().isEmpty()) {
					try (ResultSet resSet = preState1.getGeneratedKeys()) {
						// 生成された主キーを取得.
						if (resSet.next()) {
							int generatedId = resSet.getInt(1);

							for (Option option : item.getOptionList()) {
								preState2.setInt(1, generatedId);
								preState2.setInt(2, option.getId());

								// 複数行の挿入をするためバッチ処理に入れる.
								preState2.addBatch();
							}
							// オプションのつながりを登録.
							preState2.executeBatch();
						}
					}
				}

				// すべて成功したらコミット.
				con.commit();
			} catch (SQLException e) {

				// 挿入時に例外が出たらロールバックする.
				con.rollback();

				// 例外を投げる.
				throw e;

			}
		} catch (SQLException e) {
			// デバッグ用のスタックトレース.
			e.printStackTrace();

			// フロントエンド用のエラーメッセージ.
			String errMsg = "DB接続に失敗しました！<br>管理者に連絡してください。";

			// 例外を投げる.
			throw new Exception(errMsg);
		}

	}

	// 商品を更新する.
	public void updateItem(Item item) throws Exception {
		// SQL文作成.
		String sql1 = "UPDATE item"
				+ " SET item_name = ?,category_id = ?,order_number = ?,price = ?,item_image = ?,stock = ?"
				+ " WHERE item_id = ?;";
		String sql2 = "DELETE FROM item_option WHERE item_id=?;";
		String sql3 = "INSERT INTO item_option(item_id,option_id) "
				+ "VALUES(?,?);";

		try (Connection con = DatabaseManager.connect();
				PreparedStatement preState1 = con.prepareStatement(sql1);
				PreparedStatement preState2 = con.prepareStatement(sql2);
				PreparedStatement preState3 = con.prepareStatement(sql3);) {

			// 複数テーブルに挿入する必要があるので自動コミットを無効.
			con.setAutoCommit(false);

			// プリペアードステートメントを使用.
			preState1.setString(1, item.getName());
			preState1.setInt(2, item.getCategoryId());
			preState1.setInt(3, item.getItemNo());
			preState1.setInt(4, item.getPrice());
			preState1.setString(5, item.getImage().replace("items/", ""));
			preState1.setBoolean(6, item.isStock());
			preState1.setInt(7, item.getId());
			try {

				// 商品テーブルに商品を登録.
				preState1.executeUpdate();

				// オプションのつながりを登録するために一度対象の商品のデータを削除.
				preState2.setInt(1, item.getId());
				preState2.executeUpdate();

				preState3.setInt(1, item.getId());
				for (Option option : item.getOptionList()) {
					preState3.setInt(2, option.getId());

					// 複数行の挿入をするためバッチ処理に入れる.
					preState3.addBatch();
				}

				// オプションのつながりを登録.
				preState3.executeBatch();

				// すべて成功したらコミット.
				con.commit();
			} catch (SQLException e) {

				// 挿入時に例外が出たらロールバックする.
				con.rollback();

				// 例外を投げる.
				throw e;

			}

		} catch (SQLException e) {
			// デバッグ用のスタックトレース.
			e.printStackTrace();

			// フロントエンド用のエラーメッセージ.
			String errMsg = "DB接続に失敗しました！<br>管理者に連絡してください。";

			// 例外を投げる.
			throw new Exception(errMsg);
		}
	}

	// 商品を名前の部分一致で探す.
	public List<Item> searchItemByName(String name) throws Exception {
		// 返却値の参照変数.
		var list = searchItem("WHERE item_name LIKE ?;", "%" + name + "%");

		// 結果を返却.
		return list;

	}

	// 商品を注文番号(order_number)で探す.
	public Item searchItemByNumber(int num) throws Exception {
		// 返却値の参照変数.
		var list = searchItem("WHERE order_number = ?;", num);

		// 結果を返却.
		return list.isEmpty() ? null : list.get(0);
	}

	// 商品を商品IDで探す.
	public Item searchItemById(int id) throws Exception {
		// 返却値の参照変数.
		var list = searchItem("WHERE item_id = ?;", id);

		// 結果を返却.
		return list.isEmpty() ? null : list.get(0);
	}

	private List<Item> searchItem(String condition, Object... args) throws Exception {
		// 返却値の参照変数を初期化.
		ArrayList<Item> resList = new ArrayList<>();

		// 条件に含まれるプレースホルダの個数を取得する.
		int placeholderCount = condition.length() - condition.replace("?", "").length();

		// SQL文作成.
		String sql1 = "SELECT item_id,item_name,item.category_id,category_name,order_number,price,item_image,stock "
				+ "FROM item INNER JOIN category ON item.category_id = category.category_id "
				+ condition;
		String sql2 = "SELECT option_id "
				+ "FROM item_option "
				+ "WHERE item_id = ?;";

		try (Connection con = DatabaseManager.connect();
				PreparedStatement preState1 = con.prepareStatement(sql1);
				PreparedStatement preState2 = con.prepareStatement(sql2);) {

			// 引数に受け取ったオブジェクトをプレースホルダにセットする.
			for (int i = 0; i < args.length; i++) {
				if (i < placeholderCount)
					preState1.setObject(i + 1, args[i]);
			}

			try (ResultSet resSet1 = preState1.executeQuery();) {
				while (resSet1.next()) {

					// 検索結果からItemインスタンスを生成.
					Item item = new Item(resSet1.getInt("item_id"), resSet1.getString("item_name"),
							resSet1.getInt("category_id"), resSet1.getString("category_name"),
							resSet1.getInt("order_number"), resSet1.getInt("price"),
							resSet1.getString("item_image"), resSet1.getBoolean("stock"));

					// 商品の主キーを取得してオプションを検索.
					preState2.setInt(1, resSet1.getInt("item_id"));
					List<Integer> optionIdList = new ArrayList<>();
					try (ResultSet resSet2 = preState2.executeQuery();) {
						while (resSet2.next()) {
							optionIdList.add(resSet2.getInt("option_id"));
						}
					}
					// オプションがある場合のみ取得
					if (!optionIdList.isEmpty()) {
						item.setOptionList(
								searchOptionByOptionIdList(con, optionIdList));
					} else {
						item.setOptionList(new ArrayList<>());
					}

					// 作成したItemオブジェクトを返却値に入れる.
					resList.add(item);
				}
			}

		} catch (SQLException e) {
			// デバッグ用のスタックトレース.
			e.printStackTrace();

			// フロントエンド用のエラーメッセージ.
			String errMsg = "DB接続に失敗しました！<br>管理者に連絡してください。";

			// 例外を投げる.
			throw new Exception(errMsg);
		}

		// 結果を返却.
		return resList;
	}

	// 選択肢が入っていないオプションの動的配列を受け取り、それぞれに選択肢を入れて返す.
	private void setSelectionsByOptions(Connection con, List<Option> optionList) throws SQLException {

		// 引数のリストがnullか空なら何もせず戻る.
		if (optionList == null || optionList.isEmpty())
			return;

		Map<Integer, Option> optionMap = optionList.stream().collect(Collectors.toMap(Option::getId, o -> o));

		// 引数のリストの長さ分、?をカンマ区切りにしたものを生成.
		String placeholders = optionList.stream()
				.map(o -> "?")
				.collect(Collectors.joining(","));

		// SQL文作成.
		String sql = "SELECT option_id, option_detail_id, option_detail_name, price "
				+ "FROM option_detail "
				+ "WHERE option_id IN (" + placeholders + ");";

		try (PreparedStatement preState = con.prepareStatement(sql);) {
			// プリペアードステートメントを使用.
			for (int i = 0; i < optionList.size(); i++) {
				preState.setInt(i + 1, optionList.get(i).getId());
			}
			try (ResultSet resSet = preState.executeQuery()) {
				while (resSet.next()) {
					int oid = resSet.getInt("option_id");
					Option target = optionMap.get(oid);
					target.setSelection(resSet.getInt("option_detail_id"), resSet.getString("option_detail_name"),
							resSet.getInt("price"));
				}
			}
		}
	}

	// オプションをオプションIDのリストから取得する.
	private List<Option> searchOptionByOptionIdList(Connection con, List<Integer> optionIdList) throws SQLException {

		// 返却値の参照変数を初期化.
		List<Option> resList = new ArrayList<>();

		// 引数のリストがnullか空なら空のListを返す.
		if (optionIdList == null || optionIdList.isEmpty())
			return resList;

		// 引数の長さ分、?をカンマ区切りにしたものを生成.
		String placeholders = optionIdList.stream()
				.map(o -> "?")
				.collect(Collectors.joining(","));

		// SQL文作成.
		String sql = "SELECT option_id, option_name "
				+ "FROM `option` "
				+ "WHERE option_id IN (" + placeholders + ");";

		try (PreparedStatement preState = con.prepareStatement(sql)) {
			for (int i = 0; i < optionIdList.size(); i++) {
				preState.setInt(i + 1, optionIdList.get(i));
			}

			try (ResultSet resSet = preState.executeQuery();) {
				while (resSet.next()) {

					// Optionインスタンスを生成.
					Option option = new Option(resSet.getInt("option_id"), resSet.getString("option_name"));

					// オプションをItemインスタンスに追加.
					resList.add(option);
				}
			}
		}
		setSelectionsByOptions(con, resList);

		// 結果を返却.
		return resList;
	}

	public List<Option> searchOptionByOptionIdList(List<Integer> optionIdList) throws Exception {

		// 返却値の参照変数を初期化.
		List<Option> resList = new ArrayList<>();

		try (Connection con = DatabaseManager.connect();) {
			resList = searchOptionByOptionIdList(con, optionIdList);
		} catch (SQLException e) {
			// デバッグ用のスタックトレース.
			e.printStackTrace();

			// フロントエンド用のエラーメッセージ.
			String errMsg = "DB接続に失敗しました！<br>管理者に連絡してください。";

			// 例外を投げる.
			throw new Exception(errMsg);
		}

		return resList;
	}

	// オプションをカテゴリーIDで仕分けて取得する.
	public Map<Integer, List<Option>> getAllOptionsGroupedByCategory() throws Exception {

		// 返却値の参照変数を初期化.
		Map<Integer, List<Option>> resMap = new HashMap<>();

		// SQL文作成.
		String sql = "SELECT `option`.option_id, option_name, category_id "
				+ "FROM `option` INNER JOIN category_option "
				+ "ON `option`.option_id = category_option.option_id;";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {

			try (ResultSet resSet = preState.executeQuery();) {
				//検索結果をmapに格納.
				while (resSet.next()) {
					int categoryId = resSet.getInt("category_id");
					Option opt = new Option(resSet.getInt("option_id"), resSet.getString("option_name"));

					resMap.computeIfAbsent(categoryId, k -> new ArrayList<>()).add(opt);
				}
			}

			for (List<Option> options : resMap.values()) {
				setSelectionsByOptions(con, options);
			}

		} catch (SQLException e) {
			// デバッグ用のスタックトレース.
			e.printStackTrace();

			// フロントエンド用のエラーメッセージ.
			String errMsg = "DB接続に失敗しました！<br>管理者に連絡してください。";

			// 例外を投げる.
			throw new Exception(errMsg);
		}

		// 結果を返却.
		return resMap;
	}

	// カテゴリー一覧を取得する.
	public Map<Integer, String> getCategoryList() throws Exception {
		// 返却値の参照変数を初期化.
		Map<Integer, String> resMap = new HashMap<>();

		// SQL文作成.
		String sql = "SELECT category_id,category_name"
				+ " FROM category;";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {
			try (ResultSet resSet = preState.executeQuery();) {
				//検索結果をmapに格納.
				while (resSet.next()) {
					resMap.put(resSet.getInt("category_id"), resSet.getString("category_name"));
				}
			}

		} catch (SQLException e) {
			// デバッグ用のスタックトレース.
			e.printStackTrace();

			// フロントエンド用のエラーメッセージ.
			String errMsg = "DB接続に失敗しました！<br>管理者に連絡してください。";

			// 例外を投げる.
			throw new Exception(errMsg);
		}

		return resMap;
	}

	// 全件またはカテゴリーで絞って商品一覧を取得する（from さい）.
	public ArrayList<Item> getItemList(Integer categoryId) throws Exception {

		// 返却値の参照変数を初期化.
		ArrayList<Item> resList = new ArrayList<>();

		// 条件を指定する.
		String condition = "";

		// categoryIdがあればWHERE句を追加する.
		if (categoryId != null) {
			condition += " WHERE item.category_id = ?";
		}

		// 検索を実行して返却用変数に格納.
		resList = (ArrayList<Item>) searchItem(condition, categoryId);

		return resList;
	}

	// 全件またはカテゴリーで絞って、件数を指定して、商品一覧を取得する（from さい）.
	public ArrayList<Item> getItemListByPage(Integer categoryId, int offset, int limit, boolean alcohol)
			throws Exception {
		// 返却値の参照変数を初期化.
		ArrayList<Item> resList = new ArrayList<>();

		// 条件を指定する.
		String condition = "";

		// プレースホルダ用のリスト.
		List<Object> paramList = new ArrayList<>();

		// categoryIdがあればWHERE句を追加する.
		if (categoryId != null) {
			condition += "WHERE item.category_id = ? ";
			paramList.add(categoryId);
		}

		// 酒類の提供がないなら条件を増やす.
		if (!alcohol) {
			if (categoryId == null) {
				condition += "WHERE ";
			} else {
				condition += "AND ";
			}
			condition += "NOT (item.category_id = 1) ";
		}

		// 件数の指定を条件に追加する.
		condition += "ORDER BY item_id LIMIT ? OFFSET ?;";
		paramList.add(limit);
		paramList.add(offset);

		resList = (ArrayList<Item>) searchItem(condition, paramList.toArray());

		return resList;
	}

	public int getItemCount(Integer categoryId) throws Exception {

		String sql = "SELECT COUNT(*) FROM item";
		if (categoryId != null) {
			sql += " WHERE category_id = ?";
		}

		try (Connection con = DatabaseManager.connect();
				PreparedStatement ps = con.prepareStatement(sql)) {

			if (categoryId != null) {
				ps.setInt(1, categoryId);
			}

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		return 0;
	}

}