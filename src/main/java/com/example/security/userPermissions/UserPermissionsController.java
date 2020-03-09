package com.example.security.userPermissions;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.security.permissions.Permissions;
import com.example.security.permissions.PermissionsService;
import com.example.security.user.User;

@RestController
public class UserPermissionsController {
	
	@Autowired
	UserPermissionsService userPermissionsService ; 
	
	@Autowired 
	PermissionsService permissionsService; 
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/userpermissions/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("userpermissions/index");
		return mav ;
	}
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/userpermissions/assign")
	public ModelAndView assignPermissionToUser(@ModelAttribute User user) {
		ModelAndView mav = new ModelAndView("userpermissions/assignpermission");
		mav.addObject("user",user);
		mav.addObject("permissionslist",this.permissionsService.getAllPermissions());
		return mav ;
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.GET , value = "/userpermissions/assig")
	public void postAssignPermissionsToUser(@ModelAttribute List<Permissions> permissions , @ModelAttribute User user,HttpServletResponse response ) throws IOException {
		for(Permissions permission : permissions  ) {
			UserPermission userPermissions = new UserPermission(user,permission) ; 
			this.userPermissionsService.addUserPermission(userPermissions);
		}		
		response.sendRedirect("/userpermissions/index");
	}
	
	
}
