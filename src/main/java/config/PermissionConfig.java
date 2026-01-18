package config;

public class PermissionConfig {

    public static String getPermission(String path) {

        if (path.startsWith("/admin/")) {
            return "ADMIN_ALL";
        }

        if (path.startsWith("/front/")) {
            return "VIEW_FRONT";
        }

        if (path.startsWith("/floor/")) {
            return "VIEW_FLOOR";
        }

        if (path.startsWith("/kitchen/")) {
            return "VIEW_KITCHEN";
        }

        return null;
    }
}
