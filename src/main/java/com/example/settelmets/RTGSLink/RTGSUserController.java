package com.example.settelmets.RTGSLink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

	@RequestMapping(method = RequestMethod.GET , value = "/settlements/addRTAdmin")
	public String addRTGSAdmin() {
		this.rtgsUserService.addRTGSAdmin();
		return "ok";
	}
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/settlements/users/editRTUser/{userid}")
	public ModelAndView editRTGSUser(@PathVariable int userid) {
		RTGSUser user = this.rtgsUserService.getUserById(userid);
		if(user == null ) {
			return MasterService.sendGeneralError("لم يتم العثور على المستخدم المطلوب");
		}
		ModelAndView mav = new ModelAndView("settlement/edit");
		mav.addObject("user",user);
		return mav ; 
	}
	
	
	@RequestMapping(method = RequestMethod.POST , value = "/settlements/users/editRTUser")
	public ModelAndView editRTGSUser(@ModelAttribute RTGSUser user) {
		String result =  this.rtgsUserService.updateRTUser(user) ; 
		if(result.equalsIgnoreCase("ok")) {
			return MasterService.sendSuccessMsg("تم تعديل  المستخدم بنجاح");
		}else {
			return MasterService.sendGeneralError(result);
		}
	}
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/settlements/users/changeState/{userid}")
	public ModelAndView changeUserState(@PathVariable int userid) {
		RTGSUser user = this.rtgsUserService.getUserById(userid);
		if(user == null ) {
			return MasterService.sendGeneralError("لم يتم العثور على المستخدم المطلوب");
		}
		if(user.isActive()) {
			this.rtgsUserService.setUserState(false,userid);
			return MasterService.sendSuccessMsg("تم تعطيل  المستخدم بنجاح");
		}else {
			this.rtgsUserService.setUserState(true,userid);
			return MasterService.sendSuccessMsg("تم تفعيل  المستخدم بنجاح");
		}
	}
	
	
	
	
	
}
