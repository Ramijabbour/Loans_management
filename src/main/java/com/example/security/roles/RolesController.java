package com.example.security.roles;

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
	
	
	public RolesController() {
	Method[] methods =  this.getClass().getDeclaredMethods();
	List<String> methodsNames = new ArrayList<String>(); 
	for(Method method : methods) {
		if(!methodsNames.contains(method.getName()))
			methodsNames.add(method.getName());
	}
	PermissionsService.addPermissionsToPermissionsList(methodsNames);
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/security/roles/all")
	public ModelAndView viewAllRoles(@Param(value ="index") int index) {
		ModelAndView mav = new ModelAndView("Roles/allRoles");
		List<Roles> rolesList =  this.rolesService.getAllRoles(index); 
		mav.addObject("roleslist",rolesList);
		if(rolesList.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav ; 
		
	}
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/security/roles/viewrole/{roleId}")
	public ModelAndView viewSingleRole(@PathVariable int roleId) {
		ModelAndView mav = new ModelAndView("Roles/manageRole");
		Roles role = this.rolesService.getRoleByID(roleId); 
		mav.addObject("role",role);
		mav.addObject("permissionslist",this.rolesPermissionsService.getPermissionsOfRole(role));
		mav.addObject("userslist",this.userRolesService.getUsersWithRole(role));
		return mav ; 
	}

	
	@RequestMapping(method = RequestMethod.GET ,value = "/security/roles/addrole")
	public ModelAndView addRole() {
		ModelAndView mav = new ModelAndView("Roles/addRole");
		mav.addObject("roleobject",new Roles());
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/security/role/addrole")
	@Transactional
	public ModelAndView addRole(@ModelAttribute Roles role) throws IOException {
		if(role.getRoleName().contains("admin")) {
			return MasterService.sendGeneralError("لا يمكن إضافة دور بهذا الاسم");
		}
		if(role.getRoleName().contains("super")) {
			return MasterService.sendGeneralError("لا يمكن إضافة دور بهذا الاسم");
		}
		if(role.getRoleName().contains("ANALYTICS")) {
			return MasterService.sendGeneralError("لا يمكن إضافة دور بهذا الاسم");
		}
		if(role.getRoleName().contains("ALLANALYTICS")) {
			return MasterService.sendGeneralError("لا يمكن إضافة دور بهذا الاسم");
		}
		this.rolesService.addRole(role); 
		return MasterService.sendSuccessMsg("تمت عملية إضافة الدور بنجاح");
	} 
	
	@RequestMapping(method = RequestMethod.POST , value = "/security/roles/delete/{roleid}")
	public ModelAndView deleteRole(@PathVariable int roleid, HttpServletResponse response ) throws IOException {
		Roles role = this.rolesService.getRoleByID(roleid);
		if(role == null ) {
			return MasterService.sendGeneralError("لا يمكن العثور على هذا الدور");
		}else {
			if(role.getRoleName().equalsIgnoreCase("SUPER")) {
				return MasterService.sendGeneralError("لا يمكن حذف هذا الدور");
			}
			if(role.getRoleName().equalsIgnoreCase("ADMIN")) {
				return MasterService.sendGeneralError("لا يمكن حذف هذا الدور");
			}
			if(role.getRoleName().equalsIgnoreCase("ANALYTICS")) {
				return MasterService.sendGeneralError("لا يمكن حذف هذا الدور");
			}
			if(role.getRoleName().equalsIgnoreCase("ALLANALYTICS")) {
				return MasterService.sendGeneralError("لا يمكن حذف هذا الدور");
			}
		this.rolesService.deleteRole(role);
		return MasterService.sendSuccessMsg("تم حذف الدور بنجاح");
		}
	}

	
	@RequestMapping(method = RequestMethod.POST , value = "/security/roles/role/permissions/revoke/{roleid}/{permissionid}")
	public void revokePermissionFromRole(@PathVariable int roleid , @PathVariable int permissionid , HttpServletResponse response  ) throws IOException{
		Roles role = this.rolesService.getRoleByID(roleid);
		Permissions permission = this.permissionsService.getPermissionById(permissionid); 
		this.rolesPermissionsService.revokePermissionFromRole(role, permission);
		String redirectPath = "/security/roles/viewrole/"+role.getId();
		response.sendRedirect(redirectPath);
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
	@RequestMapping(method = RequestMethod.GET , value = "/security/roles/role/permissions/grant/{roleid}")
	public ModelAndView grantpermissionToRole(@PathVariable int roleid) {
		Roles role = this.rolesService.getRoleByID(roleid);
		ModelAndView mav = new ModelAndView("Permissions/grantpermissiontorole");
		List<Permissions> allPermissions = this.permissionsService.getAllPermissions(0);
		List<Permissions> currentPermissions =  this.rolesPermissionsService.getPermissionsOfRole(role);
		List<Permissions> uniquePermissions = new ArrayList<Permissions>();
		for(Permissions permission : allPermissions ) {
			if(!currentPermissions.contains(permission)) {
				uniquePermissions.add(permission);
			}
		}
		mav.addObject("role",role);
		mav.addObject("permissionsList",uniquePermissions);
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/security/roles/role/permissions/grant/{roleid}/{permissionid}")
	public void grantPermission(@PathVariable int roleid , @PathVariable int permissionid , HttpServletResponse response) throws IOException {
		Roles role = this.rolesService.getRoleByID(roleid);
		Permissions permission = this.permissionsService.getPermissionById(permissionid);
		this.rolesService.grantPermissionsToRole(permission, role);
		String redirectPath = "/security/roles/role/permissions/grant/"+roleid;
		response.sendRedirect(redirectPath);
	}
	
	//grant permissions end 
	
	
}
