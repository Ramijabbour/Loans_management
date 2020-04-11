package com.example;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.Notifications.NotificationsService;
import com.example.security.Activities.ActivityService;
import com.example.security.user.User;
import com.example.security.user.UserRepository;

@Service
public class MasterService {
	@Autowired
	protected NotificationsService notificationsService ; 
	
	@Autowired
	protected UserRepository userRepo; 
	
	@Autowired
	protected ActivityService activityService ; 
	
	protected User get_current_User() {
		String username ; 
    	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        Object principal =  auth.getPrincipal();
	        if(principal instanceof UserDetails) {
	        	 username = ((UserDetails) principal).getUsername() ; 
		         for(User user : this.userRepo.findAll()) {
		 			if(user.getUsername().equalsIgnoreCase(username)) {
		 				return user ; 
		 			}
		 		}
	        }
	         return null  ; 
    }
	
	public static LocalDateTime getCurrDateTime() {
		   //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		   LocalDateTime now = LocalDateTime.now();  
		   //System.out.println("current request time :"+dtf.format(now));
		   return now ; 
	}
	
	protected  Object encryptData(Object object) {
		Field[] fields = object.getClass().getDeclaredFields() ; 
		System.out.println("object flat initiated with fileds :");
		for(Field field : fields  ) {
			encryptType(field,object);
		}
		return object; 
	}
	
	protected  Object decryptData(Object object) {
		Field[] fields = object.getClass().getDeclaredFields() ; 
		System.out.println("object flat initiated with fileds :");
		for(Field field : fields  ) {
			decryptType(field,object);
		}
		return object; 
	}
	
	protected Field encryptType(Field field,Object object) {
		try {
		if (field.getGenericType().getTypeName().equalsIgnoreCase("int")) {
			field.setAccessible(true);
			//encrypt field  
			field.setAccessible(false);
		}else if(field.getGenericType().getTypeName().equalsIgnoreCase("long")) {
			field.setAccessible(true);
			//encrypt field  
			field.setAccessible(false);
		}else if(field.getGenericType().getTypeName().equalsIgnoreCase("java.lang.String")) {
			//for private fields we need to change the access modifier 
			field.setAccessible(true);
			//the field value : 
			System.out.println("field value :");
			System.out.println(field.get(object));
			//change the field value : 
			//field.set( object,"static test");
			field.setAccessible(false);
			//encrypt field
		}else if(field.getGenericType().getTypeName().equalsIgnoreCase("java.time.LocalDateTime")) {
			field.setAccessible(true);
			//encrypt field  
			field.setAccessible(false);
		}else if(field.getGenericType().getTypeName().equalsIgnoreCase("boolean")) {
			field.setAccessible(true);
			//encrypt field  
			field.setAccessible(false);
		}
		return field ;
		//may throw IllegalAccessException or ArgumentMissmatch Exception 
		}catch(Exception e ) {
			e.printStackTrace();
			return null ; 
		} 
	}
	
	protected Field decryptType(Field field,Object object) {
		try {
		if (field.getGenericType().getTypeName().equalsIgnoreCase("int")) {
			field.setAccessible(true);
			//encrypt field  
			field.setAccessible(false);
		}else if(field.getGenericType().getTypeName().equalsIgnoreCase("long")) {
			field.setAccessible(true);
			//encrypt field  
			field.setAccessible(false);
		}else if(field.getGenericType().getTypeName().equalsIgnoreCase("java.lang.String")) {
			//for private fields we need to change the access modifier 
			field.setAccessible(true);
			//the field value : 
			System.out.println("field value :");
			System.out.println(field.get(object));
			//change the field value : 
			field.set( object,"static test");
			field.setAccessible(false);
			//encrypt field
		}else if(field.getGenericType().getTypeName().equalsIgnoreCase("java.time.LocalDateTime")) {
			field.setAccessible(true);
			//encrypt field  
			field.setAccessible(false);
		}else if(field.getGenericType().getTypeName().equalsIgnoreCase("boolean")) {
			field.setAccessible(true);
			//encrypt field  
			field.setAccessible(false);
		}
		return field ;
		//may throw IllegalAccessException or ArgumentMissmatch Exception 
		}catch(Exception e ) {
			e.printStackTrace();
			return null ; 
		} 
	}
	
	
}
