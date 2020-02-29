package com.example.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Roles {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int RoleID ;

    private String RoleName="";

	
	private String RoleType="";


	public int getRoleID() {
		return RoleID;
	}


	public void setRoleID(int roleID) {
		RoleID = roleID;
	}


	public String getRoleName() {
		return RoleName;
	}


	public void setRoleName(String roleName) {
		RoleName = roleName;
	}


	public String getRoleType() {
		return RoleType;
	}


	public void setRoleType(String roleType) {
		RoleType = roleType;
	}


	
	
	
}
