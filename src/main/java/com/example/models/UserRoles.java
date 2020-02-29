package com.example.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UserRoles {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int UserRoleID ;

	@ManyToOne
	private User use = null;
	
	@ManyToOne
	private Roles role =null;

	public int getUserRoleID() {
		return UserRoleID;
	}

	public void setUserRoleID(int userRoleID) {
		UserRoleID = userRoleID;
	}

	public User getUse() {
		return use;
	}

	public void setUse(User use) {
		this.use = use;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}



	
	
	
}
