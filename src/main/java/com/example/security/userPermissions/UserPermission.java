package com.example.security.userPermissions;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.example.security.permissions.Permissions;
import com.example.security.user.User;

@Entity
public class UserPermission {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int UserPermissionID ;
	
	
	@ManyToOne
    private User user = null;
	
	@ManyToOne
    private Permissions permission = null;
	
	
	public UserPermission() {}
	
	public UserPermission(User user , Permissions permission) {
		this.permission = permission; 
		this.user = user ; 
	}
	
	public User getUser() {
		return this.user ; 
	}
	public Permissions getPermissions() {
		return this.permission; 
	}
	
}
