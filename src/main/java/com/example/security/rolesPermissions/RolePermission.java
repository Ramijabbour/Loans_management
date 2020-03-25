package com.example.security.rolesPermissions;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.example.security.permissions.Permissions;
import com.example.security.roles.Roles;

@Entity
public class RolePermission {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int RolePermissionID ;

	@ManyToOne
	private Roles role = null;
	
	@ManyToOne
	private Permissions permission =null;

	
	public RolePermission() {}
	
	public RolePermission(Permissions permission , Roles role ) {
		this.permission = permission ; 
		this.role = role ; 
	}
	
	public int getRolePermissionID() {
		return RolePermissionID;
	}

	public void setRolePermissionID(int rolePermissionID) {
		RolePermissionID = rolePermissionID;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public Permissions getPermission() {
		return permission;
	}

	public void setPermission(Permissions permission) {
		this.permission = permission;
	}

}
