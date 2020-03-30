package com.example.Notifications;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.security.roles.Roles;

@Entity
public class Notification {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int NotificationId;
	private String NotificationTitle ; 
	private String Link ; 
	private String TargetedRoles ="";
	private boolean Status = false ; 
	
	public void setNotificationRoles(String roles ) {
		this.TargetedRoles = roles ; 
	}
	
	//major runtime issue 
	public boolean containsRole(Roles role ) {
		String[] roles = this.TargetedRoles.split(","); 
		for(int i = 0 ; i < roles.length  ; i ++) {
			
			if(roles[i].equalsIgnoreCase(role.getRoleName())) {
				return true ; 
			}
		}
		return false;  
	}
	
	public Notification() {
		super();
	}
	
	public Notification(String notificationTitle, String link, String targetedRoles) {
		super();
		NotificationTitle = notificationTitle;
		Link = link;
		this.setNotificationRoles(targetedRoles);
	}

	public int getNotificationId() {
		return NotificationId;
	}
	
	public String getNotificationTitle() {
		return NotificationTitle;
	}
	
	public void setNotificationTitle(String notificationTitle) {
		NotificationTitle = notificationTitle;
	}
	
	public String getLink() {

		return Link;
	}
	
	public void setLink(String link) {
		Link = link;
	}
	
	public String getTargetedRoles() {
		return TargetedRoles;
	}
	
	public void setTargetedRoles(String targetedRoles) {
		TargetedRoles = targetedRoles;
	}

	
	public boolean isStatus() {
		return Status;
	}

	
	public void setStatus(boolean status) {
		Status = status;
	} 
	
	
	
}