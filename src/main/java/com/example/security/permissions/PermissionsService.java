package com.example.security.permissions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.aspect.DataDuplicationException;
import com.example.aspect.ItemNotFoundException;
import com.example.security.roles.Roles;
import com.example.security.rolesPermissions.RolesPermissionsService;
import com.example.security.user.User;
import com.example.security.userPermissions.UserPermissionsService;

@Service
public class PermissionsService {
	
	@Autowired
	PermissionsRepository permissionsRepository ; 
	
	
	@Autowired 
	private UserPermissionsService userPermissionsService ; 
	
	@Autowired 
	private RolesPermissionsService rolesPermissionsService ; 
	
	
	private static List<String> PermissionsList = new ArrayList<String>() ; 
	
	PermissionsService(){
		Method[] methods =  this.getClass().getDeclaredMethods();
		List<String> methodsNames = new ArrayList<String>(); 
		for(Method method : methods) {
			System.out.println("method name from service : "+method.getName());
			methodsNames.add(method.getName());
		}
		PermissionsService.addPermissionsToPermissionsList(methodsNames);
	}
	
	public int getPermissionsCount() {
		return this.permissionsRepository.findAll().size() ; 
	}
	
	public List<Permissions> getAllPermissions(){
		return this.permissionsRepository.findAll() ; 
	}
	
	public void addPermission(Permissions permission ) {
		if(this.permissionsRepository.findAll().contains(permission)) {
			throw new DataDuplicationException();
		}
		this.permissionsRepository.save(permission); 
	}
	
	public void updatePermission(Permissions permission ) {
		if (this.permissionsRepository.findById(permission.getPermissionID()) == null ) {
			throw new ItemNotFoundException();
		}else 
			{
				this.permissionsRepository.save(permission);
			} 
	}
	
	//we should add reference to UserPermissions Service && Role Permissions Service -- Cascade delete 
	public void deletePermission(Permissions permission) {
		this.userPermissionsService.deletePermission(permission);
		this.rolesPermissionsService.deletePermission(permission);
		this.permissionsRepository.delete(permission);
	}
	
	public static void addPermissionsToPermissionsList(List<String> permissions) {
		System.out.println("permissions list method invoked with methods : ");
		for(String methodName : permissions) {
			System.out.println(methodName);
		}
		PermissionsList.addAll(permissions);
	}
	
	public void commitPermissionsInjection() {
		List<String> ComparePermissionList = new ArrayList<String>();
		List<Permissions> permissionsDBList = this.permissionsRepository.findAll() ; 
		for(Permissions object : permissionsDBList) {
			ComparePermissionList.add(object.getPermissionName());
		}
		for(String name:PermissionsList) {
			System.out.println("injection check --------------");
			if(!ComparePermissionList.contains(name)) {
				Permissions permission = new Permissions(name);
				permissionsRepository.save(permission);
			}	
		}
	}
	
	
	public Permissions getPermissionById(int permissionID) {
		List<Permissions> permissionsList = this.permissionsRepository.findAll(); 
		for(Permissions permission : permissionsList ) {
			if(permission.getPermissionID() == permissionID) {
				return permission ; 
			}
		}
		return null ;
	}
	
	public List<Roles> getRolesWithPermission(Permissions permission){
		return this.rolesPermissionsService.getRolesWithPermission(permission);
	}
	
	public List<User> getUsersWithPermission(Permissions permission){
		return this.userPermissionsService.getUsersWithPermission(permission);
	}
	
}
