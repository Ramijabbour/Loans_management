package com.example.Notifications;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	
	
	public boolean containRolesList(String roles ){
		System.out.println("roles string ; "+roles );
		List<String>rolesList = splitWithComma(roles);
		System.out.println("roles list size "+rolesList.size());
		for(String temp : rolesList) {
			System.out.println("roles list : "+temp);
		}
		
		List<String>targetedRolesList = splitWithComma(this.TargetedRoles);
		System.out.println("size : "+targetedRolesList.size());
		for(String s : targetedRolesList) {
			System.out.println("string "+s );
		}
		for(String roleItem : rolesList ) {
			if(targetedRolesList.contains(roleItem)) {
				System.out.println("condition");
				return true ;  
			}
		}
		return false ; 
	}
	 
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
	
	public List<String> splitWithComma(String splitTarget){
		String tempString ="";
		List<String> result = new ArrayList<String>(); 
		for(int i = 0 ; i<splitTarget.length() ; i++) {
			if(splitTarget.charAt(i) !=',') {
				if(splitTarget.charAt(i) ==' ') {
					continue ;
				}
				tempString += splitTarget.charAt(i); 
			}else {
				result.add(tempString);
				tempString ="" ; 
			}
		}
		result.add(tempString);
		return result ; 
	}
	
}