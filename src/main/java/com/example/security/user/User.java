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
	
	private String UserName="";
	private String Gender="" ;
	private String UserPermissions = "" ; 
	private String UserRoles =""; 
	

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
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
	

}
