package com.example.security.user;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.SiteConfig.MasterService;
import com.example.SiteConfig.SiteConfiguration;
import com.example.security.Dispatcher.ServiceDispatcher;
import com.example.security.permissions.Permissions;
import com.example.security.roles.Roles;


@RestController
public class UserController {

	@Autowired
	UserService userService ; 
	
	@Autowired 
	ServiceDispatcher dispatcher ;
	

	public UserController() { 
		Method[] methods =  this.getClass().getDeclaredMethods();
		List<String> methodsNames = new ArrayList<String>(); 
		for(Method method : methods) {
			if(!methodsNames.contains(method.getName()))
				{
					methodsNames.add(method.getName());
				}
		}
		methodsNames.add(this.getClass().getSimpleName());
		MasterService.addPermissionsToPermissionService(methodsNames);
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
	public ModelAndView addUser() {
		ModelAndView mav = new ModelAndView("User/AddUser");
		mav.addObject("user",new User());
		return mav; 
	}
	
	//add user .//
	@RequestMapping(method = RequestMethod.POST , value="/adminstration/users/adduser")
	public ModelAndView addUser(@ModelAttribute User user)  {
		String response = this.userService.addUser(user); 
		if(response.equalsIgnoreCase("ok")) {
			return MasterService.sendSuccessMsg("تمت عملية إضافة مستخدم بنجاح"); 
		}else {
			return MasterService.sendGeneralError(response); 
		}
	}
	
	
	///update user ///	
	@RequestMapping(method = RequestMethod.GET , value="/adminstration/users/update/{id}")
	public ModelAndView updateUser(@PathVariable int id ) throws IOException {
		ModelAndView mav = new ModelAndView("User/update");
		User user = this.userService.getUserByID(id);
		mav.addObject("user",user);
		return mav ; 
	
	}
	
	@RequestMapping(method = RequestMethod.POST , value="/adminstration/users/update")
	public ModelAndView updateUser(@ModelAttribute User user) {
		String response = this.userService.updateUser(user);
		if(response.equalsIgnoreCase("ok")) {
			return MasterService.sendSuccessMsg("تم تعديل المستخدم بنجاح"); 
		}else {
			return MasterService.sendGeneralError(response);
		}
	}
	
	
	///delete User ///
	@Transactional
	@RequestMapping(method = RequestMethod.POST , value="/adminstration/users/delete/{userid}")
	public ModelAndView deleteUser(@PathVariable int userid){
		this.userService.deleteUser(this.userService.getUserByID(userid));
		return MasterService.sendSuccessMsg("تم حذف المستخدم بنجاح");
	}
	
	
	//Access Control Methods
	
	@RequestMapping(method = RequestMethod.GET , value = "/admistration/users/user/viewuser/{userid}")
	public ModelAndView viewUser(@PathVariable int userid ) {
		ModelAndView mav = new ModelAndView("User/viewUser");
		mav.addObject("userRoles",dispatcher.getUserRolesService().getRolesOfUsers(this.userService.getUserByID(userid)));
		mav.addObject("userPermissions",dispatcher.getUserPermissionsService().getPermissionsOfUser(this.userService.getUserByID(userid)));
		mav.addObject("user",this.userService.getUserByID(userid));
		return mav ;  
	}
	
	
	/*
	 * get the current user permissions 
	 * get all permissions 
	 * get the permissions that the user does not have 
	 */
	@RequestMapping(method=RequestMethod.GET , value = "/admistration/users/user/permissions/grant/{userid}")
	public ModelAndView grantPermissionsToUser(@PathVariable int userid) {
		User user = this.userService.getUserByID(userid);
		ModelAndView mav = new ModelAndView("Permissions/grantpermissions");
		List<Permissions>userPermissionsList = dispatcher.getUserPermissionsService().getPermissionsOfUser(user);
		List<Permissions> allPermissionsList =  dispatcher.getPermissionsService().getAllPermissionsNoPage() ; 
		List<Permissions> uniquePermissionsList = new ArrayList<Permissions>() ; 
		for(Permissions permission : allPermissionsList ) {
			if(!userPermissionsList.contains(permission))
				uniquePermissionsList.add(permission);
		}
		mav.addObject("user",user);
		mav.addObject("permissionslist",uniquePermissionsList);
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/admistration/users/user/permissions/grant/{userid}/{permissionsid}")
	public void grantPermissionsToUser(@PathVariable int userid,@PathVariable int permissionsid, HttpServletResponse response ) throws IOException {
		User user = this.userService.getUserByID(userid);
		Permissions permission = dispatcher.getPermissionsService().getPermissionById(permissionsid);
		dispatcher.getUserPermissionsService().grantPermissionsToUser(permission, user);
		String redirectPath = "/admistration/users/user/permissions/grant/"+user.getId();
		response.sendRedirect(redirectPath);
	}
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/admistration/users/user/roles/grant/{userid}")
	public ModelAndView grantRoleToUser(@PathVariable int userid) {
		User user = this.userService.getUserByID(userid);
		ModelAndView mav = new ModelAndView("Roles/grantroles");
		List<Roles> allRoles = dispatcher.getRolesService().getAllRoles(0) ; 
		List<Roles> currentUserRoles = dispatcher.getUserRolesService().getRolesOfUsers(user); 
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
	public void grantRoleToUser(@PathVariable int roleid ,@PathVariable int userid,HttpServletResponse response  ) throws IOException {
		Roles role = dispatcher.getRolesService().getRoleByID(roleid);
		User user = this.userService.getUserByID(userid);
		dispatcher.getUserRolesService().grantRoleToUser(role, user);
		String redirectPath = "/admistration/users/user/roles/grant/"+user.getId();
		response.sendRedirect(redirectPath);
	}
	

	
	@RequestMapping(method = RequestMethod.POST , value = "/admistration/users/user/roles/revoke/{userid}/{roleid}" )
	public void revokeRoleFromUser(@PathVariable int userid , @PathVariable int roleid , HttpServletResponse response)throws IOException {
		User user = this.userService.getUserByID(userid);
		Roles role = dispatcher.getRolesService().getRoleByID(roleid);
		dispatcher.getUserRolesService().revokeRoleFromUser(user, role);
		String redirectPath = "/admistration/users/user/viewuser/"+user.getId(); 
		response.sendRedirect(redirectPath);
	}
	
	
	@RequestMapping(method = RequestMethod.POST , value = "/admistration/users/user/permissions/revoke/{userid}/{permissionid}" )
	public void revokePermissionFromUser(@PathVariable int userid , @PathVariable int permissionid , HttpServletResponse response)throws IOException {
		User user = this.userService.getUserByID(userid);
		Permissions permission = dispatcher.getPermissionsService().getPermissionById(permissionid);
		dispatcher.getUserPermissionsService().revokePermissionFromUser(user, permission);
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
	
}