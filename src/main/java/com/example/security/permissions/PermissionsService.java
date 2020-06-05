package com.example.security.permissions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Banks.Banks;
import com.example.SiteConfig.SiteConfiguration;
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
	}
	
	public int getPermissionsCount() {
		return this.permissionsRepository.findAll().size() ; 
	}
	
	public List<Permissions> getAllPermissions(int PageNumber){
		Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize(), Sort.by("id"));
		Page<Permissions> pagedResult = this.permissionsRepository.findAll(paging);
		if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Permissions>();
        }
	}
	
	public void addPermission(Permissions permission ) {
		if(this.permissionsRepository.findAll().contains(permission)) {
			throw new DataDuplicationException();
		}
		this.permissionsRepository.save(permission); 
	}
	
	public void updatePermission(Permissions permission ) {
		if (this.permissionsRepository.findById(permission.getId()) == null ) {
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
			if(permission.getId() == permissionID) {
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
	
	
	public List<Permissions> SearchbyPermissionkName(int PageNumber,String permissionName){
		Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize(), Sort.by("id"));
		Slice<Permissions> pagedResult = this.permissionsRepository.findByPermissionName(permissionName,paging);
		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Permissions>();
		}
	}
	
	
	public List<Permissions> getAllPermissionsNoPage(){
		return this.permissionsRepository.findAll() ; 
	}
	
}
