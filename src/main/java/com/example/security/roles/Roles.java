package com.example.security.roles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.aspect.EncryptDecrypt.IntEncryptDecryptConverter;
import com.example.aspect.EncryptDecrypt.StringEncryptDecryptConverter;

@Entity
public class Roles {
	//attributes 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Convert(converter = IntEncryptDecryptConverter.class)
	private int id ;
	
	@Convert(converter = StringEncryptDecryptConverter.class)
    private String RoleName=" ";
	
	@Convert(converter = StringEncryptDecryptConverter.class)
	private String AssignedPermissions="none";
	//
	
	public Roles(String roleName, String assignedPermissions) {
		super();
		RoleName = roleName;
		AssignedPermissions = assignedPermissions;
		this.AssignedPermissions = "none";
	}
	
	public Roles() {}

	//setters and getters 
	public String getAssignedPermissions() {
		return this.AssignedPermissions ; 
	}
	public List<String> getAssignedPermissionsList(){
		String[] permissions = this.AssignedPermissions.split(",");
		List<String> assignedPermissionsList = Arrays.asList(permissions);
		return assignedPermissionsList; 
	}
	
	
	public int getId() {
		return id;
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
		if(this.AssignedPermissions.equalsIgnoreCase("") || this.AssignedPermissions.equalsIgnoreCase(" ")) {
			this.AssignedPermissions = permission ; 
		}else if(this.convertPermissionsToList().contains(permission)) {
			return ; 
		}else 
		AssignedPermissions += ","+permission;
	}
	
	public void revokePermissionFromRole(String permission) {
		List<String> rolePermissions = this.convertPermissionsToList();
		int indexOFPermission = rolePermissions.indexOf(permission) ; 
		List<String> newPermissionsList = new ArrayList<String>();  
		if(indexOFPermission != -1 ) {
		this.AssignedPermissions="" ; 
		for( int i = 0 ; i < rolePermissions.size() ; i++) {
			if(i == indexOFPermission) {
				continue ; 
			}else {
				newPermissionsList.add(rolePermissions.get(i));
			}
		}
		this.assignPermissionsList(rolePermissions);
		}
	}
	
	public List<String> convertPermissionsToList(){
		if(this.AssignedPermissions.equalsIgnoreCase("") || this.AssignedPermissions.equalsIgnoreCase(" ")) {
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
