package com.example.Notifications;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.MasterService;
import com.example.aspect.EncryptDecrypt.IntEncryptDecryptConverter;
import com.example.aspect.EncryptDecrypt.StringEncryptDecryptConverter;
import com.example.security.roles.Roles;

@Entity
public class Notification {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Convert(converter = IntEncryptDecryptConverter.class)
    public int NotificationId;
	
	@Convert(converter = StringEncryptDecryptConverter.class)
	private String NotificationTitle ; 
	
	@Convert(converter = StringEncryptDecryptConverter.class)	
	private String Link ; 
	
	@Convert(converter = StringEncryptDecryptConverter.class)
	private String TargetedRoles ="";
	
	private boolean Status = false ; 
	private LocalDateTime localDateTime ; 
	
	public void setNotificationRoles(String roles ) {
		this.TargetedRoles = roles ; 
		
	}
	
	
	
	public boolean containRolesList(String roles ){
		List<String>rolesList = splitWithComma(roles);	
		List<String>targetedRolesList = splitWithComma(this.TargetedRoles);
		for(String roleItem : rolesList ) {
			if(targetedRolesList.contains(roleItem)) {
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
		this.localDateTime = MasterService.getCurrDateTime() ;
	}
	
	public Notification(String notificationTitle, String link, String targetedRoles) {
		super();
		NotificationTitle = notificationTitle;
		Link = link;
		this.setNotificationRoles(targetedRoles);
		this.localDateTime = MasterService.getCurrDateTime() ;
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



	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}



	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}
	
	
	
}