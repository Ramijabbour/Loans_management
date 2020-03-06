package com.example.security.permissions;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Permissions {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int PermissionID ;

    private String PermissionName="";

	
	private String PermissionType="";


	public int getPermissionID() {
		return PermissionID;
	}


	public void setPermissionID(int permissionID) {
		PermissionID = permissionID;
	}


	public String getPermissionName() {
		return PermissionName;
	}


	public void setPermissionName(String permissionName) {
		PermissionName = permissionName;
	}


	public String getPermissionType() {
		return PermissionType;
	}


	public void setPermissionType(String permissionType) {
		PermissionType = permissionType;
	}


	
	
	
}
