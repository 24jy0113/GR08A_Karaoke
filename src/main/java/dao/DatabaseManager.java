package dao;

import java.sql.Connection;
import java.sql.DriverManager;

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
}
