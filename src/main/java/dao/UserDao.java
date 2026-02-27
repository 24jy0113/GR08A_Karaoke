package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.User;
import util.PasswordUtil;

public class UserDao {
	public static User login(String userId, String plainPassword) throws Exception {

		String sql = "SELECT u.user_id, u.user_name, u.password, u.last_login_time, r.role_name " +
				"FROM user u " +
				"JOIN user_role ur ON u.user_id = ur.user_id " +
				"JOIN role r ON ur.role_id = r.role_id " +
				"WHERE u.user_id = ?";

		try (Connection conn = DatabaseManager.connect();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, userId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				String dbHash = rs.getString("password");
				String inputHash = PasswordUtil.hash(plainPassword);

				if (!dbHash.equals(inputHash)) {
					return null;
				}

				User user = new User();
				user.setUserId(rs.getString("user_id"));
				user.setUserName(rs.getString("user_name"));
				user.setRoleName(rs.getString("role_name"));
				user.setPasswordHash(dbHash);
				user.setLastLoginTime(rs.getTimestamp("last_login_time"));

				return user;
			}
			return null;

		} catch (Exception e) {
			throw new Exception("DBアクセスエラー", e);
		}
	}

	public static void insertUser(String userId, String userName, String rawPassword) {

		String sql = "INSERT INTO user(user_id, password, user_name) VALUES (?, ?, ?)";

		String hash = PasswordUtil.hash(rawPassword);

		try (Connection con = DatabaseManager.connect();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, userId);
			ps.setString(2, hash);
			ps.setString(3, userName);
			ps.executeUpdate();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean checkPassword(String userId, String rawPassword) throws Exception {

		String sql = "SELECT password FROM user WHERE user_id = ?";

		try (Connection conn = DatabaseManager.connect();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, userId);

			try (ResultSet rs = ps.executeQuery()) {

				if (!rs.next()) {
					// ユーザー存在しない
					return false;
				}

				String storedHash = rs.getString("password");
				String inputHash = PasswordUtil.hash(rawPassword);

				// パスワードの比較
				return storedHash.equals(inputHash);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static String generateNextUserId() {

		String sql = "SELECT MAX(user_id) FROM user";

		try (Connection con = DatabaseManager.connect();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				String maxId = rs.getString(1);
				int next = Integer.parseInt(maxId) + 1;
				return String.format("%06d", next);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return "000001";
	}

	public static void insertUserRole(String userId, int roleId) {

		String sql = "INSERT INTO user_role(user_id, role_id) VALUES (?, ?)";

		try (Connection con = DatabaseManager.connect();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, userId);
			ps.setInt(2, roleId);
			ps.executeUpdate();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void updateLastLoginTime(String userId) {

		String sql = "UPDATE user SET last_login_time = NOW() WHERE user_id = ?";

		try (Connection con = DatabaseManager.connect();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, userId);
			ps.executeUpdate();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static User searchUserByUserId(String userId) {
		String sql = "SELECT\n"
				+ "    u.user_id,\n"
				+ "    u.user_name,\n"
				+ "    r.role_name,\n"
				+ "    u.last_login_time\n"
				+ "FROM USER u\n"
				+ "LEFT JOIN USER_ROLE ur ON u.user_id = ur.user_id\n"
				+ "LEFT JOIN ROLE r ON ur.role_id = r.role_id\n"
				+ "WHERE u.user_id = ?\n";
		try (Connection con = DatabaseManager.connect();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				User u = new User();
				u.setUserId(rs.getString("user_id"));
				u.setUserName(rs.getString("user_name"));
				u.setRoleName(rs.getString("role_name"));
				u.setLastLoginTime(rs.getTimestamp("last_login_time"));
				return u;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<User> searchUserByUserName(String userName) {
		ArrayList<User> list = new ArrayList<>();

		String sql = "SELECT\n"
				+ "    u.user_id,\n"
				+ "    u.user_name,\n"
				+ "    r.role_name,\n"
				+ "    u.last_login_time\n"
				+ "FROM USER u\n"
				+ "LEFT JOIN USER_ROLE ur ON u.user_id = ur.user_id\n"
				+ "LEFT JOIN ROLE r ON ur.role_id = r.role_id\n"
				+ "WHERE user_name LIKE ?";
		try (Connection con = DatabaseManager.connect();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, "%" + userName + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				User u = new User();
				u.setUserId(rs.getString("user_id"));
				u.setUserName(rs.getString("user_name"));
				u.setRoleName(rs.getString("role_name"));
				u.setLastLoginTime(rs.getTimestamp("last_login_time"));
				list.add(u);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static boolean updateUser(User u) {

		Connection conn = null;

		try {
			conn = DatabaseManager.connect();
			conn.setAutoCommit(false);

			if (u.getUserName() != null && !u.getUserName().isEmpty()) {
				try (PreparedStatement ps = conn.prepareStatement(
						"UPDATE user SET user_name = ? WHERE user_id = ?")) {
					ps.setString(1, u.getUserName());
					ps.setString(2, u.getUserId());
					ps.executeUpdate();
				}
			}

			if (u.getPasswordHash() != null && !u.getPasswordHash().isEmpty()) {
				try (PreparedStatement ps = conn.prepareStatement(
						"UPDATE user SET password = ? WHERE user_id = ?")) {
					ps.setString(1, u.getPasswordHash());
					ps.setString(2, u.getUserId());
					ps.executeUpdate();
				}
			}
			if (u.getRoleId() != null) {
				try (PreparedStatement ps = conn.prepareStatement(
						"UPDATE user_role SET role_id = ? WHERE user_id = ?")) {
					ps.setInt(1, u.getRoleId());
					ps.setString(2, u.getUserId());

					int count = ps.executeUpdate();

					if (count == 0) {
						try (PreparedStatement ps2 = conn.prepareStatement(
								"INSERT INTO user_role (user_id, role_id) VALUES (?, ?)")) {
							ps2.setString(1, u.getUserId());
							ps2.setInt(2, u.getRoleId());
							ps2.executeUpdate();
						}
					}
				}
			}

			conn.commit();
			return true;

		} catch (Exception e) {
			try {
				if (conn != null)
					conn.rollback();
			} catch (Exception ignore) {
			}
			e.printStackTrace();
			return false;

		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception ignore) {
			}
		}
	}

	public static boolean deleteUser(String userId) {

		String deleteUserRoleSql = "DELETE FROM user_role WHERE user_id = ?";
		String deleteUserSql = "DELETE FROM user WHERE user_id = ?";

		try (Connection con = DatabaseManager.connect()) {

			con.setAutoCommit(false);

			try (PreparedStatement ps1 = con.prepareStatement(deleteUserRoleSql);
					PreparedStatement ps2 = con.prepareStatement(deleteUserSql)) {

				ps1.setString(1, userId);
				ps1.executeUpdate();

				ps2.setString(1, userId);
				int result = ps2.executeUpdate();

				con.commit();
				return result == 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static int findRoleIdByRoleName(String roleName) {

		String sql = "SELECT role_id FROM role WHERE role_name = ?";

		try (Connection con = DatabaseManager.connect();
				PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setString(1, roleName);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("role_id");
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		throw new IllegalArgumentException("存在しない role_name: " + roleName);
	}
}