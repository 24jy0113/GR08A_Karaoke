package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Item;

public class ItemDao {

	// 商品を追加する
	public void addItem(Item item) {

		// プリペアードステートメントの参照変数
		PreparedStatement preState = null;

		// データベース接続の参照変数
		Connection con = null;

		try {
			// データベース接続

			// SQL文作成
			String sql = "INSERT INTO"
					+ " item(item_name,category_id,order_number,price,item_image,stock)"
					+ " VALUES"
					+ " (?,?,?,?,?,?)";

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

		try {
			// データベース接続

			// SQL文作成
			String sql = "UPDATE item"
					+ " SET item_name = ?,category_id = ?,order_number = ?,price = ?,item_image = ?,stock = ?)"
					+ " WHERE item_id = ?";

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

		try {
			// データベース接続

			// SQL文作成
			String sql = "DALETE FROM item"
					+ " WHERE item_id = ?";

			// プリペアードステートメントを使用
			preState = con.prepareStatement(sql);
			preState.setInt(1, item.getId());
			preState.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 商品を名前の部分一致で探す
	public Item searchItemByName(String name) {
		// プリペアードステートメントの参照変数
		PreparedStatement preState = null;

		// データベース結果セットの参照変数
		ResultSet resSet = null;

		// 返却地の参照変数を初期化
		ArrayList<Item> list = new ArrayList<Item>();

		// データベース接続の参照変数
		Connection con = null;

		try {
			// データベース接続

			// SQL文作成
			String sql = "SELECT item_name,price,item_image,stock"
					+ " FROM item"
					+ " WHERE item_name LIKE ?";

			// プリペアードステートメントを使用
			preState = con.prepareStatement(sql);
			preState.setString(1, "%" + name + "%");
			resSet = preState.executeQuery();

			//検索結果からItemインスタンスを生成
			while(resSet.next()) {
				list.add(new Item(resSet.getInt("item_id"),resSet.getString("item_name"),
						resSet.getInt("category_id"),resSet.getInt("order_number"),
						resSet.getInt("price"),resSet.getString("item_image"),
						resSet.getBoolean("ctock")));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}