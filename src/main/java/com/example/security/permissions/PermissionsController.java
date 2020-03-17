package com.example.security.permissions;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class PermissionsController {
	
	@Autowired
	PermissionsService permissionsService ;
	
	@RequestMapping(method = RequestMethod.GET ,value = "/security/permissions/commit")
	public void commitPermissions() {
		this.permissionsService.commitPermissionsInjection();
	}
	
	
	@RequestMapping(method = RequestMethod.GET ,value = "/security/permissions/all")
	public ModelAndView getAllPermissions() {
		ModelAndView mav = new ModelAndView("Permissions/allPermissions");
		mav.addObject("permissionsList",this.permissionsService.getAllPermissions());
		return mav ; 
	}
	
	
	@RequestMapping(method = RequestMethod.GET ,value = "/security/permissions/manage/{permissionId}")
	public ModelAndView managePermission(@PathVariable int permissionId,HttpServletResponse response ) throws IOException {
		ModelAndView mav = new ModelAndView("Permissions/managePermission");
		Permissions permission = this.permissionsService.getPermissionById(permissionId);
		if(permission == null ) {
			//replace with error msg later 
			response.sendRedirect("/security/permissions/all");
		}
		mav.addObject("permission",permission);
		mav.addObject("rolesList",this.permissionsService.getRolesWithPermission(permission));
		mav.addObject("userslist",this.permissionsService.getUsersWithPermission(permission));
		return mav ; 
	}
	
	
	
}
