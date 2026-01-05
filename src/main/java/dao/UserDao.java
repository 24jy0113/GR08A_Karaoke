package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

import model.User;

public class UserDao {
	private Connection con = null;

	public UserDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		try {
			con = (Connection) DriverManager.getConnection("jdbc:mysql://10.64.144.5:3306/"
					+ "24jy0125?characterEncoding=UTF-8", "24jy0125", "24jy0125");

		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void connectionClose() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean validate(String username, String passwordHash) {

		String sql = "SELECT 1 FROM users WHERE user_name = ? AND password_hash = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, username);
			ps.setString(2, passwordHash);

			ResultSet rs = ps.executeQuery();
			return rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public User findById(String id) {

		User user = null;

		String sql = "SELECT id, user_name, role_name, permissions, password_hash, last_login_time "
				+ "FROM users WHERE id = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				user = new User(
						rs.getString("id"),
						rs.getString("user_name"),
						rs.getString("role_name"),
						rs.getString("permissions"),
						rs.getString("password_hash"),
						rs.getTimestamp("last_login_time"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public ArrayList<User> findByUserName(String userName) {

		ArrayList<User> list = new ArrayList<>();

		String sql = "SELECT id, user_name, role_name, permissions, password_hash, last_login_time "
				+ "FROM users WHERE user_name LIKE ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, "%" + userName + "%");
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            User user = new User(
	                    rs.getString("id"),
	                    rs.getString("user_name"),
	                    rs.getString("role_name"),
	                    rs.getString("permissions"),
	                    rs.getString("password_hash"),
	                    rs.getTimestamp("last_login_time")
	            );
	            list.add(user);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return list;
	}
}
