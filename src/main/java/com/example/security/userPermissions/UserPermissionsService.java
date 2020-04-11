package com.example.security.userPermissions;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.permissions.Permissions;
import com.example.security.user.User;
import com.example.security.user.UserService;

@Service
public class UserPermissionsService {

	
	@Autowired
	private UserPermissionsRepository userPermissionsRepository ; 

	@Autowired
	private UserService userService ; 
	
	public List<User> getUsersWithPermission(Permissions permission){
		List<UserPermission> userPermissions = this.userPermissionsRepository.findAll(); 
		List<User> usersWithPermission = new ArrayList<User>(); 
		
		for(UserPermission userPermission : userPermissions ) {
			if(userPermission.getPermissions().getPermissionName().equalsIgnoreCase(permission.getPermissionName())) {
				usersWithPermission.add(userPermission.getUser());
			}
		}
		
		return usersWithPermission ; 
	}
	
	
	public List<Permissions> getPermissionsOfUser(User user ){
		List<UserPermission> userPermissions = this.userPermissionsRepository.findAll(); 
		List<Permissions> permissionsOfUser = new ArrayList<Permissions>() ; 
		
		for(UserPermission tempUserPermissions : userPermissions ) {
			if(tempUserPermissions.getUser().getUsername().equalsIgnoreCase(user.getUsername())) {
				permissionsOfUser.add(tempUserPermissions.getPermissions());
			}
		}
		return permissionsOfUser; 
	}
	
	public void grantPermissionsToUser(Permissions permission , User user ) {
			if(!this.userPermissionsExsit(permission, user)) {
				this.userService.addPermissionsToUser(user, permission);
				UserPermission userPermission = new UserPermission(user,permission);
				this.userPermissionsRepository.save(userPermission);
			}	
	}
	
	public void deletePermission(Permissions permission) {
		List<UserPermission> userPermissionsList =  this.userPermissionsRepository.findAll() ; 		
		//delete from users 
		for(UserPermission userPermission : userPermissionsList) {
			if(userPermission.getPermissions().getPermissionName().equalsIgnoreCase(permission.getPermissionName())) {
				this.userService.revokePermissionFromUser(userPermission.getUser(), userPermission.getPermissions());
				this.userPermissionsRepository.delete(userPermission);
			}
		}
	}
	
	public void deleteUser(User user ) {
		List<UserPermission> userPermissionsList = this.userPermissionsRepository.findAll(); 
		for(UserPermission userPermission : userPermissionsList) {
			if(userPermission.getUser().getUserID() == user.getUserID()) {
				this.userPermissionsRepository.delete(userPermission);
			}else {
				continue ; 
			}
		}
	}
	
	@Transactional 
	public void  revokePermissionFromUser(User user , Permissions permission ) {
		List<UserPermission> userPermissionList = this.userPermissionsRepository.findAll(); 
		for(UserPermission userPermission : userPermissionList) {
			if(userPermission.getUser().getUsername().equalsIgnoreCase(user.getUsername())) {
				if(userPermission.getPermissions().getPermissionName().equalsIgnoreCase(permission.getPermissionName())) {
					this.userService.revokePermissionFromUser(userPermission.getUser(), userPermission.getPermissions());
					this.userPermissionsRepository.delete(userPermission);
				}
			}
		}
		
		
	}
	
	public boolean userPermissionsExsit(Permissions permission , User user ) {
		List<UserPermission> userPermissions = this.userPermissionsRepository.findAll() ; 
		for(UserPermission userPermission : userPermissions ) {
			if(userPermission.getUser().getUsername().equalsIgnoreCase(user.getUsername())) {
				if(userPermission.getPermissions().getPermissionName().equalsIgnoreCase(permission.getPermissionName())) {
					return true ; 
				}
			}
		}
		return false ; 
	}
	
}
