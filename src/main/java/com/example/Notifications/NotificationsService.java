package com.example.Notifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MasterService;

@Service
public class NotificationsService extends MasterService{
	
	@Autowired
	private NotificationsRepository notificationsRepository ; 
			
	public void addNotification(String title , String link,String rolesList){
		Notification notification = new Notification(title,link,rolesList);
		this.notificationsRepository.save(notification); 
	}
	
	public List<Notification> getRoleNewNotifications(){
		if(this.get_current_User() == null ) {
			return new ArrayList<Notification>();
		}
		List<Notification> roleNotificationsList = new ArrayList<Notification>(); 
		List<Notification> allNotifications = this.notificationsRepository.findAll() ; 
		for(Notification notification : allNotifications) {
			if(notification.isStatus()) {
				continue ; 
			}
			if(notification.containRolesList(super.get_current_User().getUserRoles())) {
				notification.setStatus(true);
				this.notificationsRepository.save(notification);
				roleNotificationsList.add(notification);
			}
		}	
		return roleNotificationsList ; 
	}
	
	public List<Notification> getRoleAllNotifications(){
		if(this.get_current_User() == null ) {
			return new ArrayList<Notification>();
		}
		List<Notification> roleNotificationsList = new ArrayList<Notification>();
		List<Notification> allNotifications = this.notificationsRepository.findAll() ; 
		String userRoles = super.get_current_User().getUserRoles() ; 
		for(Notification notification : allNotifications) {
			if(notification.containRolesList(userRoles)) {
				roleNotificationsList.add(notification);
			}
		}	
		return roleNotificationsList ; 
	}

	public Notification getNotificationsByID(int id) {
		List<Notification> all = this.notificationsRepository.findAll(); 
		for(Notification notification : all ) {
			if(notification.getId() == id ) {
				return notification ; 
			}
		}
		return null;
	}

	
}
