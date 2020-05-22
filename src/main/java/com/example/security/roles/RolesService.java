package com.example.security.roles;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.SiteConfiguration;
import com.example.aspect.ItemNotFoundException;
import com.example.security.UserRoles.UserRoleService;
import com.example.security.permissions.Permissions;
import com.example.security.permissions.PermissionsService;
import com.example.security.rolesPermissions.RolesPermissionsService;
import com.example.security.user.User;

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
	
	
	public List<Roles> getAllRoles(int PageNumber){
		Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize(), Sort.by("id"));
		Page<Roles> pagedResult = this.rolesRepository.findAll(paging);
		if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Roles>();
        }
	}
	
	public Roles getRoleByID(int roleid) {
		List<Roles> rolesList = this.rolesRepository.findAll() ; 
		if(rolesList.isEmpty()) {
			return null ; 
		}else {
			for(Roles role : rolesList ) {
				if(role.getId() == roleid ) {
					return role ; 
				}
			}
		}
		return null ; 
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
		if (this.rolesRepository.findById(role.getId()) == null ) {
			throw new ItemNotFoundException();
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
	
	public void grantPermissionsToRole(Permissions permission, Roles role ) {
		if(permission == null ) {
			return  ;
		}
		role.addSinglePermissionToRole(permission.getPermissionName());
		this.rolesPermissionsService.addPermissionsToRole(role,permission) ; 
	}
	
}
