package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Item;

public class ItemDao {

	// 商品を追加する
	public void addItem(Item item) {

		// プリペアードステートメントの参照変数
		PreparedStatement preState = null;

		// データベース接続の参照変数
		Connection con = null;

		// SQL文作成
		String sql = "INSERT INTO"
				+ " item(item_name,category_id,order_number,price,item_image,stock)"
				+ " VALUES(?,?,?,?,?,?)";

		try {
			// データベース接続
			con = DatabaseManager.connect();

			// プリペアードステートメントを使用
			preState = con.prepareStatement(sql);
			preState.setString(1, item.getItemName());
			preState.setInt(2, item.getCategoryId());
			preState.setInt(3, item.getItemNo());
			preState.setInt(4, item.getPrice());
			preState.setString(5, item.getImage());
			preState.setBoolean(6, item.isStock());
			preState.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 商品を更新する
	public void updateItem(Item item) {

		// プリペアードステートメントの参照変数
		PreparedStatement preState = null;

		// データベース接続の参照変数
		Connection con = null;

		// SQL文作成
		String sql = "UPDATE item"
				+ " SET item_name = ?,category_id = ?,order_number = ?,price = ?,item_image = ?,stock = ?)"
				+ " WHERE item_id = ?";

		try {
			// データベース接続
			con = DatabaseManager.connect();

			// プリペアードステートメントを使用
			preState = con.prepareStatement(sql);
			preState.setString(1, item.getItemName());
			preState.setInt(2, item.getCategoryId());
			preState.setInt(3, item.getItemNo());
			preState.setInt(4, item.getPrice());
			preState.setString(5, item.getImage());
			preState.setBoolean(6, item.isStock());
			preState.setInt(7, item.getId());
			preState.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 商品を削除する
	public void delItem(Item item) {

		// プリペアードステートメントの参照変数
		PreparedStatement preState = null;

		// データベース接続の参照変数
		Connection con = null;

		// SQL文作成
		String sql = "DALETE FROM item"
				+ " WHERE item_id = ?";

		try {
			// データベース接続
			con = DatabaseManager.connect();

			// プリペアードステートメントを使用
			preState = con.prepareStatement(sql);
			preState.setInt(1, item.getId());
			preState.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 商品を名前の部分一致で探す
	public ArrayList<Item> searchItemByName(String name) {

		// プリペアードステートメントの参照変数
		PreparedStatement preState = null;

		// データベース結果セットの参照変数
		ResultSet resSet = null;

		// 返却地の参照変数を初期化
		ArrayList<Item> list = new ArrayList<Item>();

		// データベース接続の参照変数
		Connection con = null;

		// SQL文作成
		String sql = "SELECT item_id,item_name,item.category_id,category_name,order_number,price,item_image,stock"
				+ " FROM item INNER JOIN category ON item.category_id = category.category_id"
				+ " WHERE item_name LIKE ?";

		try {
			// データベース接続
			con = DatabaseManager.connect();

			// プリペアードステートメントを使用
			preState = con.prepareStatement(sql);
			preState.setString(1, "%" + name + "%");
			resSet = preState.executeQuery();

			//検索結果からItemインスタンスを生成
			while (resSet.next()) {
				list.add(new Item(resSet.getInt("item_id"), resSet.getString("item_name"),
						resSet.getInt("category_id"), resSet.getString("category_name"),
						resSet.getInt("order_number"), resSet.getInt("price"),
						resSet.getString("item_image"), resSet.getBoolean("stock")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// 商品を注文番号(order_number)で探す
	public Item searchItemByNumber(int num) {

		// プリペアードステートメントの参照変数
		PreparedStatement preState = null;

		// データベース結果セットの参照変数
		ResultSet resSet = null;

		// 返却地の参照変数を初期化
		Item list = new Item();

		// データベース接続の参照変数
		Connection con = null;
		// SQL文作成
		String sql = "SELECT item_id,item_name,item.category_id,category_name,order_number,price,item_image,stock"
				+ " FROM item INNER JOIN category ON item.category_id = category.category_id"
				+ " WHERE order_number = ?";

		try {
			// データベース接続
			con = DatabaseManager.connect();

			// プリペアードステートメントを使用
			preState = con.prepareStatement(sql);
			preState.setInt(1, num);
			resSet = preState.executeQuery();

			//検索結果からItemインスタンスを生成

			list = new Item(resSet.getInt("item_id"), resSet.getString("item_name"),
					resSet.getInt("category_id"), resSet.getString("category_name"),
					resSet.getInt("order_number"), resSet.getInt("price"),
					resSet.getString("item_image"), resSet.getBoolean("stock"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// 商品を商品IDで探す
	public Item searchItemById(int id) {

		// プリペアードステートメントの参照変数
		PreparedStatement preState = null;

		// データベース結果セットの参照変数
		ResultSet resSet = null;

		// 返却地の参照変数を初期化
		Item list = new Item();

		// データベース接続の参照変数
		Connection con = null;
		// SQL文作成
		String sql = "SELECT item_id,item_name,item.category_id,category_name,order_number,price,item_image,stock"
				+ " FROM item INNER JOIN category ON item.category_id = category.category_id"
				+ " WHERE item_id = ?";
		try {
			// データベース接続
			con = DatabaseManager.connect();

			// プリペアードステートメントを使用
			preState = con.prepareStatement(sql);
			preState.setInt(1, id);
			resSet = preState.executeQuery();

			//検索結果からItemインスタンスを生成
			list = new Item(resSet.getInt("item_id"), resSet.getString("item_name"),
					resSet.getInt("category_id"), resSet.getString("category_name"),
					resSet.getInt("order_number"), resSet.getInt("price"),
					resSet.getString("item_image"), resSet.getBoolean("stock"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// カテゴリー一覧を取得する
	public Map<Integer, String> categoryGetList() {

		// プリペアードステートメントの参照変数
		PreparedStatement preState = null;

		// データベース結果セットの参照変数
		ResultSet resSet = null;

		// 返却地の参照変数を初期化
		Map<Integer, String> map = new HashMap<>();

		// データベース接続の参照変数
		Connection con = null;

		// SQL文作成
		String sql = "SELECT category_id,category_name"
				+ " FROM category";

		try {
			// データベース接続
			con = DatabaseManager.connect();

			// プリペアードステートメントを使用
			preState = con.prepareStatement(sql);
			resSet = preState.executeQuery();

			//検索結果をmapに格納
			while (resSet.next()) {
				map.put(resSet.getInt("category_id"), resSet.getString("category_name"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}
	
	
}