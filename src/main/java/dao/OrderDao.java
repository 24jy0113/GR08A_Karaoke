package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
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
            
            if ("カウンター受取".equals(order.getPickupMethod())) {
                int receivingNo = generateReceivingNo(con);
                order.setReceivingNo(receivingNo);
            } else {
                // 部屋まで届ける
                order.setReceivingNo(null);
            }
            /* ① orders*/
            String sqlOrder =
                "INSERT INTO orders " +
                "(total, receiving_number,item_creating_status_id,room_id,pickup_method) " +
                "VALUES (?, ?, ?, ?,?)";

            psOrder = con.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
            psOrder.setInt(1, order.getTotal());
            if (order.getReceivingNo() != null) {
                psOrder.setInt(2, order.getReceivingNo());
            } else {
                psOrder.setNull(2, Types.INTEGER);
            }
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
	private int generateReceivingNo(Connection con) throws Exception {

        String selectSql =
            "SELECT current_no FROM receiving_no WHERE id = 1 FOR UPDATE";
        String updateSql =
            "UPDATE receiving_no SET current_no = ? WHERE id = 1";

        int nextNo;

        try (
            PreparedStatement ps = con.prepareStatement(selectSql);
            ResultSet rs = ps.executeQuery()
        ) {
            rs.next();
            nextNo = rs.getInt("current_no") + 1;
            if (nextNo > 9999) {
                nextNo = 1;
            }
        }

        try (PreparedStatement ps = con.prepareStatement(updateSql)) {
            ps.setInt(1, nextNo);
            ps.executeUpdate();
        }

        return nextNo;
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

	
	/**
     * order_detail の count と sub_total を一括更新する.
     */
    public void updateOrderDetail(List<OrderItem> detailList) throws Exception {

        String sql = "UPDATE order_detail SET count = ?, sub_total = ? WHERE order_detail_id = ?";

        try (Connection con = DatabaseManager.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            con.setAutoCommit(false);

            for (OrderItem oi : detailList) {
                ps.setInt(1, oi.getCount());
                ps.setInt(2, oi.getTotal());
                ps.setInt(3, oi.getId());
                ps.addBatch();
            }

            ps.executeBatch();
            con.commit();

        }
    }

    /**
     * orders の total と item_creating_status_id を更新する.
     */
    public void updateOrderTotalAndStatus(int orderId, int newTotal, int newStatusId) throws Exception {

        String sql = "UPDATE orders SET total = ?, item_creating_status_id = ? WHERE order_id = ?";

        try (Connection con = DatabaseManager.connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, newTotal);
            ps.setInt(2, newStatusId);
            ps.setInt(3, orderId);
            ps.executeUpdate();

        }
    }
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
				    order.setRoomNo(resSet.getInt("room_number"));
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

	
public List<Order> findByStatus(int statusId) throws Exception {

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
        WHERE o.item_creating_status_id = ?
        ORDER BY o.order_id ASC
    """;

    try (
        Connection con = DatabaseManager.connect();
        PreparedStatement ps = con.prepareStatement(sql)
    ) {
        ps.setInt(1, statusId);

        try (ResultSet rs = ps.executeQuery()) {
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
    }
    return list;
}


	// 注文済み
	public List<Order> findOrderedList() throws Exception {
	    return findByStatus(1);
	}

	// 調理済み
	public List<Order> findCookingFinishedList() throws Exception {
	    return findByStatus(2);
	}

	// 完了
	public List<Order> findCompletedList() throws Exception {
	    return findByStatus(3);
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
