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
		List<Permissions> rolePermissionsList = this.getPermissionsOfRole(role);
		for(Permissions tempPermission : rolePermissionsList ) {
			if(tempPermission.getId() == permission.getId()) {
			 return ; 
			}
		}
		this.rolePermissionRepository.save(new RolePermission(permission,role));
	}
	
	public List<Permissions> getPermissionsOfRole(Roles role ){
		List<Permissions> rolePermissionsList = new ArrayList<Permissions>(); 
		List<RolePermission> rolePermissionsFromRepo = this.rolePermissionRepository.findAll() ; 
		for(RolePermission rolePermission : rolePermissionsFromRepo) {
			if(rolePermission.getRole().getRoleName().equalsIgnoreCase(role.getRoleName())){
				rolePermissionsList.add(rolePermission.getPermission());
			}
		}
		return rolePermissionsList ; 
	}
	
	
	
	public List<Roles> getRolesWithPermission(Permissions permission){
		List<Roles> rolesWithPermissionList = new ArrayList<Roles>() ; 
		List<RolePermission> rolePermissionList = this.rolePermissionRepository.findAll() ; 
		for(RolePermission rolePermission : rolePermissionList) {
			if(rolePermission.getPermission().getId() == permission.getId()) {
				rolesWithPermissionList.add(rolePermission.getRole());
			}
		}	
		return rolesWithPermissionList ; 
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
	

	public void addPermissionsToRole(Roles role , Permissions permission ) {
			this.addRolePermission(permission, role);
	}
	
	public void revokePermissionFromRole (Roles role , Permissions permission) {
		List<RolePermission> rolePermissionsList = this.rolePermissionRepository.findAll() ; 
		for(RolePermission rolePermission : rolePermissionsList ) {
			if(rolePermission.getRole().getId() == role.getId() && rolePermission.getPermission().getId() == permission.getId()) {
				this.rolesService.revokePermissionFromRoles(permission, role);
				this.rolePermissionRepository.delete(rolePermission);
				return ; 
			}
		}
	}
	
}
