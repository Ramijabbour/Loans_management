package com.example.security.roles;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.aspect.Exceptions;
import com.example.security.permissions.PermissionsService;

@Service
public class RolesService {
	@Autowired
	RolesRepository rolesRepository ; 
	
	
	RolesService(){
		System.out.println("roles Service Started -----------------------> ");
		Method[] methods =  this.getClass().getDeclaredMethods();
		List<String> methodsNames = new ArrayList<String>(); 
		for(Method method : methods) {
			System.out.println("method name from service : "+method.getName());
			methodsNames.add(method.getName());
		}
		PermissionsService.addPermissionsToPermissionsList(methodsNames);
	}
	
	public void addRole(Roles role ) {
		if(this.checkRoleDuplication(role)) {
			throw new Exceptions(-405,"role already exist in the System");
		}else {
		this.rolesRepository.save(role);
		} 
	}
	
	public void updateRole(Roles role ) {
		if (this.rolesRepository.findById(role.getRoleID()) == null ) {
			throw new Exceptions(-404,"item not found in the System");
		}else {
				this.rolesRepository.save(role);
			} 
	}
	
	public void deleteRole(Roles role ) {
		if (this.rolesRepository.findById(role.getRoleID()) == null ) {
			throw new Exceptions(-404,"item not found in the System");
		}else {
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
