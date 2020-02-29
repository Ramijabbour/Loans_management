package com.example.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UserPermission {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int UserPermissionID ;

	@ManyToOne
	private User use = null;
	
	@ManyToOne
	private Permissions permission =null;

	public int getUserPermissionID() {
		return UserPermissionID;
	}

	public void setUserPermissionID(int userPermissionID) {
		UserPermissionID = userPermissionID;
	}

	public User getUse() {
		return use;
	}

	public void setUse(User use) {
		this.use = use;
	}

	public Permissions getPermission() {
		return permission;
	}

	public void setPermission(Permissions permission) {
		this.permission = permission;
	}

	


	
	
	
}
