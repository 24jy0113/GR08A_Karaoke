package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
			preState1.setString(5, item.getImage());
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
			preState1.setString(5, item.getImage());
			preState1.setBoolean(6, item.isStock());
			preState1.setInt(7, item.getId());
			try {
				preState1.executeUpdate();
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
	public ArrayList<Item> searchItemByName(String name) throws Exception {
		// 返却値の参照変数.
		var list = searchItem("item_name LIKE ?;", "%" + name + "%");

		// 結果を返却.
		return list;

	}

	// 商品を注文番号(order_number)で探す.
	public Item searchItemByNumber(int num) throws Exception {
		// 返却値の参照変数.
		var list = searchItem("order_number = ?;", num);

		// 結果を返却.
		return list.isEmpty() ? null : list.get(0);
	}

	// 商品を商品IDで探す.
	public Item searchItemById(int id) throws Exception {
		// 返却値の参照変数.
		var list = searchItem("item_id = ?;", id);

		// 結果を返却.
		return list.isEmpty() ? null : list.get(0);
	}

	private ArrayList<Item> searchItem(String condition, Object... args) throws Exception {
		// 返却値の参照変数を初期化.
		ArrayList<Item> resList = new ArrayList<>();

		// SQL文作成.
		String sql1 = "SELECT item_id,item_name,item.category_id,category_name,order_number,price,item_image,stock "
				+ "FROM item INNER JOIN category ON item.category_id = category.category_id "
				+ "WHERE "+condition;
		String sql2 = "SELECT `option`.option_id,option_name "
				+ "FROM item_option "
				+ "INNER JOIN `option` "
				+ "ON item_option.option_id = `option`.option_id "
				+ "WHERE item_option.item_id = ?;";
		String sql3 = "SELECT option_detail_id,option_detail_name,price "
				+ "FROM option_detail "
				+ "WHERE option_id = ?;";

		try (Connection con = DatabaseManager.connect();
				PreparedStatement preState1 = con.prepareStatement(sql1);
				PreparedStatement preState2 = con.prepareStatement(sql2);
				PreparedStatement preState3 = con.prepareStatement(sql3);) {

			// プリペアードステートメントを使用.
			for (int i = 0; i < args.length; i++) {
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
					try (ResultSet resSet2 = preState2.executeQuery();) {
						while (resSet2.next()) {

							// Optionインスタンスを生成.
							Option option = new Option(resSet2.getInt("option_id"), resSet2.getString("option_name"));

							// オプションの主キーを取得して選択肢を検索.
							preState3.setInt(1, resSet2.getInt("option_id"));
							try (ResultSet resSet3 = preState3.executeQuery();) {
								while (resSet3.next()) {

									//　オプションの選択肢をOptionインスタンスに追加.
									option.setSelection(resSet3.getInt("option_detail_id"),
											resSet3.getString("option_detail_name"), resSet3.getInt("price"));
								}
							}

							// オプションをItemインスタンスに追加.
							item.setOption(option);
						}
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
	private void setSelectionsByOptions(Connection con, ArrayList<Option> optionList) throws SQLException {
		if (optionList == null || optionList.isEmpty())
			return;

		Map<Integer, Option> optionMap = optionList.stream().collect(Collectors.toMap(Option::getId, o -> o));

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

	// オプションをカテゴリーIDで探す.
	public ArrayList<Option> searchOptionByCategoryId(int category_id) throws Exception {

		// 返却値の参照変数を初期化.
		ArrayList<Option> resList = new ArrayList<>();

		// SQL文作成.
		String sql = "SELECT `option`.option_id, option_name "
				+ "FROM `option` INNER JOIN category_option "
				+ "ON `option`.option_id = category_option.option_id "
				+ "WHERE category_id = ?;";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {

			// プリペアードステートメントを使用.
			preState.setInt(1, category_id);
	// 選択肢が入っていないオプションの動的配列を受け取り、それぞれに選択肢を入れて返す.
	private void setSelectionsByOptions(Connection con, ArrayList<Option> optionList) throws SQLException {
		if (optionList == null || optionList.isEmpty())
			return;

		Map<Integer, Option> optionMap = optionList.stream().collect(Collectors.toMap(Option::getId, o -> o));

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

	// オプションをカテゴリーIDで探す.
	public ArrayList<Option> searchOptionByCategoryId(int category_id) throws Exception {

		// 返却値の参照変数を初期化.
		ArrayList<Option> resList = new ArrayList<>();

		// SQL文作成.
		String sql = "SELECT `option`.option_id, option_name "
				+ "FROM `option` INNER JOIN category_option "
				+ "ON `option`.option_id = category_option.option_id "
				+ "WHERE category_id = ?;";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {

			// プリペアードステートメントを使用.
			preState.setInt(1, category_id);


			try (ResultSet resSet = preState.executeQuery();) {
				//検索結果をmapに格納.
				while (resSet.next()) {
					resList.add(new Option(resSet.getInt("option_id"), resSet.getString("option_name")));
				}
			}

			setSelectionsByOptions(con, resList);

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

	// 商品一覧を取得（全件 or カテゴリ指定 from さい）.
	public ArrayList<Item> getItemList(Integer categoryId) throws Exception {

		ArrayList<Item> list = new ArrayList<>();

		String sql = "SELECT i.item_id, i.item_name, i.category_id, c.category_name, " +
				"i.order_number, i.price, i.item_image, i.stock " +
				"FROM item i " +
				"INNER JOIN category c ON i.category_id = c.category_id ";

		if (categoryId != null) {
			sql += " WHERE i.category_id = ?";
		}

		try (Connection con = DatabaseManager.connect();
				PreparedStatement ps = con.prepareStatement(sql)) {

			if (categoryId != null) {
				ps.setInt(1, categoryId);
			}

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Item item = new Item(
							rs.getInt("item_id"),
							rs.getString("item_name"),
							rs.getInt("category_id"),
							rs.getString("category_name"),
							rs.getInt("order_number"),
							rs.getInt("price"),
							rs.getString("item_image"),
							rs.getBoolean("stock"));
					list.add(item);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("商品一覧の取得に失敗しました");
		}

		return list;
	}

	public ArrayList<Item> getItemListByPage(Integer categoryId, int offset, int limit)
			throws Exception {

		ArrayList<Item> list = new ArrayList<>();

		String sql = "SELECT i.item_id, i.item_name, i.category_id, c.category_name, " +
				"i.order_number, i.price, i.item_image, i.stock " +
				"FROM item i " +
				"INNER JOIN category c ON i.category_id = c.category_id ";

		if (categoryId != null) {
			sql += " WHERE i.category_id = ? ";
		}

		sql += " ORDER BY i.item_id LIMIT ? OFFSET ?";

		try (Connection con = DatabaseManager.connect();
				PreparedStatement ps = con.prepareStatement(sql)) {

			int index = 1;

			if (categoryId != null) {
				ps.setInt(index++, categoryId);
			}

			ps.setInt(index++, limit);
			ps.setInt(index, offset);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Item item = new Item(
							rs.getInt("item_id"),
							rs.getString("item_name"),
							rs.getInt("category_id"),
							rs.getString("category_name"),
							rs.getInt("order_number"),
							rs.getInt("price"),
							rs.getString("item_image"),
							rs.getBoolean("stock"));
					list.add(item);
				}
			}
		}

		return list;
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