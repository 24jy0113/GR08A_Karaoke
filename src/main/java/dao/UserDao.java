package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Connection;

import model.User;
import util.DBUtil;
import util.PasswordUtil;

public class UserDao {
	public static User login(String userId, String plainPassword) {

	    String sql =
	        "SELECT u.user_id, u.user_name, u.password, u.last_login_time, r.role_name " +
	        "FROM user u " +
	        "JOIN role_detail rd ON u.user_id = rd.user_id " +
	        "JOIN role r ON rd.role_id = r.role_id " +
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
	            user.setPermissions(buildPermissions(user.getRoleName()));

	            return user;
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}


    private static String buildPermissions(String roleName) {
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
}