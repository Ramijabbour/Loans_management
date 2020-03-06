package com.example.security.userPermissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.aspect.Exceptions;

@Service
public class UserPermissionsService {	
	@Autowired
	UserPermissionRepository userPermissionsRepository ; 
	
	public void addUserPermission(UserPermission userPermissions ) {
		if(this.userPermissionsRepository.findAll().contains(userPermissions)) {
			throw new Exceptions(-405,"this user already has this permission");
		}else {
			this.userPermissionsRepository.save(userPermissions);
		}
	}
	
	public void deleteUserPermission(UserPermission userPermission ) {
		this.userPermissionsRepository.delete(userPermission);
	}
	
	public void updateUserPermission(UserPermission userPermission ) {
		if(!this.userPermissionsRepository.findAll().contains(userPermission)) {
			throw new Exceptions(-404,"the requested user permission cannot be found ");
		}else {
			this.userPermissionsRepository.save(userPermission); 
		}
	}
	
	
}
