package com.example.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.SiteConfig.MasterService;
import com.example.security.Dispatcher.ServiceDispatcher;

@RestController
public class ProfileController {

	@Autowired 
	ProfileService profileService ; 

	@Autowired 
	ServiceDispatcher dispatcher ;
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/profile")
	public ModelAndView getUserProfile() {
		ModelAndView mav = new ModelAndView("User/profile");
		User currUser = this.profileService.getCurrUser() ; 
		if(currUser == null ) {
			return MasterService.sendGeneralError("لا يمكن الوصول إلى هذه الصفحة");
		}else {
			mav.addObject("userRoles",dispatcher.getUserRolesService().getRolesOfUsers(currUser));
			mav.addObject("userPermissions",dispatcher.getUserPermissionsService().getPermissionsOfUser(currUser));
			mav.addObject("user",currUser); 
			return mav;
		}
	}
	

	@RequestMapping(method = RequestMethod.GET , value = "/profile/edit")
	public ModelAndView editProfile() {
		ModelAndView mav = new ModelAndView("User/editProfile");
		User currUser = this.profileService.getCurrUser() ; 
		if(currUser == null ) {
			return MasterService.sendGeneralError("لا يمكن الوصول إلى هذه الصفحة");
		}else {
			mav.addObject("user",currUser); 
			return mav; 
		}
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/profile/edit")
	public ModelAndView editProfile(@ModelAttribute User user) {
		User currUser = this.profileService.getCurrUser() ; 
		if(currUser == null ) {
			return MasterService.sendGeneralError("لا يمكن الوصول إلى هذه الصفحة");
		}else if (currUser.getId() != user.getId()) {
			return MasterService.sendGeneralError("لا يمكن تعديل هذا الحساب ");
		}
		String result = dispatcher.getUserService().updateUser(user);
		if(result.equalsIgnoreCase("ok"))
			return getUserProfile();
		else 
			return MasterService.sendGeneralError(result); 
	}
	
		
	
	
}
