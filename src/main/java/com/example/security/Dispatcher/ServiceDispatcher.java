package com.example.security.Dispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.Activities.ActivityService;
import com.example.security.UserRoles.UserRoleService;
import com.example.security.permissions.PermissionsService;
import com.example.security.roles.RolesService;
import com.example.security.rolesPermissions.RolesPermissionsService;
import com.example.security.user.UserService;
import com.example.security.userPermissions.UserPermissionsService;

@Service
public class ServiceDispatcher {

	@Autowired
	private UserService userService ; 
	
	@Autowired
	private RolesService rolesService ; 
	
	@Autowired 
	private PermissionsService permissionsService ;

	@Autowired 
	private UserPermissionsService userPermissionsService ; 
	
	@Autowired 
	private RolesPermissionsService rolesPermissionsService ; 
	
	@Autowired 
	private ActivityService activityService ; 
	
	@Autowired 
	private UserRoleService userRolesService ; 
	
	
	public UserService getUserService() {
		return userService;
	}

	public RolesService getRolesService() {
		return rolesService;
	}

	public PermissionsService getPermissionsService() {
		return permissionsService;
	}


	public UserPermissionsService getUserPermissionsService() {
		return userPermissionsService;
	}

	
	public RolesPermissionsService getRolesPermissionsService() {
		return rolesPermissionsService;
	}

	
	public ActivityService getActivityService() {
		return activityService;
	}

	public UserRoleService getUserRolesService() {
		return userRolesService;
	} 
	
	
	

}
