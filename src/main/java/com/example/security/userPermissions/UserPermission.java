package com.example.security.userPermissions;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.example.security.permissions.Permissions;
import com.example.security.user.User;

@Entity
public class UserPermission {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int UserPermissionID ;

	@ManyToOne
	private User user = null;
	
	@ManyToOne
	private Permissions permission =null;

	
	public UserPermission(User user , Permissions permission ) {
			this.user = user ; 
			this.permission = permission ; 
	}
	
	public int getUserPermissionID() {
		return UserPermissionID;
	}

	public void setUserPermissionID(int userPermissionID) {
		UserPermissionID = userPermissionID;
	}

	public User getUser() {
		return user;
	}

	public void setUse(User use) {
		this.user = use;
	}

	public Permissions getPermission() {
		return permission;
	}

	public void setPermission(Permissions permission) {
		this.permission = permission;
	}
	
	
}
