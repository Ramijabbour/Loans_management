package com.example.security.UserRoles;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.example.security.roles.Roles;
import com.example.security.user.User;

@Entity
public class UserRole {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int UserRoleID ;
	
	@ManyToOne
    private User user = null;
	@ManyToOne
    private Roles role = null;
	
	public UserRole() {}
	
	
	public UserRole(User user , Roles role ) {
		this.user = user ; 
		this.role = role ; 
	}
	
	public User getUser() {
		return this.user ; 
	}
	public Roles getRole() {
		return this.role ; 
	}
	
}
