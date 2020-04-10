package com.example.security.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.MasterService;

@Entity
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int UserID ;

	@Column(nullable = false )
	private String email=" ";

	@Column(nullable = false )
	private String password = " ";
	
	private String username ;
	private String Gender="" ;
	private String UserPermissions = "none" ; 
	private String UserRoles ="none"; 
	private LocalDateTime createdAt ; 
	private boolean Active = false ; 

	
	public User() {
		this.createdAt = MasterService.getCurrDateTime() ;
		this.UserRoles = "none"; 
		this.UserPermissions = "none";
	}
	
	public User(String email, String password, String username, String gender, String userPermissions,
			String userRoles, boolean isActive) {
		super();
		this.email = email;
		this.password = password;
		this.username = username;
		Gender = gender;
		if(userPermissions.equalsIgnoreCase("") || userPermissions.equalsIgnoreCase(" "))
			this.UserPermissions = "none";
		else 
			this.UserPermissions = userPermissions ; 
		if(userRoles.equalsIgnoreCase("") || userRoles.equalsIgnoreCase(" "))
			this.UserRoles = "none";
		else 
			this.UserRoles = userRoles; 
		this.Active = isActive;
		this.createdAt = MasterService.getCurrDateTime() ; 
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
	

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public boolean hasRole(String role) {
		if(this.UserRoles.equalsIgnoreCase(" ")) {
			return false ; 
		}
		else if (!this.convertRolesToList().contains(role)) {
			return false ;
		}
		return true ; 
	}
	
	public boolean hasPermission(String permission) {
		if(this.UserPermissions.equalsIgnoreCase(" ")) {
			return false ; 
		}
		else if (!this.convertPermissionsToList().contains(permission)) {
			return false  ; 
		}
		return true ; 
	}

	public void addRole(String role ) {
		if(this.UserRoles.equalsIgnoreCase(" ") || this.UserRoles.equalsIgnoreCase("")) {
			this.UserRoles=role;
		}else
		this.UserRoles+=","+role;
	}
	
	public void addPermission(String permission ) {
		if(this.UserPermissions.equalsIgnoreCase(" ") || this.UserPermissions.equalsIgnoreCase("")) {
			this.UserPermissions=permission;
		}else
		this.UserPermissions+=","+permission;
	}

	public List<String> convertPermissionsToList(){
		if(this.UserPermissions.equalsIgnoreCase("")) {
			return null ; 
		}
		else {
			List<String> userPermissions = new ArrayList<String>() ;
			String[] permissions = this.UserPermissions.split(",");
			for(int i = 0 ; i<permissions.length ; i++) {
				userPermissions.add(permissions[i]);
			}
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
			for(int i =0 ; i < roles.length ; i++) {
				userRoles.add(roles[i]); 
			}
			return userRoles ; 
			
		}
	}
	
	public void revokeRoleFromUser(String role ) {
		List<String> userRoles = this.convertRolesToList() ; 
		if(userRoles.size() != 0 ) {
		if(userRoles.contains(role)) {
			userRoles.remove(userRoles.indexOf(role));
			this.UserRoles = "";
			for(String tempRole : userRoles) {
				this.addRole(tempRole);
				}
			}
		}
	}
	
	public void revokePermissionFromUser(String permission) {
		List<String> userPermissions = this.convertPermissionsToList();
		if(userPermissions.size() != 0 ) {
			if(userPermissions.contains(permission)) {
				userPermissions.remove(permission);
				this.UserPermissions = "";
				for(String tempPermission : userPermissions) {
					this.addPermission(tempPermission);
				}
			}
		}
	}

	
}
