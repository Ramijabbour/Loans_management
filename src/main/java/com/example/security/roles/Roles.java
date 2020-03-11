package com.example.security.roles;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Roles {

	//attributes 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int RoleID ;
    private String RoleName="";
	private String AssignedPermissions="";
	//
	
	//setters and getters 
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
		return AssignedPermissions;
	}
	//
	
	//permissions assignment handling 
	public void addSinglePermissionToRole(String permission) {
		if(this.AssignedPermissions.equalsIgnoreCase("")) {
			this.AssignedPermissions = permission ; 
		}else if(this.convertPermissionsToList().contains(permission)) {
			return ; 
		}else 
		AssignedPermissions += ","+permission;
	}
	
	public void revokePermissionFromRole(String permission) {
		List<String> rolePermissions = this.convertPermissionsToList();
		if(rolePermissions.contains(permission)) {
			rolePermissions.remove(permission);
		}
		this.AssignedPermissions="" ; 
		this.assignPermissionsList(rolePermissions);
	}
	
	public List<String> convertPermissionsToList(){
		if(this.AssignedPermissions.equalsIgnoreCase("")) {
			return null ; 
		}
		String[] permissionsArray = this.AssignedPermissions.split(",");
		List<String> permissionsList = Arrays.asList(permissionsArray);
		return permissionsList ; 
	}
	
	public void assignPermissionsList(List<String> permissionsList) {
		for(String permission : permissionsList) {
			this.addSinglePermissionToRole(permission);
		}
	}
	//
	
}
