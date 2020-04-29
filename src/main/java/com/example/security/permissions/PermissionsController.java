package com.example.security.permissions;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.SiteConfiguration;

@RestController
public class PermissionsController {
	
	@Autowired
	PermissionsService permissionsService ;
	
	@RequestMapping(method = RequestMethod.GET ,value = "/security/permissions/commit")
	public void commitPermissions() {
		this.permissionsService.commitPermissionsInjection();
	}
	
	
	@RequestMapping(method = RequestMethod.GET ,value = "/security/permissions/all")
	public ModelAndView getAllPermissions(@Param(value ="index") int index) {
		ModelAndView mav = new ModelAndView("Permissions/allPermissions");
		List<Permissions> permissionsList =  this.permissionsService.getAllPermissions(index) ; 
		mav.addObject("permissionsList",permissionsList);
		if(permissionsList.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
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
