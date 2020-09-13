package com.example.security.permissions;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.SiteConfig.MasterService;
import com.example.SiteConfig.SiteConfiguration;

@RestController
public class PermissionsController {
	
	@Autowired 
	private PermissionsService permissionsService ;
	
	public PermissionsController() {
		Method[] methods =  this.getClass().getDeclaredMethods();
		List<String> methodsNames = new ArrayList<String>(); 
		for(Method method : methods) {
			if(!methodsNames.contains(method.getName()))
				methodsNames.add(method.getName());
		}
		PermissionsService.addPermissionsToPermissionsList(methodsNames);
	}
	
	@RequestMapping(method = RequestMethod.GET ,value = "/security/permissions/commit")
	public ModelAndView commitPermissions() {
		permissionsService.commitPermissionsInjection();
		return MasterService.sendSuccessMsg("تمت عملية إدخال الصلاحيات بنجاح");
	}
	
	
	@RequestMapping(method = RequestMethod.GET ,value = "/security/permissions/all")
	public ModelAndView getAllPermissions(@Param(value ="index") int index) {
		ModelAndView mav = new ModelAndView("Permissions/allPermissions");
		List<Permissions> permissionsList =  permissionsService.getAllPermissions(index) ; 
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
		Permissions permission = permissionsService.getPermissionById(permissionId);
		if(permission == null ) {
			//replace with error msg later 
			response.sendRedirect("/security/permissions/all");
		}
		mav.addObject("permission",permission);
		mav.addObject("rolesList",permissionsService.getRolesWithPermission(permission));
		mav.addObject("userslist",permissionsService.getUsersWithPermission(permission));
		return mav ; 
	}
	
	
	
	@RequestMapping(method = RequestMethod.POST , value = "/Permissions/Search")
	public ModelAndView getAllChecksbyCheckid(@Param(value ="index") int index,@RequestParam("search") String permissionName) {
		ModelAndView mav = new ModelAndView("Permissions/searchPermissions");
		List<Permissions> allPermissions = permissionsService.SearchbyPermissionkName(index,permissionName);
		mav.addObject("allpermissions",allPermissions);
		mav.addObject("searchvar",permissionName);
		if(allPermissions.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav ;
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/Permissions/Search/nxtRes/{index}/{searchvar}")
	public ModelAndView searchBankNext(@PathVariable int index,@PathVariable String searchvar ) {
		ModelAndView mav = new ModelAndView("Permissions/searchPermissions");
		List<Permissions> allPermissions = permissionsService.SearchbyPermissionkName(index,searchvar);
		mav.addObject("allpermissions",allPermissions);
		mav.addObject("searchvar",searchvar);
		if(allPermissions.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav ;
	}
	
	
	
}
