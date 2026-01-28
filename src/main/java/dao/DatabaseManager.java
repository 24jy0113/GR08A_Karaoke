package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
	private static String url = "jdbc:mysql://10.64.144.5:3306/24jy0125";
	private static String user = "24jy0125";
	private static String pass = "24jy0125";

	static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
	
	private DatabaseManager() {
	}

	protected static Connection connect() throws Exception {
		return DriverManager.getConnection(url, user, pass);
	}

	protected static void close(Connection con) throws Exception {
		try {
			// 受け取った接続がnullではない場合.
			if (con != null) {
				// データベースとの接続を切断する.
				con.close();
			}
		} catch (SQLException e) {

			// デバッグ用のスタックトレース.
			e.printStackTrace();

			// フロントエンド用のエラーメッセージ.
			String errMsg = "DB接続に失敗しました！<br>管理者に連絡してください。";

			// 例外を投げる
			throw new Exception(errMsg);

		}
	}
}
