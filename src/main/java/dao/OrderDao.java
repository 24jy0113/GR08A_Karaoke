package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Order;
import model.OrderItem;

public class OrderDao {
	public void addOrder(Order order) throws Exception {

		if (order.getId() > 0) {
			System.err.println("order_idが割り振られているため、このorderは登録済みです。");

			// フロントエンド用のエラーメッセージ
			String errMsg = "注文の登録が重複したためDBの登録に失敗しました！<br>管理者に連絡してください。";

			// 例外を投げる
			throw new Exception(errMsg);
		}

		// SQL文の作成
		String sql1 = "INSERT INTO orders(total,receiving_number,item_creating_status_id,room_id) VALUE(?,?,?,?);";
		String sql2 = "INSERT INTO order_detail(order_id,item_id,`count`,sub_total) VALUE(?,?,?,?);";
		String sql3 = "INSERT INTO order_detail_option(order_detail_id,option_detail_id) VALUE(?,?);";

		try (Connection con = DatabaseManager.connect();
				PreparedStatement preState1 = con.prepareStatement(sql1, PreparedStatement.RETURN_GENERATED_KEYS);
				PreparedStatement preState2 = con.prepareStatement(sql2, PreparedStatement.RETURN_GENERATED_KEYS);
				PreparedStatement preState3 = con.prepareStatement(sql3)) {

			// データベース接続
			con = DatabaseManager.connect();

			// プリペアードステートメントを使用してSQL文を実行
			preState1.setInt(0, order.getTotal());
			preState1.setInt(1, order.getReceivingNo());
			preState1.executeUpdate();

		} catch (SQLException e) {

			// デバッグ用のスタックトレース
			e.printStackTrace();

			// フロントエンド用のエラーメッセージ
			String errMsg = "DB接続に失敗しました！<br>管理者に連絡してください。";

			// 例外を投げる
			throw new Exception(errMsg);

		}
	}

	public void updateOrder(Order order) {
	}

	public void delOrder(Order order) {
	}

	public ArrayList<Order> searchOederByRoom(int roomId) throws Exception {

		// 返却値の参照変数を初期化.
		ArrayList<Order> list = null;

		// SQL文の作成
		String sql = "SELECT order_id,total,orders.room_id,room_number,receiving_number,"
				+ "orders.item_creating_status_id,item_creating_status_name "
				+ "FROM orders "
				+ "INNER JOIN item_creating_status "
				+ "ON orders.item_creating_status_id = item_creating_status.item_creating_status_id "
				+ "INNER JOIN room "
				+ "ON orders.room_id = room.room_id "
				+ "WHERE orders.room_id=?;";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {

			// プリペアードステートメントを使用してSQL文を実行
			preState.setInt(1, roomId);
			try (ResultSet resSet = preState.executeQuery();) {

				// 検索結果からOrderインスタンスを生成
				while (resSet.next()) {
					list.add(new Order(resSet.getInt("order_id"), searchOrderItem(resSet.getInt("order_id")),
							resSet.getInt("total"), resSet.getInt("orders.room_id"), resSet.getInt("room"),
							resSet.getInt("receiving_number"), resSet.getInt("orders.item_creating_status_id"),
							resSet.getString("orders.item_creating_status_name")));
				}
			}
		} catch (SQLException e) {

			// デバッグ用のスタックトレース
			e.printStackTrace();

			// フロントエンド用のエラーメッセージ
			String errMsg = "DB接続に失敗しました！<br>管理者に連絡してください。";

			// 例外を投げる
			throw new Exception(errMsg);

		}
		return list;
	}

	public ArrayList<Order> searchOederByStatus(int statusId) throws Exception {
		// 返却値の参照変数を初期化.
		ArrayList<Order> list = null;

		// SQL文の作成
		String sql = "SELECT order_id,total,orders.room_id,room_number,receiving_number,"
				+ "orders.item_creating_status_id,item_creating_status_name "
				+ "FROM orders "
				+ "INNER JOIN item_creating_status "
				+ "ON orders.item_creating_status_id = item_creating_status.item_creating_status_id "
				+ "INNER JOIN room "
				+ "ON orders.room_id = room.room_id "
				+ "WHERE orders.item_creating_status_id=?;";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {

			// プリペアードステートメントを使用してSQL文を実行
			preState.setInt(1, statusId);
			try (ResultSet resSet = preState.executeQuery();) {

				// 検索結果からOrderインスタンスを生成
				while (resSet.next()) {
					list.add(new Order(resSet.getInt("order_id"), searchOrderItem(resSet.getInt("order_id")),
							resSet.getInt("total"), resSet.getInt("orders.room_id"), resSet.getInt("room"),
							resSet.getInt("receiving_number"), resSet.getInt("orders.item_creating_status_id"),
							resSet.getString("orders.item_creating_status_name")));
				}
			}

		} catch (SQLException e) {

			// デバッグ用のスタックトレース
			e.printStackTrace();

			// フロントエンド用のエラーメッセージ
			String errMsg = "DB接続に失敗しました！<br>管理者に連絡してください。";

			// 例外を投げる
			throw new Exception(errMsg);

		}
		return list;

	}

	public void addOrderItem(OrderItem item, Order order) {
	}

	public void updateOrderItem(OrderItem item, Order order) {
	}

	public void delOrderItem(OrderItem item, Order order) {
	}

	public ArrayList<OrderItem> searchOrderItem(int order) throws Exception {

		// 返却値の参照変数を初期化.
		ArrayList<OrderItem> list = null;

		// SQL文の作成.
		String sql1 = "SELECT item_id,`count`,sub_total,order_detail_id "
				+ "FROM order_detail "
				+ "WHERE order_id=?;";
		String sql2 = "SELECT option_detail_id "
				+ "FROM order_detail_option "
				+ "WHERE order_detail_id=?;";
		try (Connection con = DatabaseManager.connect();
				PreparedStatement preState1 = con.prepareStatement(sql1);
				PreparedStatement preState2 = con.prepareStatement(sql2);) {

			// プリペアードステートメントを使用してSQL文を実行.
			// 注文単位でアイテムの情報を取得.
			preState1.setInt(1, order);
			try (ResultSet resSet1 = preState1.executeQuery();) {

				while (resSet1.next()) {
					// オプション選択を記録するためのリスト.
					ArrayList<Integer> option = new ArrayList<>();

					// プリペアードステートメントを使用してSQL文を実行.
					// 商品単位でオプションの情報を取得.
					preState2.setInt(1, resSet1.getInt("order_detail_id"));
					try (ResultSet resSet2 = preState1.executeQuery();) {

						// 結果をリストに格納.
						while (resSet2.next()) {
							option.add(resSet2.getInt("option_detail_id"));
						}
						// 検索結果からOrderインスタンスを生成.
						/*
						list.add(new OrderItem(resSet1.getInt("item_id"), , resSet1.getInt("count"),
								option.stream().mapToInt(Integer::intValue).toArray(), resSet1.getInt("sub_total")));
						*/
					}
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

		return list;
	}
}
