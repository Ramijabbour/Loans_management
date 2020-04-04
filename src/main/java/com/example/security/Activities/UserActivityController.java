package com.example.security.Activities;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserActivityController {

	
	@Autowired 
	private ActivityService activityService ; 
	
	@RequestMapping(method = RequestMethod.GET , value = "/adminstration/logs/all")
	public ModelAndView getAllLogs() {
		ModelAndView mav = new ModelAndView("Logs/all");
		mav.addObject("logList",this.activityService.getActivityLog());
		return mav ; 
		}
	
	@RequestMapping(method = RequestMethod.GET,value = "/adminstration/logs/userlogs/{userId}")
	public ModelAndView getUserLog(@PathVariable int userId ) {
		ModelAndView mav = new ModelAndView("Logs/user");
		mav.addObject("userLogs",this.activityService.getUserActivity(userId));
		return mav ; 
	}
	
	
	@RequestMapping(method = RequestMethod.GET , value ="/adminstration/logs/archive/commit")
	public void archiveLogs(HttpServletResponse respons ) throws IOException{
		String redirectPath = "/adminstration/logs/all" ;
		this.activityService.listLogs(); 
		respons.sendRedirect(redirectPath);
	}
	
	
}
