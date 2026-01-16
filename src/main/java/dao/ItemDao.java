package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Item;
import model.Option;

public class ItemDao {

	// 商品を追加する.
	public void addItem(Item item) throws Exception {
		// SQL文作成.
		String sql = "INSERT INTO"
				+ " item(item_name,category_id,order_number,price,item_image,stock)"
				+ " VALUES(?,?,?,?,?,?);";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {
			// プリペアードステートメントを使用.
			preState.setString(1, item.getItemName());
			preState.setInt(2, item.getCategoryId());
			preState.setInt(3, item.getItemNo());
			preState.setInt(4, item.getPrice());
			preState.setString(5, item.getImage());
			preState.setBoolean(6, item.isStock());
			preState.executeUpdate();
		} catch (Exception e) {
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
		String sql = "UPDATE item"
				+ " SET item_name = ?,category_id = ?,order_number = ?,price = ?,item_image = ?,stock = ?)"
				+ " WHERE item_id = ?;";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {

			// 複数テーブルに挿入する必要があるので自動コミットを無効.
			con.setAutoCommit(false);

			// プリペアードステートメントを使用.
			preState.setString(1, item.getItemName());
			preState.setInt(2, item.getCategoryId());
			preState.setInt(3, item.getItemNo());
			preState.setInt(4, item.getPrice());
			preState.setString(5, item.getImage());
			preState.setBoolean(6, item.isStock());
			preState.setInt(7, item.getId());
			preState.executeUpdate();
		} catch (Exception e) {
			// デバッグ用のスタックトレース.
			e.printStackTrace();

			// フロントエンド用のエラーメッセージ.
			String errMsg = "DB接続に失敗しました！<br>管理者に連絡してください。";

			// 例外を投げる.
			throw new Exception(errMsg);
		}
	}

	// 商品を削除する.
	public void delItem(Item item) throws Exception {
		// SQL文作成.
		String sql = "DALETE FROM item"
				+ " WHERE item_id = ?";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {
			// プリペアードステートメントを使用.
			preState.setInt(1, item.getId());
			preState.executeUpdate();
		} catch (Exception e) {
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

		// 返却値の参照変数を初期化.
		ArrayList<Item> resList = new ArrayList<Item>();

		// SQL文作成.
		String sql1 = "SELECT item_id,item_name,item.category_id,category_name,order_number,price,item_image,stock "
				+ "FROM item INNER JOIN category ON item.category_id = category.category_id "
				+ "WHERE item_name LIKE ?;";
		String sql2 = "SELECT item_id,item_name,item.category_id,category_name,order_number,price,item_image,stock "
				+ "FROM item INNER JOIN category ON item.category_id = category.category_id "
				+ "WHERE item_id = ?;";
		String sql3 = "SELECT option_detail_id,option_detail_name,price "
				+ "FROM option_detail "
				+ "WHERE option_id = ?;";

		try (Connection con = DatabaseManager.connect();
				PreparedStatement preState1 = con.prepareStatement(sql1);
				PreparedStatement preState2 = con.prepareStatement(sql2);
				PreparedStatement preState3 = con.prepareStatement(sql3);) {

			// プリペアードステートメントを使用.
			preState1.setString(1, "%" + name + "%");

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
									option.addSelection(resSet3.getInt("option_detail_id"),
											resSet3.getString("option_detail_name"), resSet3.getInt("price"));
								}
							}

							// オプションをItemインスタンスに追加.
							item.addOption(option);
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

	// 商品を注文番号(order_number)で探す.
	public Item searchItemByNumber(int num) throws Exception {
		// 返却値の参照変数を初期化.
		Item resItem = null;

		// SQL文作成.
		String sql1 = "SELECT item_id,item_name,item.category_id,category_name,order_number,price,item_image,stock "
				+ "FROM item INNER JOIN category ON item.category_id = category.category_id "
				+ "WHERE order_number = ?;";
		String sql2 = "SELECT item_id,item_name,item.category_id,category_name,order_number,price,item_image,stock "
				+ "FROM item INNER JOIN category ON item.category_id = category.category_id "
				+ "WHERE item_id = ?;";
		String sql3 = "SELECT option_detail_id,option_detail_name,price "
				+ "FROM option_detail "
				+ "WHERE option_id = ?;";

		try (Connection con = DatabaseManager.connect();
				PreparedStatement preState1 = con.prepareStatement(sql1);
				PreparedStatement preState2 = con.prepareStatement(sql2);
				PreparedStatement preState3 = con.prepareStatement(sql3);) {

			// プリペアードステートメントを使用.
			preState1.setInt(1, num);
			preState2.setInt(1, num);

			try (ResultSet resSet1 = preState1.executeQuery();
					ResultSet resSet2 = preState2.executeQuery();) {

				// 検索結果からItemインスタンスを生成.
				Item item = new Item(resSet1.getInt("item_id"), resSet1.getString("item_name"),
						resSet1.getInt("category_id"), resSet1.getString("category_name"),
						resSet1.getInt("order_number"), resSet1.getInt("price"),
						resSet1.getString("item_image"), resSet1.getBoolean("stock"));

				while (resSet2.next()) {
					// Optionインスタンスを生成.
					Option option = new Option(resSet2.getInt("option_id"), resSet2.getString("option_name"));

					// オプションの主キーを取得して選択肢を検索.
					preState3.setInt(1, resSet2.getInt("option_id"));
					try (ResultSet resSet3 = preState3.executeQuery();) {

						//　オプションの選択肢をOptionインスタンスに追加.
						while (resSet3.next()) {
							option.addSelection(resSet3.getInt("option_detail_id"),
									resSet3.getString("option_detail_name"), resSet3.getInt("price"));
						}
					}

					// オプションをItemインスタンスに追加.
					item.addOption(option);
				}

				// 作成したItemオブジェクトを返却値に入れる.
				resItem = item;
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
		return resItem;
	}

	// 商品を商品IDで探す.
	public Item searchItemById(int id) throws Exception {
		// 返却値の参照変数を初期化.
		Item resItem = null;

		// SQL文作成.
		String sql1 = "SELECT item_id,item_name,item.category_id,category_name,order_number,price,item_image,stock "
				+ "FROM item INNER JOIN category ON item.category_id = category.category_id "
				+ "WHERE item_id = ?;";
		String sql2 = "SELECT item_id,item_name,item.category_id,category_name,order_number,price,item_image,stock "
				+ "FROM item INNER JOIN category ON item.category_id = category.category_id "
				+ "WHERE item_id = ?;";
		String sql3 = "SELECT option_detail_id,option_detail_name,price "
				+ "FROM option_detail "
				+ "WHERE option_id = ?;";

		try (Connection con = DatabaseManager.connect();
				PreparedStatement preState1 = con.prepareStatement(sql1);
				PreparedStatement preState2 = con.prepareStatement(sql2);
				PreparedStatement preState3 = con.prepareStatement(sql3);) {

			// プリペアードステートメントを使用.
			preState1.setInt(1, id);
			preState2.setInt(1, id);

			try (ResultSet resSet1 = preState1.executeQuery();
					ResultSet resSet2 = preState2.executeQuery();) {

				// 検索結果からItemインスタンスを生成.
				Item item = new Item(resSet1.getInt("item_id"), resSet1.getString("item_name"),
						resSet1.getInt("category_id"), resSet1.getString("category_name"),
						resSet1.getInt("order_number"), resSet1.getInt("price"),
						resSet1.getString("item_image"), resSet1.getBoolean("stock"));

				while (resSet2.next()) {
					// Optionインスタンスを生成.
					Option option = new Option(resSet2.getInt("option_id"), resSet2.getString("option_name"));

					// オプションの主キーを取得して選択肢を検索.
					preState3.setInt(1, resSet2.getInt("option_id"));
					try (ResultSet resSet3 = preState3.executeQuery();) {

						//　オプションの選択肢をOptionインスタンスに追加.
						while (resSet3.next()) {
							option.addSelection(resSet3.getInt("option_detail_id"),
									resSet3.getString("option_detail_name"), resSet3.getInt("price"));
						}
					}

					// オプションをItemインスタンスに追加.
					item.addOption(option);
				}

				// 作成したItemオブジェクトを返却値に入れる.
				resItem = item;
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
		return resItem;
	}

	// カテゴリー一覧を取得する.
	public Map<Integer, String> categoryGetList() throws Exception {
		// 返却値の参照変数を初期化.
		Map<Integer, String> map = new HashMap<>();

		// SQL文作成.
		String sql = "SELECT category_id,category_name"
				+ " FROM category";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {
			try (ResultSet resSet = preState.executeQuery();) {
				//検索結果をmapに格納.
				while (resSet.next()) {
					map.put(resSet.getInt("category_id"), resSet.getString("category_name"));
				}
			}

		} catch (Exception e) {
			// デバッグ用のスタックトレース.
			e.printStackTrace();

			// フロントエンド用のエラーメッセージ.
			String errMsg = "DB接続に失敗しました！<br>管理者に連絡してください。";

			// 例外を投げる.
			throw new Exception(errMsg);
		}

		return map;
	}

}