package com.example.security.rolesPermissions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.permissions.Permissions;
import com.example.security.roles.Roles;
import com.example.security.roles.RolesService;

@Service
public class RolesPermissionsService {

	@Autowired
	RolePermissionRepository rolePermissionRepository ; 
	
	@Autowired 
	private RolesService rolesService ; 
	
	 
	public void addRolePermission(Permissions permission,Roles role ) {
		List<RolePermission> rolePermissionsList = this.getPermissionsOfRole(role);
		for(RolePermission tempRolePermission : rolePermissionsList ) {
			if(tempRolePermission.getPermission().getPermissionID() == permission.getPermissionID()) {
			 return ; 
			}
		}
		this.rolePermissionRepository.save(new RolePermission(permission,role));
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
	
	public List<Permissions> getRolePermissionsList(Roles role ){
		List<RolePermission> rolePermissions = this.getPermissionsOfRole(role);
		List<Permissions> permissionsList = new ArrayList<Permissions>(); 
		for(RolePermission rolePermission : rolePermissions) {
			permissionsList.add(rolePermission.getPermission());
		}
		return permissionsList ; 
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
	

	public void addPermissionsToRole(Roles role , List<Permissions> permission ) {
		for(Permissions tempPermission : permission) {
			this.addRolePermission(tempPermission, role);
		}
	}
	
}
