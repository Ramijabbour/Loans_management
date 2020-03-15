package com.example.security.roles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.security.UserRoles.UserRoleService;
import com.example.security.permissions.Permissions;
import com.example.security.permissions.PermissionsService;
import com.example.security.rolesPermissions.RolesPermissionsService;


@RestController 
public class RolesController {
	@Autowired 
	private RolesService rolesService ; 
	@Autowired
	private RolesPermissionsService rolesPermissionsService ; 
	@Autowired 
	private UserRoleService userRolesService ; 
	@Autowired
	private PermissionsService permissionsService ; 
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/roles/all")
	public ModelAndView getAllRoles() {
		ModelAndView mav = new ModelAndView("roles/all");
		mav.addObject("roleslist",this.rolesService.getAllRoles());
		return mav;
	}
	
	
	@RequestMapping(method = RequestMethod.POST , value = "/roles/viewrole")
	public ModelAndView viewRoleIndex(@ModelAttribute Roles role ) {
		ModelAndView mav = new ModelAndView("roles/viewrole");
		mav.addObject("permissionslist",this.rolesPermissionsService.getPermissionsOfRole(role));
		mav.addObject("userslist",this.userRolesService.getUsersWithRole(role));
		return mav ; 
	}

	
	@RequestMapping(method = RequestMethod.GET ,value = "/roles/addrole")
	public ModelAndView addRoleRequest() {
		ModelAndView mav = new ModelAndView("roles/addrole");
		mav.addObject("roleobject",new Roles());
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/role/addrole")
	public void addRoleResponse(@ModelAttribute Roles role ,HttpServletResponse response) throws IOException {
		if(this.rolesService.addRole(role)) {
			response.sendError(200, "role added");
		}else {
			response.sendError(400, "role already exist");
		}
		response.sendRedirect("/roles/all");
	} 
	
	@RequestMapping(method = RequestMethod.POST , value = "/roles/delete")
	public void deleteRole(@ModelAttribute Roles role , HttpServletResponse response ) throws IOException {
		this.rolesService.deleteRole(role);
		response.sendRedirect("/roles/all");
	}

	 
	/*
	 *returns current role permissions List
	 * */
	
	@RequestMapping(method = RequestMethod.GET , value = "/roles/role/permissions/revoke")
	public ModelAndView revokePermissionsRequest(@ModelAttribute Roles role ) {
		ModelAndView mav = new ModelAndView("roles/revokepermissions");
		mav.addObject("permissionslist",this.rolesPermissionsService.getRolePermissionsList(role));
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/roles/role/permissions/revoke")
	public ModelAndView revokePermissionFromRole(@ModelAttribute Roles role , @ModelAttribute List<Permissions> permissionsList ){
		for(Permissions permission : permissionsList) {
			this.rolesService.revokePermissionFromRoles(permission, role);
		}
		ModelAndView mav = new ModelAndView("roles/viewrole");
		mav.addObject("permissionslist",this.rolesPermissionsService.getPermissionsOfRole(role));
		mav.addObject("userslist",this.userRolesService.getUsersWithRole(role));
		return mav ; 
	}
	
	
	/*
	 * grant methods  
	 * GET : we return all the permissions that role does not have 
	 * services used : permissions Service 
	 * roles class : get current permissions of role 
	 * POST : assign list of permissions to role 
	 * uses : roles Service - rolesPermissionsService - UserRolesService 
	 * returns role view with current permissions and bearing users 
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET , value = "/roles/role/permissions/grant")
	public ModelAndView grantpermissionToRole(@ModelAttribute Roles role ) {
		ModelAndView mav = new ModelAndView("roles/grantpermissions");
		List<Permissions> allPermissions = this.permissionsService.getAllPermissions();
		List<String> currentPermissions =  role.getAssignedPermissionsList();
		List<Permissions> uniquePermissions = new ArrayList<Permissions>();
		for(Permissions permission : allPermissions ) {
			if(!currentPermissions.contains(permission.getPermissionName())) {
				uniquePermissions.add(permission);
			}
		}
		mav.addObject("permissionsList",uniquePermissions);
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/roles/role/permissions/grant")
	public ModelAndView grantPermission(@ModelAttribute List<Permissions> permissionsList , Roles role ) {
		this.rolesService.grantPermissionsToRole(permissionsList, role);
		ModelAndView mav = new ModelAndView("roles/viewrole");
		mav.addObject("permissionslist",this.rolesPermissionsService.getPermissionsOfRole(role));
		mav.addObject("userslist",this.userRolesService.getUsersWithRole(role));
		return mav ; 
	}
	
	//grant permissions end 
	
	
}
