package com.example.security.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.SiteConfiguration;
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
		ModelAndView mav = new ModelAndView("User/index");
		return mav ; 
	}
	
	///all users ///
	@RequestMapping(method = RequestMethod.GET , value = "/adminstration/users/all")
	public ModelAndView getAllUsers(@Param(value ="index") int index) {  
		ModelAndView mav = new ModelAndView("User/AllUsers");
		List<User> usersList =  this.userService.getAllUsers(index) ; 
		mav.addObject("userslist",usersList);
		if(usersList.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
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
	public ModelAndView addNewUser(@ModelAttribute User user)  {
		String response = this.userService.addUser(user); 
		if(response.equalsIgnoreCase("ok")) {
			return this.getAllUsers(0); 
		}else {
			ModelAndView mav = new ModelAndView("Errors/userError");
			mav.addObject("msg",response);
			return mav ; 
		}
	}
	
	
	///update user ///	
	@RequestMapping(method = RequestMethod.GET , value="/adminstration/users/update/{id}")
	public ModelAndView updateUserRequest(@PathVariable int id ) throws IOException {
		ModelAndView mav = new ModelAndView("User/update");
		User user = this.userService.getUserByID(id);
		mav.addObject("user",user);
		return mav ; 
	
	}
	
	@RequestMapping(method = RequestMethod.POST , value="/adminstration/users/update")
	public ModelAndView updateUser(@ModelAttribute User user) {
		String response = this.userService.updateUser(user);
		if(response.equalsIgnoreCase("ok")) {
			return this.getAllUsers(0) ; 
		}else {
		ModelAndView mav = new ModelAndView("Errors/userError");
		mav.addObject("msg",response);
		return mav ; 
		}
	}
	
	
	///delete User ///
	@Transactional
	@RequestMapping(method = RequestMethod.POST , value="/adminstration/users/delete/{userid}")
	public ModelAndView deleteUser(@PathVariable int userid){
		this.userService.deleteUser(this.userService.getUserByID(userid));
		return this.getAllUsers(0);
	}
	
	
	//Access Control Methods
	
	@RequestMapping(method = RequestMethod.GET , value = "/admistration/users/user/viewuser/{userid}")
	public ModelAndView viewUser(@PathVariable int userid ) {
		ModelAndView mav = new ModelAndView("User/viewUser");
		mav.addObject("userRoles",this.userRoleService.getRolesOfUsers(this.userService.getUserByID(userid)));
		mav.addObject("userPermissions",this.userPermissionsService.getPermissionsOfUser(this.userService.getUserByID(userid)));
		mav.addObject("user",this.userService.getUserByID(userid));
		return mav ;  
	}
	
	
	/*
	 * get the current user permissions 
	 * get all permissions 
	 * get the permissions that the user does not have 
	 */
	@RequestMapping(method=RequestMethod.GET , value = "/admistration/users/user/permissions/grant/{userid}")
	public ModelAndView grantPermissionsToUserRequest(@PathVariable int userid) {
		User user = this.userService.getUserByID(userid);
		ModelAndView mav = new ModelAndView("Permissions/grantpermissions");
		List<Permissions>userPermissionsList = this.userPermissionsService.getPermissionsOfUser(user);
		List<Permissions> allPermissionsList =  this.permissionsService.getAllPermissions(0) ; 
		List<Permissions> uniquePermissionsList = new ArrayList<Permissions>() ; 
		for(Permissions permission : allPermissionsList ) {
			if(!userPermissionsList.contains(permission))
				uniquePermissionsList.add(permission);
		}
		mav.addObject("user",user);
		System.out.println("permissions list size : "+uniquePermissionsList.size());
		mav.addObject("permissionslist",uniquePermissionsList);
		
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/admistration/users/user/permissions/grant/{userid}/{permissionsid}")
	public void grantPermissionsToUser(@PathVariable int userid,@PathVariable int permissionsid, HttpServletResponse response ) throws IOException {
		User user = this.userService.getUserByID(userid);
		Permissions permission = this.permissionsService.getPermissionById(permissionsid);
		this.userPermissionsService.grantPermissionsToUser(permission, user);
		String redirectPath = "/admistration/users/user/permissions/grant/"+user.getId();
		response.sendRedirect(redirectPath);
	}
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/admistration/users/user/roles/grant/{userid}")
	public ModelAndView grantRoleToUserRequest(@PathVariable int userid) {
		User user = this.userService.getUserByID(userid);
		ModelAndView mav = new ModelAndView("Roles/grantroles");
		List<Roles> allRoles = this.rolesService.getAllRoles(0) ; 
		List<Roles> currentUserRoles = this.userRoleService.getRolesOfUsers(user); 
		List<Roles> uniqueRoles = new ArrayList<Roles>() ; 
		for(Roles role : allRoles ) {
			if(!currentUserRoles.contains(role)) {
				uniqueRoles.add(role); 
			}
		}
		mav.addObject("user",user);
		mav.addObject("roles",uniqueRoles);
		return mav ; 
	} 
	
	@RequestMapping(method = RequestMethod.POST , value = "/adminstration/users/user/roles/grant/{userid}/{roleid}")
	public void grantRolesToUser(@PathVariable int roleid ,@PathVariable int userid,HttpServletResponse response  ) throws IOException {
		Roles role = this.rolesService.getRoleByID(roleid);
		User user = this.userService.getUserByID(userid);
		this.userRoleService.grantRoleToUser(role, user);
		String redirectPath = "/admistration/users/user/roles/grant/"+user.getId();
		response.sendRedirect(redirectPath);
	}
	

	
	@RequestMapping(method = RequestMethod.POST , value = "/admistration/users/user/roles/revoke/{userid}/{roleid}" )
	public void revokeRoleFromUser(@PathVariable int userid , @PathVariable int roleid , HttpServletResponse response)throws IOException {
		User user = this.userService.getUserByID(userid);
		Roles role = this.rolesService.getRoleByID(roleid);
		this.userRoleService.revokeRoleFromUser(user, role);
		String redirectPath = "/admistration/users/user/viewuser/"+user.getId(); 
		response.sendRedirect(redirectPath);
	}
	
	
	@RequestMapping(method = RequestMethod.POST , value = "/admistration/users/user/permissions/revoke/{userid}/{permissionid}" )
	public void revokePermissionFromUser(@PathVariable int userid , @PathVariable int permissionid , HttpServletResponse response)throws IOException {
		User user = this.userService.getUserByID(userid);
		Permissions permission = this.permissionsService.getPermissionById(permissionid);
		this.userPermissionsService.revokePermissionFromUser(user, permission);
		String redirectPath = "/admistration/users/user/viewuser/"+user.getId(); 
		response.sendRedirect(redirectPath);
	}
	

	@RequestMapping(method = RequestMethod.GET , value = "/adminstration/users/active")
	public ModelAndView getActiveUsers() {
		ModelAndView mav = new ModelAndView("User/activeUsers");
		mav.addObject("userslist",this.userService.getActiveUsers());
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/adminstration/users/user/deactivate/{userid}")
	public void deActivateUser(@PathVariable int userid , HttpServletResponse response) throws IOException {
		this.userService.deActivateUser(userid);
		response.sendRedirect("/adminstration/users/active");
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/adminstration/users/nonactive")
	public ModelAndView getNonActiveUsers() {
		ModelAndView mav = new ModelAndView("User/nonActiveUsers");
		mav.addObject("userslist",this.userService.getNonActiveUsers());
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/adminstration/users/user/activate/{userid}")
	public void activateUser(@PathVariable int userid , HttpServletResponse response) throws IOException {
		this.userService.activateUser(userid);
		response.sendRedirect("/adminstration/users/nonactive");
	}
	
	
	public ModelAndView returnUserView(User user ) {
		ModelAndView mav = new ModelAndView("adminstration/users/user");
		mav.addObject("user",this.userService.getUserByID(user.getId()));
		mav.addObject("userroleslist",this.userRoleService.getRolesOfUsers(user));
		mav.addObject("userpermissionslist",this.userPermissionsService.getPermissionsOfUser(user));	
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/testper")
	public void testper(){
		final StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		this.userService.getAllActuator();
		stopWatch.stop();
		System.out.println("runtime : "+stopWatch.getTotalTimeMillis() + "ms");
	}
	
}