package com.example.security.permissions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.aspect.Exceptions;

@Service
public class PermissionsService {
	
	@Autowired
	PermissionsRepository permissionsRepository ; 
	
	public List<Permissions> getAllPermissions(){
		return this.permissionsRepository.findAll() ; 
	}
	
	public void addPermission(Permissions permission ) {
		if(this.permissionsRepository.findAll().contains(permission)) {
			throw new Exceptions(-405 , "permission already exist in the System");
		}
		this.permissionsRepository.save(permission); 
	}
	
	public void updateRole(Permissions permission ) {
		if (this.permissionsRepository.findById(permission.getPermissionID()) == null ) {
			throw new Exceptions(-404,"item not found in the System");
		}else 
			{
				this.permissionsRepository.save(permission);
			} 
	}
	
	public void deletePermission(Permissions permission) {
		this.permissionsRepository.delete(permission);
	}
	
}
