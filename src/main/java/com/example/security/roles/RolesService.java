package com.example.security.roles;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.aspect.Exceptions;
import com.example.security.UserRoles.UserRoleService;
import com.example.security.permissions.Permissions;
import com.example.security.permissions.PermissionsService;
import com.example.security.rolesPermissions.RolesPermissionsService;

@Service
public class RolesService {
	@Autowired
	RolesRepository rolesRepository ; 
	
	@Autowired 
	private UserRoleService userRoleService ;
	
	@Autowired
	private RolesPermissionsService rolesPermissionsService ; 
	
	//register services to Security Permissions (permissions committed at route /permissions/commit ) 
	RolesService(){
		System.out.println("Roles Service Started -----------------------> ");
		Method[] methods =  this.getClass().getDeclaredMethods();
		List<String> methodsNames = new ArrayList<String>(); 
		for(Method method : methods) {
			System.out.println("method name from service : "+method.getName());
			methodsNames.add(method.getName());
		}
		PermissionsService.addPermissionsToPermissionsList(methodsNames);
		System.out.println("Roles Services Added to Security Permissions ");
	}
	
	
	public List<Roles> getAllRoles(){
		return this.rolesRepository.findAll() ; 
	}
	
	public boolean addRole(Roles role ) {
		if(this.checkRoleDuplication(role)) {
			return false ; 
		}else {
		this.rolesRepository.save(role);
		return true ; 
		} 
	}
	
	
	public void revokePermissionFromRoles(Permissions permission , Roles role  ) {
		List<Roles> rolesList = this.rolesRepository.findAll(); 
		for(Roles tempRole : rolesList ) {
			if(tempRole.getRoleName().equalsIgnoreCase(role.getRoleName())) {
				role.revokePermissionFromRole(permission.getPermissionName());
				this.rolesRepository.save(role);
			}
		}
	}
	
	@Transactional
	public void deleteRole(Roles role ) {
		if (this.rolesRepository.findById(role.getRoleID()) == null ) {
			throw new Exceptions(-404,"item not found in the System");
		}else {
			this.userRoleService.deleteRole(role);
			this.rolesPermissionsService.deleteRole(role);
			this.rolesRepository.delete(role);
		}
	}
		
	
	public boolean checkRoleDuplication(Roles role ) {
		List<Roles> rolesList = this.rolesRepository.findAll() ; 
		for(Roles tempRole : rolesList ) {
			if(role.getRoleName().equalsIgnoreCase(tempRole.getRoleName()))
				return true ; 
		}
		return false  ; 
	}
}
