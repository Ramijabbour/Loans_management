package com.example.security.UserRoles;

import java.util.ArrayList;
import java.util.Arrays;
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
		List<UserRoleModel> userRoleList = this.userRoleRepository.findAll() ; 
		List<User> usersWithRole = new ArrayList<User>();
		for(UserRoleModel  userRole  : userRoleList) {
			if(userRole.getRole().getRoleName().equalsIgnoreCase(role.getRoleName())) {
				usersWithRole.add(userRole.getUser());
			}
		}
		return usersWithRole ;
	}
	
	public List<Roles> getRolesOfUsers(User user  ){
		List<UserRoleModel> userRolesList = this.userRoleRepository.findAll() ; 
		List<Roles> userRoles = new ArrayList<Roles>();
		for(UserRoleModel  userRole  : userRolesList) {
			if(userRole.getUser().getUserName().equalsIgnoreCase(user.getUserName())) {
				userRoles.add(userRole.getRole());
			}
		}
		return userRoles ;
	}
	
	
	//role grant process starts from here 
	@Transactional
	public void grantRoleToUser(List<Roles> role , User user ) {
		for(Roles tempRole : role) {
			if(!this.userRoleExist(user, tempRole)) {
				this.userService.addRolesToUser(user, Arrays.asList(tempRole));
				this.userRoleRepository.save(new UserRoleModel(user,tempRole));
			}	
		}
	}
	
	@Transactional
	public void revokeRoleFromUser(User user , Roles role ) {
		List<UserRoleModel> userRoleList = this.userRoleRepository.findAll() ; 
		for(UserRoleModel userRoleModel : userRoleList) {
			if(userRoleModel.getUser().getUserName().equalsIgnoreCase(user.getUserName())) {
				if(userRoleModel.getRole().getRoleName().equalsIgnoreCase(role.getRoleName())) {
					this.userService.revokeRoleFromUser(userRoleModel.getUser(),role);
					this.userRoleRepository.delete(userRoleModel);
				}
			}
		}
	}
	
	public void deleteRole(Roles role ) {
		List<UserRoleModel> userRoleList = this.userRoleRepository.findAll() ; 
		for(UserRoleModel userRoleModel : userRoleList) {
			if(userRoleModel.getRole().getRoleName().equalsIgnoreCase(role.getRoleName())) {
				userService.revokeRoleFromUser(userRoleModel.getUser(), userRoleModel.getRole());
				this.userRoleRepository.delete(userRoleModel);
			}else {
				continue ; 
			}
		}
	}
	
	
	public boolean userRoleExist(User user , Roles role) {
		List<UserRoleModel> userRoleList = this.userRoleRepository.findAll() ; 
		for(UserRoleModel userRoleModel : userRoleList) {
			if(userRoleModel.getUser().getUserName().equalsIgnoreCase(user.getUserName())) {
				if(userRoleModel.getRole().getRoleName().equalsIgnoreCase(role.getRoleName())) {
					return true; //the repository contains this entry 
				}
			}
		}
		return false ; 
	}
}
