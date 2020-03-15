package com.example.security.rolesPermissions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.aspect.Exceptions;
import com.example.security.permissions.Permissions;
import com.example.security.roles.Roles;
import com.example.security.roles.RolesService;

@Service
public class RolesPermissionsService {

	@Autowired
	RolePermissionRepository rolePermissionRepository ; 
	
	@Autowired 
	private RolesService rolesService ; 
	
	public void addRolePermission(RolePermission rolePermission) {
		if(this.rolePermissionRepository.findAll().contains(rolePermission)) {
			throw new Exceptions(-405,"this role already contains this permission");
		}
		this.rolePermissionRepository.save(rolePermission);
	}
	
	public List<RolePermission> getPermissionsOfRole(Roles role ){
		List<RolePermission> rolePermissionsList = new ArrayList<RolePermission>(); 
		List<RolePermission> rolePermissionsFromRepo = this.rolePermissionRepository.findAll() ; 
		for(RolePermission rolePermission : rolePermissionsFromRepo) {
			if(rolePermission.getRole().getRoleName().equalsIgnoreCase(role.getRoleName())){
				rolePermissionsList.add(rolePermission);
			}
		}
		return rolePermissionsList ; 
	}
	
	//
	public void deletePermission(Permissions permission) {
		List<RolePermission> rolePermissionList = this.rolePermissionRepository.findAll() ;
		for(RolePermission rolePermission : rolePermissionList ) {
			if(rolePermission.getPermission().getPermissionName().equalsIgnoreCase(permission.getPermissionName())) {
				this.rolesService.revokePermissionFromRoles(rolePermission.getPermission(),rolePermission.getRole());
				this.rolePermissionRepository.delete(rolePermission);
			}
		}
	}
	
	public void deleteRole(Roles role ) {
		List<RolePermission> rolePermissionList = this.rolePermissionRepository.findAll(); 
		for(RolePermission rolePermission : rolePermissionList) {
			if(rolePermission.getRole().getRoleName().equalsIgnoreCase(role.getRoleName())) {
				this.rolePermissionRepository.delete(rolePermission);
			}
		}
	}
	
	public void updateRolePermission(RolePermission rolePermission) {
		if(!this.rolePermissionRepository.findAll().contains(rolePermission)) {
			throw new Exceptions(-404,"cannot find requested role-permission ");
		}
	}


}
