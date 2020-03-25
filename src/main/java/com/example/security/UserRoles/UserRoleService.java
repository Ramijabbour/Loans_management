package com.example.security.UserRoles;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.roles.Roles;
import com.example.security.user.User;
import com.example.security.user.UserService;

@Service
public class UserRoleService {

	@Autowired
	UserRoleRepository userRoleRepository ; 
	
	@Autowired
	private UserService userService ; 
	
	
	public List<User> getUsersWithRole(Roles role ) {
		List<UserRole> userRoleList = this.userRoleRepository.findAll() ; 
		List<User> usersWithRole = new ArrayList<User>();
		for(UserRole  userRole  : userRoleList) {
			if(userRole.getRole().getRoleName().equalsIgnoreCase(role.getRoleName())) {
				usersWithRole.add(userRole.getUser());
			}
		}
		return usersWithRole ;
	}
	
	public List<Roles> getRolesOfUsers(User user  ){
		List<UserRole> userRolesList = this.userRoleRepository.findAll() ; 
		List<Roles> userRoles = new ArrayList<Roles>();
		for(UserRole  userRole  : userRolesList) {
			if(userRole.getUser().getUsername().equalsIgnoreCase(user.getUsername())) {
				userRoles.add(userRole.getRole());
			}
		}
		return userRoles ;
	}
	
	
	//role grant process starts from here 
	@Transactional
	public void grantRoleToUser(Roles role , User user ) {
			if(!this.userRoleExist(user, role)) {
				this.userService.addRolesToUser(user, role);
				this.userRoleRepository.save(new UserRole(user,role));
			}			
	}
	
	@Transactional
	public void revokeRoleFromUser(User user , Roles role ) {
		List<UserRole> userRoleList = this.userRoleRepository.findAll() ; 
		for(UserRole userRoleModel : userRoleList) {
			if(userRoleModel.getUser().getUsername().equalsIgnoreCase(user.getUsername())) {
				if(userRoleModel.getRole().getRoleName().equalsIgnoreCase(role.getRoleName())) {
					this.userService.revokeRoleFromUser(userRoleModel.getUser(),role);
					this.userRoleRepository.delete(userRoleModel);
				}
			}
		}
	}
	
	public void deleteRole(Roles role ) {
		List<UserRole> userRoleList = this.userRoleRepository.findAll() ; 
		for(UserRole userRoleModel : userRoleList) {
			if(userRoleModel.getRole().getRoleName().equalsIgnoreCase(role.getRoleName())) {
				userService.revokeRoleFromUser(userRoleModel.getUser(), userRoleModel.getRole());
				this.userRoleRepository.delete(userRoleModel);
			}else {
				continue ; 
			}
		}
	}
	
	
	public void deleteUser(User user ) {
		List<UserRole> userRolesList = this.userRoleRepository.findAll() ; 
		for(UserRole userRole : userRolesList) {
			if(userRole.getUser().getUserID() == user.getUserID() ) {
				this.userRoleRepository.delete(userRole);
			}else {
				continue ;
			}
		}
	}
	
	public boolean userRoleExist(User user , Roles role) {
		List<UserRole> userRoleList = this.userRoleRepository.findAll() ; 
		for(UserRole userRoleModel : userRoleList) {
			if(userRoleModel.getUser().getUsername().equalsIgnoreCase(user.getUsername())) {
				if(userRoleModel.getRole().getRoleName().equalsIgnoreCase(role.getRoleName())) {
					return true; //the repository contains this entry 
				}
			}
		}
		return false ; 
	}

}
