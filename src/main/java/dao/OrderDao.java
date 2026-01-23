package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Order;
import model.OrderItem;
import model.OrderItem.SelectedOption;

public class OrderDao {
	public void addOrder(Order order) throws Exception {

		if (order.getId() > 0) {
			System.err.println("order_idが割り振られているため、このorderは登録済みです。");

			// フロントエンド用のエラーメッセージ.
			String errMsg = "注文の登録が重複したためDBの登録に失敗しました！<br>管理者に連絡してください。";

			// 例外を投げる.
			throw new Exception(errMsg);
		}

		// SQL文の作成.
		// 注文の登録.
		String sql1 = "INSERT INTO orders(total,receiving_number,item_creating_status_id,room_id) "
				+ "VALUES(?,?,?,?);";
		// 注文詳細（OrderItemごと）の登録.
		String sql2 = "INSERT INTO order_detail(order_id,item_id,`count`,sub_total) "
				+ "VALUES(?,?,?,?);";
		// 注文詳細と選択された商品オプションのつながりの登録.
		String sql3 = "INSERT INTO order_detail_option(order_detail_id,option_detail_id) "
				+ "VALUES(?,?);";

		try (Connection con = DatabaseManager.connect();
				PreparedStatement preState1 = con.prepareStatement(sql1, PreparedStatement.RETURN_GENERATED_KEYS);
				PreparedStatement preState2 = con.prepareStatement(sql2, PreparedStatement.RETURN_GENERATED_KEYS);
				PreparedStatement preState3 = con.prepareStatement(sql3)) {

			// 複数テーブルに挿入する必要があるので自動コミットを無効.
			con.setAutoCommit(false);

			// プリペアードステートメントを使用.
			preState1.setInt(1, order.getTotal());
			preState1.setInt(2, order.getReceivingNo());
			preState1.setInt(3, order.getStatusId());
			preState1.setInt(4, order.getRoomId());

			try {

				// 注文をテーブルに登録.
				preState1.executeUpdate();

				// 生成された注文の主キーを取得して注文詳細を登録する.
				try (ResultSet resSet1 = preState1.getGeneratedKeys();) {
					if (resSet1.next()) {
						preState2.setInt(1, resSet1.getInt(1));

						for (OrderItem item : order.getItemList()) {
							preState2.setInt(2, item.getItem().getId());
							preState2.setInt(3, item.getTotal());
							preState2.setInt(4, item.getTotal());

							// 商品をテーブルに追加.
							preState2.executeUpdate();

							// 選択したオプションがある場合のみその情報を登録.
							if (item.getSelectedOptionList().isEmpty()) {

								// 生成された注文詳細の主キーを取得して選択オプションのテーブルに登録する.
								try (ResultSet resSet2 = preState2.getGeneratedKeys()) {
									if (resSet2.next()) {
										preState3.setInt(1, resSet2.getInt(1));

										for (SelectedOption option : item.getSelectedOptionList()) {
											preState2.setInt(2, option.selectionId());

											// 複数行の挿入をするためバッチ処理に入れる.
											preState3.addBatch();
										}
									}
								}
							}
						}
					}
				}

				// バッチ処理を実行.
				// 選択オプションのテーブルに登録する.
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

	public void updateOrder(Order order) {
	}

	public void delOrder(Order order) {
	}

	public ArrayList<Order> searchOederByRoom(int roomId) throws Exception {

		// 返却値の参照変数を初期化.
		ArrayList<Order> list = new ArrayList<>();

		// SQL文の作成.
		String sql = "SELECT order_id,total,orders.room_id,room_number,receiving_number,"
				+ "orders.item_creating_status_id,item_creating_status_name "
				+ "FROM orders "
				+ "INNER JOIN item_creating_status "
				+ "ON orders.item_creating_status_id = item_creating_status.item_creating_status_id "
				+ "INNER JOIN room "
				+ "ON orders.room_id = room.room_id "
				+ "WHERE orders.room_id=?;";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {

			// プリペアードステートメントを使用.
			preState.setInt(1, roomId);
			try (ResultSet resSet = preState.executeQuery();) {

				// 検索結果からOrderインスタンスを生成.
				while (resSet.next()) {
					list.add(new Order(resSet.getInt("order_id"), searchOrderItem(resSet.getInt("order_id")),
							resSet.getInt("total"), resSet.getInt("orders.room_id"), resSet.getInt("room"),
							resSet.getInt("receiving_number"), resSet.getInt("orders.item_creating_status_id"),
							resSet.getString("orders.item_creating_status_name")));
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

	public ArrayList<Order> searchOederByStatus(int statusId) throws Exception {
		// 返却値の参照変数を初期化.
		ArrayList<Order> list = new ArrayList<>();

		// SQL文の作成.
		String sql = "SELECT order_id,total,orders.room_id,room_number,receiving_number,"
				+ "orders.item_creating_status_id,item_creating_status_name "
				+ "FROM orders "
				+ "INNER JOIN item_creating_status "
				+ "ON orders.item_creating_status_id = item_creating_status.item_creating_status_id "
				+ "INNER JOIN room "
				+ "ON orders.room_id = room.room_id "
				+ "WHERE orders.item_creating_status_id=?;";

		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {

			// プリペアードステートメントを使用.
			preState.setInt(1, statusId);
			try (ResultSet resSet = preState.executeQuery();) {

				// 検索結果からOrderインスタンスを生成.
				while (resSet.next()) {
					list.add(new Order(resSet.getInt("order_id"), searchOrderItem(resSet.getInt("order_id")),
							resSet.getInt("total"), resSet.getInt("orders.room_id"), resSet.getInt("room"),
							resSet.getInt("receiving_number"), resSet.getInt("orders.item_creating_status_id"),
							resSet.getString("orders.item_creating_status_name")));
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

	public ArrayList<OrderItem> searchOrderItem(int order) throws Exception {

		// 返却値の参照変数を初期化.
		ArrayList<OrderItem> resList = new ArrayList<>();

		// SQL文の作成.
		String sql1 = "SELECT item_id,`count`,sub_total,order_detail_id "
				+ "FROM order_detail "
				+ "WHERE order_id=?;";
		String sql2 = "SELECT order_detail_id,option_detail_id "
				+ "FROM order_detail_option "
				+ "WHERE order_detail_id=?;";
		try (Connection con = DatabaseManager.connect();
				PreparedStatement preState1 = con.prepareStatement(sql1);
				PreparedStatement preState2 = con.prepareStatement(sql2);) {

			// プリペアードステートメントを使用.
			// 注文単位でアイテムの情報を取得.
			preState1.setInt(1, order);
			try (ResultSet resSet1 = preState1.executeQuery();) {

				while (resSet1.next()) {
					// オプション選択を記録するためのリスト.
					ArrayList<SelectedOption> selOptList = new ArrayList<>();

					// プリペアードステートメントを使用.
					// 商品単位でオプションの情報を取得.
					preState2.setInt(1, resSet1.getInt("order_detail_id"));
					try (ResultSet resSet2 = preState1.executeQuery();) {

						// 結果をリストに格納.
						while (resSet2.next()) {
							selOptList.add(new SelectedOption(resSet2.getInt("order_detail_id"),
									resSet2.getInt("option_detail_id")));
						}
						ItemDao itemDao = new ItemDao();

						// 検索結果からOrderItemインスタンスを生成.
						resList.add(new OrderItem(resSet1.getInt("order_id"),
								itemDao.searchItemById(resSet1.getInt("item_id")), resSet1.getInt("count"),
								selOptList, resSet1.getInt("sub_total")));

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

		return resList;
	}
}
