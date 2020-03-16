package com.example.security.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.security.UserRoles.UserRoleService;
import com.example.security.permissions.Permissions;
import com.example.security.permissions.PermissionsService;
import com.example.security.roles.Roles;
import com.example.security.roles.RolesService;
import com.example.security.userPermissions.UserPermissionsService;


@RestController
public class UserController {

	@Autowired
	UserService userService ; 
	
	@Autowired 
	UserRoleService userRoleService ;
	
	@Autowired
	UserPermissionsService userPermissionsService ; 
	
	@Autowired
	PermissionsService permissionsService ; 
	
	@Autowired
	RolesService rolesService ; 
	
	///index page /// 
	@RequestMapping(method = RequestMethod.GET , value = "/adminstration/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("adminstration/index");
		return mav ; 
	}
	
	///all users ///
		@RequestMapping(method = RequestMethod.GET , value = "/adminstration/users/all")
		public ModelAndView getAllUsers() {
			ModelAndView mav = new ModelAndView("User/AllUsers");
			mav.addObject("userslist",this.userService.getAllUsers());
			return mav ; 
		}
	
	//get user info //
	@RequestMapping(method = RequestMethod.GET , value ="/adminstration/users/user")
	public ModelAndView getUser(@ModelAttribute User user ) {
		ModelAndView mav = new ModelAndView("adminstration/users/user");
		mav.addObject("user",this.userService.getUserByID(user.getUserID()));
		mav.addObject("userroleslist",this.userRoleService.getRolesOfUsers(user));
		mav.addObject("userpermissionslist",this.userPermissionsService.getPermissionsOfUser(user));	
		return mav ;
		}
	
	///add new user ///
	@RequestMapping(method = RequestMethod.GET , value="/adminstration/users/adduser")
	public ModelAndView addUserRequest() {
		ModelAndView mav = new ModelAndView("User/AddUser");
		mav.addObject("user",new User());
		return mav; 
	}
	
	//add user .//
	@RequestMapping(method = RequestMethod.POST , value="/adminstration/users/adduser")
	public void addNewUser(@ModelAttribute User user,HttpServletResponse response) throws IOException {
		System.out.println("posted to / admistration/users/addUser ");
		System.out.println("with info : ");
		user.flatUserDetailes();
		this.userService.addUser(user);
		response.sendRedirect("/adminstration/users/all");
	}
	
	
	///update user ///
	@RequestMapping(method = RequestMethod.GET , value="/adminstration/users/update")
	public ModelAndView updateUserRequest(@ModelAttribute User modelUser ) {
		ModelAndView mav = new ModelAndView("adminstration/users/update");
		Optional<User> user = this.userService.getUserByID(modelUser.getUserID());
		mav.addObject("user",user);
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.PUT , value="/adminstration/users/update")
	public void updateUser(@ModelAttribute User user,HttpServletResponse response ) throws IOException {
		this.userService.updateUser(user);
		response.sendRedirect("/adminstration/users/all");
	}
	
	
	///delete User ///
	///refactor user deletion // 
	@Transactional
	@RequestMapping(method = RequestMethod.DELETE , value="/adminstration/users/delete")
	public void deleteUser(@ModelAttribute User user ,HttpServletResponse response) throws IOException {
		this.userService.deleteUser(user);
		response.sendRedirect("/adminstration/users/all");
	}
	
	
	//Access Control Methods 
	/*
	 * get the current user permissions 
	 * get all permissions 
	 * get the permissions that the user does not have 
	 */
	@RequestMapping(method=RequestMethod.GET , value = "/admistration/users/user/permissions/grant")
	public ModelAndView grantPermissionsToUserRequest(@ModelAttribute User user ) {
		ModelAndView mav = new ModelAndView("users/user/grantpermissions");
		mav.addObject("user",user);
		List<Permissions>userPermissionsList = this.userPermissionsService.getPermissionsOfUser(user);
		List<Permissions> allPermissionsList =  this.permissionsService.getAllPermissions() ; 
		List<Permissions> uniquePermissionsList = new ArrayList<Permissions>() ; 
		for(Permissions permission : allPermissionsList ) {
			if(!userPermissionsList.contains(permission))
				uniquePermissionsList.add(permission);
		}
		mav.addObject("permissionslist",uniquePermissionsList);
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/admistration/users/user/permissions/grant")
	public ModelAndView grantPermissionsToUser(@ModelAttribute List<Permissions> userPermissions ,@ModelAttribute User user ) {
		this.userPermissionsService.grantPermissionsToUser(userPermissions, user);
		this.userService.addPermissionsToUser(user, userPermissions);
		ModelAndView mav = new ModelAndView("adminstration/users/user");
		mav.addObject("user",this.userService.getUserByID(user.getUserID()));
		mav.addObject("userroleslist",this.userRoleService.getRolesOfUsers(user));
		mav.addObject("userpermissionslist",this.userPermissionsService.getPermissionsOfUser(user));	
		return mav ;
	}
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/admistration/users/user/roles/grant")
	public ModelAndView grantRoleToUserRequest(@ModelAttribute User user ) {
		ModelAndView mav = new ModelAndView("users/user/grantroles");
		mav.addObject("user",user);
		List<Roles> allRoles = this.rolesService.getAllRoles() ; 
		List<Roles> currentUserRoles = this.userRoleService.getRolesOfUsers(user); 
		List<Roles> uniqueRoles = new ArrayList<Roles>() ; 
		for(Roles role : allRoles ) {
			if(!currentUserRoles.contains(role)) {
				uniqueRoles.add(role); 
			}
		}
		mav.addObject("roles",uniqueRoles);
		return mav ; 
	} 
	
	@RequestMapping(method = RequestMethod.POST , value = "/adminstration/users/user/roles/grant")
	public ModelAndView grantRolesToUser(@ModelAttribute List<Roles> rolesList ,@ModelAttribute User user ) {
		this.userRoleService.grantRoleToUser(rolesList, user);
		ModelAndView mav = new ModelAndView("adminstration/users/user");
		mav.addObject("user",this.userService.getUserByID(user.getUserID()));
		mav.addObject("userroleslist",this.userRoleService.getRolesOfUsers(user));
		mav.addObject("userpermissionslist",this.userPermissionsService.getPermissionsOfUser(user));	
		return mav ;
	}
	

	
	@RequestMapping(method = RequestMethod.GET , value = "/admistration/users/user/roles/revoke" )
	public ModelAndView revokeRoleFromUser(@ModelAttribute User user ) {
		ModelAndView mav = new ModelAndView("admistration/users/revokeroles");
		List<Roles> userRolesList = this.userRoleService.getRolesOfUsers(user); 
		mav.addObject("userroleslist",userRolesList);
		return mav; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/admistration/users/user/roles/revoke")
	public ModelAndView revokeRolesFromUser(@ModelAttribute User user , @ModelAttribute List<Roles>roles) {
		//revoke roles 
		return this.returnUserView(user);
	}
	
	//@RequestMapping(method = RequestMethod.GET , value = "/admistration/users/user/permissions/revoke" )
	
	public ModelAndView returnUserView(User user ) {
		ModelAndView mav = new ModelAndView("adminstration/users/user");
		mav.addObject("user",this.userService.getUserByID(user.getUserID()));
		mav.addObject("userroleslist",this.userRoleService.getRolesOfUsers(user));
		mav.addObject("userpermissionslist",this.userPermissionsService.getPermissionsOfUser(user));	
		return mav ; 
	}
	
	
	
}