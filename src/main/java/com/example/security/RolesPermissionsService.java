package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.aspect.Exceptions;
import com.example.dataBase.RolePermissionRepository;
import com.example.models.RolePermission;

@Service
public class RolesPermissionsService {

	@Autowired
	RolePermissionRepository rolePermissionRepository ; 
	
	public void addRolePermission(RolePermission rolePermission) {
		if(this.rolePermissionRepository.findAll().contains(rolePermission)) {
			throw new Exceptions(-405,"this role already contains this permission");
		}
		this.rolePermissionRepository.save(rolePermission);
	}
	
	public void deleteRolePermission(RolePermission rolePermission) {
		this.rolePermissionRepository.delete(rolePermission);
	}
	
	public void updateRolePermission(RolePermission rolePermission) {
		if(!this.rolePermissionRepository.findAll().contains(rolePermission)) {
			throw new Exceptions(-404,"cannot find requested role-permission ");
		}
	}


}
