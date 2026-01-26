package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public class PermissionDAO {

    public static Set<String> getPermissionsByUserId(String userId) {
        Set<String> permissions = new HashSet<>();

        String sql =
            "SELECT DISTINCT p.permission_code " +
            "FROM user_role ur " +
            "JOIN role_permission rp ON ur.role_id = rp.role_id " +
            "JOIN permission p ON rp.permission_id = p.permission_id " +
            "WHERE ur.user_id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                permissions.add(rs.getString("permission_code"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return permissions;
    }
}

