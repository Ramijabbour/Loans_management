package com.example.security.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int UserID ;

	@Column(nullable = false )
	private String email=" ";

	@Column(nullable = false )
	private String password = " ";
	
	private String username ;
	private String Gender="" ;
	private String UserPermissions = " " ; 
	private String UserRoles =" "; 
	
	private boolean Active = false ; 

	
	public User() {}
	
	public User(String email, String password, String username, String gender, String userPermissions,
			String userRoles, boolean isActive) {
		super();
		this.email = email;
		this.password = password;
		this.username = username;
		Gender = gender;
		UserPermissions = userPermissions;
		UserRoles = userRoles;
		this.Active = isActive;
	}
	
	
	public void flatUserDetailes() {
		System.out.println("user ID : "+this.UserID+" username :"+this.username+" user email : "+this.email+" gender : "
	+this.Gender+" role "+this.UserRoles +" permissions : "+this.UserPermissions);
	}
	
	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getUserPermissions() {
		return UserPermissions;
	}

	public void setUserPermissions(String userPermissions) {
		UserPermissions = userPermissions;
	}

	public String getUserRoles() {
		return UserRoles;
	}

	public void setUserRoles(String userRoles) {
		UserRoles = userRoles;
	}

	public boolean isActive() {
		return Active;
	}

	public void setActive(boolean active) {
		Active = active;
	}

	public boolean hasRole(String role) {
		if(this.UserRoles.equalsIgnoreCase("")) {
			return false ; 
		}
		else if (!this.convertRolesToList().contains(role)) {
			return false  ; 
		}
		return true ; 
	}
	
	public boolean hasPermission(String permission) {
		if(this.UserPermissions.equalsIgnoreCase("")) {
			return false ; 
		}
		else if (!this.convertPermissionsToList().contains(permission)) {
			return false  ; 
		}
		return true ; 
	}

	public void addRole(String role ) {
		this.UserRoles+=","+role;
	}
	
	public void addPermission(String permission ) {
		this.UserPermissions+=","+permission;
	}

	public List<String> convertPermissionsToList(){
		if(this.UserPermissions.equalsIgnoreCase("")) {
			return null ; 
		}
		else {
			List<String> userPermissions = new ArrayList<String>() ;
			String[] permissions = this.UserPermissions.split(",");
			userPermissions = Arrays.asList(permissions);
			return userPermissions ; 
			
		}
	}
	
	public List<String> convertRolesToList(){
		if(this.UserRoles.equalsIgnoreCase("")) {
			return null ; 
		}
		else {
			List<String> userRoles = new ArrayList<String>() ;
			String[] roles = this.UserRoles.split(",");
			userRoles = Arrays.asList(roles);
			return userRoles ; 
			
		}
	}
	
	public void revokeRoleFromUser(String role ) {
		List<String> userRoles = this.convertRolesToList() ; 
		if(userRoles.contains(role)) {
			userRoles.remove(role);
			this.UserRoles = "";
			for(String tempRole : userRoles) {
				this.addRole(tempRole);
			}
		}
	}
	
	public void revokePermissionFromUser(String permission) {
		List<String> userPermissions = this.convertPermissionsToList(); 
		if(userPermissions.contains(permission)) {
			userPermissions.remove(permission);
			this.UserPermissions = "";
			for(String tempPermission : userPermissions) {
				this.addPermission(tempPermission);
			}
		}
	}
	
}
