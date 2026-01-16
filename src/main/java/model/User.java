package model;

import java.sql.Timestamp;

public class User {
	private String userId;
	private String userName;
	private String roleName;
	private String permissions;
	private String passwordHash;
	private Timestamp lastLoginTime;
	
	public User() {
	}

	public User(String userId, String userName, String roleName, String permissions, String passwordHash,
			Timestamp lastLoginTime) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.roleName = roleName;
		this.permissions = permissions;
		this.passwordHash = passwordHash;
		this.lastLoginTime = lastLoginTime;
	}

	public boolean isFront() {
        return "フロント".equals(roleName);
    }
	public boolean isKitchen() {
        return "キッチン".equals(roleName);
    }
    public boolean isFloor() {
        return "フロア".equals(roleName);
    }

    public boolean isManager() {
        return "管理者".equals(roleName);
    }
    public boolean hasPermission(String perm) {
        return permissions != null && permissions.contains(perm);
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

}
