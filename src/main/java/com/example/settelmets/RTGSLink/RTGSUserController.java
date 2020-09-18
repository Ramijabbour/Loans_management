package com.example.settelmets.RTGSLink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.SiteConfig.MasterService;

@RestController
public class RTGSUserController {

	
	@Autowired
	private RTGSUserService rtgsUserService; 
	
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/settlements/users/addrtUser")
	public ModelAndView addRTGSUser() {
		ModelAndView mav = new ModelAndView("settlement/addRTGSUser");
		mav.addObject("user",new RTGSUser());
		return mav ; 
	}
	
	
	@RequestMapping(method = RequestMethod.POST , value = "/settlements/users/addrtUser")
	public ModelAndView addRtUser(@ModelAttribute RTGSUser user) {
		String result =  this.rtgsUserService.addRTUser(user) ; 
		if(result.equalsIgnoreCase("ok")) {
		return MasterService.sendSuccessMsg("تمت إضافة المستخدم بنجاح");
		}else {
			return MasterService.sendGeneralError(result);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/settlements/users/allrtUsers")
	public ModelAndView getAllrtUsers() {
		ModelAndView mav = new ModelAndView("settlement/allrtusers");
		mav.addObject("userslist",this.rtgsUserService.getallUsers() );
		return mav ; 
	}

}
