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
	private String Email="";

	@Column(nullable = false )
	private String password = " ";
	
	private String username ;
	private String Gender="" ;
	private String UserPermissions = "" ; 
	private String UserRoles =""; 
	
	
	private boolean isActive = false ; 

	
	public User() {}
	
	public User(String email, String password, String username, String gender, String userPermissions,
			String userRoles, boolean isActive) {
		super();
		Email = email;
		this.password = password;
		this.username = username;
		Gender = gender;
		UserPermissions = userPermissions;
		UserRoles = userRoles;
		this.isActive = isActive;
	}
	
	
	public void flatUserDetailes() {
		System.out.println("user ID : "+this.UserID+" username :"+this.username+" user email : "+this.Email);
	}
	
	
	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		username = userName;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getUserRoles() {
		return UserRoles;
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
	

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
