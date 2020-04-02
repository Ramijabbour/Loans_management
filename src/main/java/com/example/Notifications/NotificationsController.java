package com.example.Notifications;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class NotificationsController {

	@Autowired
	private NotificationsService notificationsService ; 
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/notifications/all")
	public ModelAndView getAllNotifications() {
		ModelAndView mav = new ModelAndView("Notifications/allNotifications");
		mav.addObject("notificationslist",this.notificationsService.getRoleAllNotifications());
		return mav ;  
	}
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/notifications/new")
	public ModelAndView getNewNotifications() {
		ModelAndView mav = new ModelAndView("Notifications/newNotifications");
		mav.addObject("notificationslist",this.notificationsService.getRoleNewNotifications());
		return mav ;  
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/notifications/redirect/{id}")
	public void redirectToNotification(HttpServletResponse response ,@PathVariable int id) throws IOException {
		Notification notification = this.notificationsService.getNotificationsByID(id);
		String redirectPath = notification.getLink() ; 
		response.sendRedirect(redirectPath);
	}
	
	
}
