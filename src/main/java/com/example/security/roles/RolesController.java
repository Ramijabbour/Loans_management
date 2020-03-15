package com.example.security.roles;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.security.UserRoles.UserRoleService;
import com.example.security.permissions.Permissions;
import com.example.security.rolesPermissions.RolesPermissionsService;


@RestController 
public class RolesController {
	@Autowired 
	private RolesService rolesService ; 
	@Autowired
	private RolesPermissionsService rolesPermissionsService ; 
	@Autowired 
	private UserRoleService userRolesService ; 
	
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
	
	@RequestMapping(method = RequestMethod.POST , value = "/roles/revokepermission")
	public ModelAndView revokePermissionFromRole(@ModelAttribute Roles role , @ModelAttribute Permissions permission ){
		this.rolesService.revokePermissionFromRoles(permission, role);
		ModelAndView mav = new ModelAndView("roles/viewrole");
		mav.addObject("permissionslist",this.rolesPermissionsService.getPermissionsOfRole(role));
		mav.addObject("userslist",this.userRolesService.getUsersWithRole(role));
		return mav ; 
	}
	
}
