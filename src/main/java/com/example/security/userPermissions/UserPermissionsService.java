package com.example.security.userPermissions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.aspect.Exceptions;
import com.example.security.permissions.Permissions;
import com.example.security.user.User;

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

	
	public boolean checkUserPermissionDuplication(User user , Permissions permission) {
		List<UserPermission> userPerm = new ArrayList<UserPermission>();  
		for ( UserPermission userPermission : this.userPermissionsRepository.findAll()) {
			if(user.getUserID() == userPermission.getUser().getUserID())
			userPerm.add(userPermission);
		}
		
		
		return false ; 
	}
	
}
