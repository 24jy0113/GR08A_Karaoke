package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Order;
import model.OrderItem;
import model.OrderItem.SelectedOption;
import model.OrderStatus;

public class OrderDao {
	public int insertOrder(Order order) throws Exception {

        Connection con = null;
        PreparedStatement psOrder = null;
        PreparedStatement psDetail = null;
        PreparedStatement psOption = null;

        try {
            con = DatabaseManager.connect();
            con.setAutoCommit(false);

            /* ① orders*/
            String sqlOrder =
                "INSERT INTO orders " +
                "(total, receiving_number,item_creating_status_id,room_id,pickup_method) " +
                "VALUES (?, ?, ?, ?,?)";

            psOrder = con.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
            psOrder.setInt(1, order.getTotal());
            psOrder.setInt(2, order.getReceivingNo());
            psOrder.setInt(3, order.getItemCreatingStatusId());
            psOrder.setInt(4, order.getRoomId());
            psOrder.setString(5, order.getPickupMethod());

            psOrder.executeUpdate();

            ResultSet rsOrder = psOrder.getGeneratedKeys();
            if (!rsOrder.next()) {
                throw new SQLException("order_id の取得に失敗しました");
            }
            int orderId = rsOrder.getInt(1);

            /* =========================
             * ② order_detail
             * ========================= */
            String sqlDetail =
                "INSERT INTO order_detail " +
                "(order_id, item_id,item_name,\n"
                + "  item_price,count,sub_total) " +
                "VALUES (?, ?, ?,?,?, ?)";

            psDetail = con.prepareStatement(sqlDetail, Statement.RETURN_GENERATED_KEYS);

            /* =========================
             * ③ order_detail_option
             * ========================= */
            String sqlOption =
                "INSERT INTO order_detail_option " +
                "(order_detail_id, option_detail_id) " +
                "VALUES (?, ?)";

            psOption = con.prepareStatement(sqlOption);

            for (OrderItem oi : order.getItemList()) {

                // --- order_detail insert
                psDetail.setInt(1, orderId);
                psDetail.setInt(2, oi.getItemId());
                psDetail.setString(3, oi.getItemName());
                psDetail.setInt(4, oi.getItemPrice());
                psDetail.setInt(5, oi.getCount());
                psDetail.setInt(6, oi.getTotal());
                psDetail.executeUpdate();

                ResultSet rsDetail = psDetail.getGeneratedKeys();
                if (!rsDetail.next()) {
                    throw new SQLException("order_detail_id の取得に失敗しました");
                }
                int orderDetailId = rsDetail.getInt(1);

                //option insert
                List<SelectedOption> options = oi.getSelectedOptions();
                if (options != null) {
                    for (SelectedOption opt : options) {
                        psOption.setInt(1, orderDetailId);
                        psOption.setInt(2, opt.selectionId());
                        psOption.executeUpdate();
                    }
                }
            }

            con.commit(); // ★ 全成功
            return orderId;

        } catch (Exception e) {
            if (con != null) con.rollback(); // ★ 途中失敗は全取消
            throw e;

        } finally {
            if (psOption != null) psOption.close();
            if (psDetail != null) psDetail.close();
            if (psOrder != null) psOrder.close();
            if (con != null) con.close();
        }
    }
	public List<Order> findActiveOrdersByRoom(int roomId) throws Exception {
	    List<Order> list = new ArrayList<>();//顧客側注文履歴取得

	    String sql =
	        "SELECT order_id, total, receiving_number, pickup_method " +
	        "FROM orders " +
	        "WHERE room_id = ? " +
	        "AND usage_history_id IS NULL " +
	        "ORDER BY receiving_number, order_id";

	    try (Connection con = DatabaseManager.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, roomId);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Order o = new Order();
	            o.setId(rs.getInt("order_id"));
	            o.setTotal(rs.getInt("total"));
	            o.setReceivingNo(rs.getInt("receiving_number"));
	            o.setPickupMethod(rs.getString("pickup_method"));
	            list.add(o);
	        }
	    }
	    return list;
	}
	public int getActiveOrderTotalByRoom(int roomId) throws Exception {

	    String sql =
	        "SELECT COALESCE(SUM(total), 0) AS sum_total " +
	        "FROM orders " +
	        "WHERE room_id = ? " +
	        "AND usage_history_id IS NULL";

	    try (Connection con = DatabaseManager.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, roomId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            return rs.getInt("sum_total");
	        }
	    }
	    return 0;
	}

	/*
	public void addOrder(Order order) throws Exception {

		if (order.hasOptionUnselected()) {
			System.err.println("未選択のオプションがある注文を登録しようとしました。");

			// フロントエンド用のエラーメッセージ.
			String errMsg = "未選択のオプションがあるため注文の登録に失敗しました。<br>管理者に連絡してください。";

			// 例外を投げる.	
			throw new Exception(errMsg);
		}

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
			preState1.setInt(3, order.getStatusId().getId());
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
							preState2.setInt(3, item.getCount());
							preState2.setInt(4, item.getTotal());

							// 商品をテーブルに追加.
							preState2.executeUpdate();

							// 選択したオプションがある場合のみその情報を登録.
							if (!item.getSelectedOptionList().isEmpty()) {

								// 生成された注文詳細の主キーを取得して選択オプションのテーブルに登録する.
								try (ResultSet resSet2 = preState2.getGeneratedKeys()) {
									if (resSet2.next()) {
										int orderDetailId = resSet2.getInt(1);

										for (SelectedOption option : item.getSelectedOptionList()) {
											preState3.setInt(1, orderDetailId);
											preState3.setInt(2, option.selectionId());

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
*/
	public void updateStatus(int orderId, int statusId) throws Exception {

		// SQL文の作成.
		// 注文の更新.
		String sql = "UPDATE orders "
				+ "SET item_creating_status_id=? "
				+ "WHERE order_id=?;";

		try (Connection con = DatabaseManager.connect();
				PreparedStatement preState = con.prepareStatement(sql);) {

			// プリペアードステートメントを使用.
			preState.setInt(1, statusId);
			preState.setInt(2, orderId);

			// 更新を実行.
			preState.executeUpdate();

		} catch (SQLException e) {

			// デバッグ用のスタックトレース.
			e.printStackTrace();

			// フロントエンド用のエラーメッセージ.
			String errMsg = "DB接続に失敗しました！<br>管理者に連絡してください。";

			// 例外を投げる.
			throw new Exception(errMsg);

		}
	}

	//	public void delOrder(Order order) throws Exception {
	//		// SQL文の作成.
	//		// 注文詳細と選択された商品オプションのつながりの削除.
	//		String sql1 = "";
	//		// 注文詳細（OrderItemごと）の削除.
	//		String sql2 = "";
	//		// 注文の登録.
	//		String sql3 = "";
	//
	//		try (Connection con = DatabaseManager.connect();
	//				PreparedStatement preState1 = con.prepareStatement(sql1);
	//				PreparedStatement preState2 = con.prepareStatement(sql2);
	//				PreparedStatement preState3 = con.prepareStatement(sql3)) {
	//
	//			// 複数テーブルを更新する必要があるので自動コミットを無効.
	//			con.setAutoCommit(false);
	//
	//			// プリペアードステートメントを使用.
	//			
	//			
	//			try {
	//
	//				// 注文をテーブルに登録.
	//				preState1.executeUpdate();
	//
	//				// 生成された注文の主キーを取得して注文詳細を登録する.
	//				try (ResultSet resSet1 = preState1.getGeneratedKeys();) {
	//					if (resSet1.next()) {
	//						preState2.setInt(1, resSet1.getInt(1));
	//
	//						for (OrderItem item : order.getItemList()) {
	//							preState2.setInt(2, item.getItem().getId());
	//							preState2.setInt(3, item.getTotal());
	//							preState2.setInt(4, item.getTotal());
	//
	//							// 商品をテーブルに追加.
	//							preState2.executeUpdate();
	//
	//							// 選択したオプションがある場合のみその情報を登録.
	//							if (item.getSelectedOptionList().isEmpty()) {
	//
	//								// 生成された注文詳細の主キーを取得して選択オプションのテーブルに登録する.
	//								try (ResultSet resSet2 = preState2.getGeneratedKeys()) {
	//									if (resSet2.next()) {
	//										preState3.setInt(1, resSet2.getInt(1));
	//
	//										for (SelectedOption option : item.getSelectedOptionList()) {
	//											preState2.setInt(2, option.selectionId());
	//
	//											// 複数行の挿入をするためバッチ処理に入れる.
	//											preState3.addBatch();
	//										}
	//									}
	//								}
	//							}
	//						}
	//					}
	//				}
	//
	//				// バッチ処理を実行.
	//				// 選択オプションのテーブルに登録する.
	//				preState3.executeBatch();
	//
	//				// すべて成功したらコミット.
	//				con.commit();
	//
	//			} catch (SQLException e) {
	//
	//				// 挿入時に例外が出たらロールバックする.
	//				con.rollback();
	//
	//				// 例外を投げる.
	//				throw e;
	//
	//			}
	//		} catch (SQLException e) {
	//
	//			// デバッグ用のスタックトレース.
	//			e.printStackTrace();
	//
	//			// フロントエンド用のエラーメッセージ.
	//			String errMsg = "DB接続に失敗しました！<br>管理者に連絡してください。";
	//
	//			// 例外を投げる.
	//			throw new Exception(errMsg);
	//
	//		}
	//	}

		// 注文IDに対応する注文をDBから取得してOrderクラスとして返す。見つからないとnullが出るのでnullチェックをすること.
	//	private Order searchOrderById(int orderId) throws Exception {
	//
	//		// 返却値の参照変数を初期化.
	//		Order resOrder = null;
	//
	//		// SQL文の作成.
	//		String sql = "SELECT order_id,total,orders.room_id,room_number,receiving_number,"
	//				+ "orders.item_creating_status_id,item_creating_status_name "
	//				+ "FROM orders "
	//				+ "INNER JOIN item_creating_status "
	//				+ "ON orders.item_creating_status_id = item_creating_status.item_creating_status_id "
	//				+ "INNER JOIN room "
	//				+ "ON orders.room_id = room.room_id "
	//				+ "WHERE orders.order_id=?;";
	//
	//		try (Connection con = DatabaseManager.connect(); PreparedStatement preState = con.prepareStatement(sql);) {
	//
	//			// プリペアードステートメントを使用.
	//			preState.setInt(1, orderId);
	//			try (ResultSet resSet = preState.executeQuery();) {
	//
	//				// 検索結果からOrderインスタンスを生成.
	//				while (resSet.next()) {
	//					resOrder = new Order(orderId, searchOrderItem(orderId), resSet.getInt("total"),
	//							resSet.getInt("orders.room_id"), resSet.getInt("room"), resSet.getInt("receiving_number"),
	//							resSet.getInt("orders.item_creating_status_id"),
	//							resSet.getString("orders.item_creating_status_name"));
	//				}
	//			}
	//		} catch (SQLException e) {
	//
	//			// デバッグ用のスタックトレース.
	//			e.printStackTrace();
	//
	//			// フロントエンド用のエラーメッセージ.
	//			String errMsg = "DB接続に失敗しました！<br>管理者に連絡してください。";
	//
	//			// 例外を投げる.
	//			throw new Exception(errMsg);
	//
	//		}
	//		return resOrder;
	//
	//	}
	public List<OrderItem> findOrderItemsByOrderId(int orderId)//注文履歴とキッチン用.
	        throws Exception {

	    List<OrderItem> list = new ArrayList<>();

	    String sql = """
	        SELECT
	          order_detail_id,
	          item_id,
	          item_name,
	    	  item_price,
	          count,
	          sub_total
	        FROM order_detail
	        WHERE order_id = ?
	    """;

	    try (Connection con = DatabaseManager.connect();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, orderId);

	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {

	            OrderItem oi = new OrderItem(
	                rs.getInt("order_detail_id"),
	                null, 
	                rs.getInt("count"),
	                new ArrayList<>(),
	                rs.getInt("sub_total")
	            );

	            oi.setItemId(rs.getInt("item_id"));
	            oi.setItemName(rs.getString("item_name"));
	            oi.setItemPrice(rs.getInt("item_price"));
	            oi.setSelectedOptionDetails(
	            		findOptionsByOrderDetailId(rs.getInt("order_detail_id"), con)
	                );
	            list.add(oi);
	        }
	    }
	    return list;
	}

	public List<OrderItem.SelectedOptionDetail> //注文履歴用.
	findOptionsByOrderDetailId(int orderDetailId,Connection con) throws Exception {

	    List<OrderItem.SelectedOptionDetail> list = new ArrayList<>();

	    String sql = """
	        SELECT
	          o.option_id,
	          o.option_name,
	          od.option_detail_id,
	          od.option_detail_name,
	          od.price
	        FROM order_detail_option odo
	        JOIN option_detail od
	          ON odo.option_detail_id = od.option_detail_id
	        JOIN `option` o
	          ON od.option_id = o.option_id
	        WHERE odo.order_detail_id = ?
	    """;

	    try (
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, orderDetailId);

	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            list.add(
	                new OrderItem.SelectedOptionDetail(
	                    rs.getInt("option_id"),
	                    rs.getString("option_name"),
	                    rs.getInt("option_detail_id"),
	                    rs.getString("option_detail_name"),
	                    rs.getInt("price")
	                )
	            );
	        }
	    }
	    return list;
	}

	public ArrayList<Order> searchOrderByRoom(int roomId) throws Exception {

		// 返却値の参照変数を初期化.
		ArrayList<Order> resList = new ArrayList<>();

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

				    int orderId = resSet.getInt("order_id");

				    Order order = new Order();

				    order.setId(orderId);
				    order.setTotal(resSet.getInt("total"));
				    order.setRoomId(resSet.getInt("orders.room_id"));
				    order.setRoomNumber(resSet.getInt("room_number"));
				    order.setReceivingNo(resSet.getInt("receiving_number"));
				    order.setStatus(
				        OrderStatus.fromId(
				            resSet.getInt("orders.item_creating_status_id")
				        )
				    );

				    // 子表查询（OrderItem）
				    order.setItemList(searchOrderItem(orderId));

				    resList.add(order);
				}

			}
		} catch (IllegalArgumentException e) {

			// デバッグ用のスタックトレース.
			e.printStackTrace();

			// フロントエンド用のエラーメッセージ.
			String errMsg = "状態IDが不正です。";

			// 例外を投げる.
			throw new Exception(errMsg);

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

	/*public ArrayList<Order> searchOrderByStatus(OrderStatus status) throws Exception {
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
			preState.setInt(1, status.getId());
			try (ResultSet resSet = preState.executeQuery();) {

				// 検索結果からOrderインスタンスを生成.
				while (resSet.next()) {
					int orderId = resSet.getInt("order_id");
					list.add(new Order(orderId, searchOrderItem(orderId), resSet.getInt("total"),
							resSet.getInt("orders.room_id"), resSet.getInt("room"), resSet.getInt("receiving_number"),
							OrderStatus.fromId(resSet.getInt("orders.item_creating_status_id")),
							resSet.getString("orders.item_creating_status_name")));
				}
			}

		} catch (IllegalArgumentException e) {

			// デバッグ用のスタックトレース.
			e.printStackTrace();

			// フロントエンド用のエラーメッセージ.
			String errMsg = "状態IDが不正です。";

			// 例外を投げる.
			throw new Exception(errMsg);

		} catch (SQLException e) {

			// デバッグ用のスタックトレース.
			e.printStackTrace();

			// フロントエンド用のエラーメッセージ.
			String errMsg = "DB接続に失敗しました！<br>管理者に連絡してください。";

			// 例外を投げる.
			throw new Exception(errMsg);

		}
		return list;

	}*/
	public List<Order> findOrderedList() throws Exception {// 注文済み一覧

	    List<Order> list = new ArrayList<>();

	    String sql = """
	        SELECT
            o.order_id,
            o.total,
            o.receiving_number,
            o.item_creating_status_id,
            o.room_id,
            r.room_number,
            o.usage_history_id,
            o.pickup_method
        FROM orders o
        JOIN room r
          ON o.room_id = r.room_id
        WHERE o.item_creating_status_id = 1
        ORDER BY o.order_id ASC
	    """;

	    try (
	        Connection con = DatabaseManager.connect();
	        PreparedStatement ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	    ) {
	        while (rs.next()) {
	            Order o = new Order();
	            o.setId(rs.getInt("order_id"));
	            o.setTotal(rs.getInt("total"));
	            o.setReceivingNo(rs.getInt("receiving_number"));
	            o.setItemCreatingStatusId(rs.getInt("item_creating_status_id"));
	            o.setRoomId(rs.getInt("room_id"));
	            o.setRoomNo(rs.getInt("room_number"));
	            o.setUsageHistoryId(rs.getInt("usage_history_id"));
	            o.setPickupMethod(rs.getString("pickup_method"));
	            list.add(o);
	        }
	    }
	    return list;
	}

	public List<Order> findCookingFinishedList() throws Exception {//調理済み一覧

	    List<Order> list = new ArrayList<>();

	    String sql = """
	        SELECT *
	        FROM orders
	        WHERE item_creating_status_id = 2
	        ORDER BY order_id ASC
	    """;

	    try (
	        Connection con = DatabaseManager.connect();
	        PreparedStatement ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	    ) {
	        while (rs.next()) {
	            Order o = new Order();
	            o.setId(rs.getInt("order_id"));
	            o.setTotal(rs.getInt("total"));
	            o.setReceivingNo(rs.getInt("receiving_number"));
	            o.setItemCreatingStatusId(rs.getInt("item_creating_status_id"));
	            o.setRoomId(rs.getInt("room_id"));
	            o.setUsageHistoryId(rs.getInt("usage_history_id"));
	            o.setPickupMethod(rs.getString("pickup_method"));

	            list.add(o);
	        }
	    }
	    return list;
	}
	public int getItemCreatingStatus(int orderId) throws Exception {//キッチン調理状態変更用

	    String sql = """
	        SELECT item_creating_status_id
	        FROM orders
	        WHERE order_id = ?
	    """;

	    try (
	        Connection con = DatabaseManager.connect();
	        PreparedStatement ps = con.prepareStatement(sql);
	    ) {
	        ps.setInt(1, orderId);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("item_creating_status_id");
	            }
	        }
	    }
	    return -1;
	}

	public ArrayList<OrderItem> searchOrderItem(int order) throws Exception {

		// 返却値の参照変数を初期化.
		ArrayList<OrderItem> resList = new ArrayList<>();

		// SQL文の作成.
		String sql1 = "SELECT order_id,item_id,`count`,sub_total,order_detail_id "
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
					try (ResultSet resSet2 = preState2.executeQuery();) {

						// 結果をリストに格納.
						while (resSet2.next()) {
							selOptList.add(new SelectedOption(resSet2.getInt("order_detail_id"),
									resSet2.getInt("option_detail_id")));
						}
						var itemDao = new ItemDao();

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
