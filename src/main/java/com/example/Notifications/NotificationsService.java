package com.example.Notifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.roles.Roles;

@Service
public class NotificationsService {
	
	@Autowired
	private NotificationsRepository notificationsRepository ; 
	
	public void addNotification(String title , String link,String rolesList){
		Notification notification = new Notification(title,link,rolesList);
		this.notificationsRepository.save(notification); 
	}
	
	public List<Notification> getRoleNewNotifications(Roles role){
		List<Notification> roleNotificationsList = new ArrayList<Notification>(); 
		for(Notification notification : roleNotificationsList) {
			if(notification.isStatus()) {
				continue ; 
			}
			if(notification.containsRole(role)) {
				notification.setStatus(true);
				this.notificationsRepository.save(notification);
				roleNotificationsList.add(notification);
			}
		}	
		return roleNotificationsList ; 
	}
	
	public List<Notification> getRoleAllNotifications(Roles role){
		List<Notification> roleNotificationsList = new ArrayList<Notification>(); 
		for(Notification notification : roleNotificationsList) {
			if(notification.containsRole(role)) {
				roleNotificationsList.add(notification);
			}
		}	
		return roleNotificationsList ; 
	}
	
	
	
}
