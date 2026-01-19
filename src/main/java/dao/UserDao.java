package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;

import model.User;
import util.DBUtil;
import util.PasswordUtil;

public class UserDao {
	public static User login(String userId, String plainPassword) {

	    String sql =
	        "SELECT u.user_id, u.user_name, u.password, u.last_login_time, r.role_name " +
	        "FROM user u " +
	        "JOIN user_role ur ON u.user_id = ur.user_id " +
	        "JOIN role r ON ur.role_id = r.role_id " +
	        "WHERE u.user_id = ?";

	    try (Connection conn = DBUtil.getConnection();
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
	            //user.setPermissions(buildPermissions(user.getRoleName()));

	            return user;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	public static boolean checkPassword(String userId, String rawPassword) {

	    String sql = "SELECT password FROM user WHERE user_id = ?";

	    try (Connection conn = DBUtil.getConnection();
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

	    try (Connection con = DBUtil.getConnection();
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
	public static void insertUser(String userId, String userName, String rawPassword) {

	    String sql = "INSERT INTO user(user_id, password, user_name) VALUES (?, ?, ?)";

	    String hash = PasswordUtil.hash(rawPassword);

	    try (Connection con = DBUtil.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, userId);
	        ps.setString(2, hash);
	        ps.setString(3, userName);
	        ps.executeUpdate();

	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	public static void insertUserRole(String userId, int roleId) {

	    String sql = "INSERT INTO user_role(user_id, role_id) VALUES (?, ?)";

	    try (Connection con = DBUtil.getConnection();
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

	    try (Connection con = DBUtil.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, userId);
	        ps.executeUpdate();

	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	public static User searchUserByUserId(String userId) {
	    String sql = "SELECT user_id, user_name FROM user WHERE user_id = ?";
	    try (Connection con = DBUtil.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, userId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            User u = new User();
	            u.setUserId(rs.getString("user_id"));
	            u.setUserName(rs.getString("user_name"));
	            return u;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	public static ArrayList<User> searchUserByUserName(String userName) {
	    ArrayList<User> list = new ArrayList<>();

	    String sql = "SELECT user_id, user_name FROM user WHERE user_name LIKE ?";
	    try (Connection con = DBUtil.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, "%" + userName + "%");
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            User u = new User();
	            u.setUserId(rs.getString("user_id"));
	            u.setUserName(rs.getString("user_name"));
	            list.add(u);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	public static boolean updateUser(User user) {
	    String sql = "UPDATE user SET user_name = ? WHERE user_id = ?";

	    try (Connection con = DBUtil.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, user.getUserName());
	        ps.setString(2, user.getUserId());
	        return ps.executeUpdate() == 1;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	public static boolean deleteUser(String userId) {

	    String deleteUserRoleSql = "DELETE FROM user_role WHERE user_id = ?";
	    String deleteUserSql     = "DELETE FROM user WHERE user_id = ?";

	    try (Connection con = DBUtil.getConnection()) {

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


   /* private static String buildPermissions(String roleName) {
        switch (roleName) {
            case "管理者":
                return "all";
            case "フロント":
                return "order,customer";
            case "キッチン":
                return "order";
            default:
                return "";
        }
    }
    */
}