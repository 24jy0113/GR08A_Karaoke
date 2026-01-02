package model;

import java.sql.Timestamp;

public class User {
	private String id;
	private String userName;
	private String roleName;
	private String permissions;
	private String passwordHash;
	private Timestamp lastLoginTime;
	
	public User() {
	}

	public User(String id, String userName, String roleName, String permissions, String passwordHash,
			Timestamp lastLoginTime) {
		super();
		this.id = id;
		this.userName = userName;
		this.roleName = roleName;
		this.permissions = permissions;
		this.passwordHash = passwordHash;
		this.lastLoginTime = lastLoginTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
