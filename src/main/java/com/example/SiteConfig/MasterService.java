package com.example.SiteConfig;


import java.time.LocalDate;
/*
import java.util.Base64;
import java.lang.reflect.Field;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
*/
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.example.security.Activities.ActivityService;
import com.example.security.permissions.PermissionsService;
import com.example.security.user.User;
import com.example.security.user.UserRepository;

@Service
public class MasterService {

	@Autowired
	protected UserRepository userRepo; 
	
	@Autowired
	protected ActivityService activityService ;

	@Autowired
	PermissionsService permissionsService ; 

	
	public MasterService(){
	 
	}
	
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
	
	public static LocalDate getCurrDate() {
		   //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		   LocalDate now = LocalDate.now();  
		   //System.out.println("current request time :"+dtf.format(now));
		   return now ; 
	}
	
	public static String getDateAsString() {
		LocalDate now = LocalDate.now();  
		return now.toString();  
	}
	
	public static String getDateTimeAsString() {
		LocalDateTime now = LocalDateTime.now();  
		return now.toString();  
	}
	
	public static String getYearFromStringDate(String date) {
		LocalDate desiredDate = LocalDate.parse(date);
		int year = desiredDate.getYear(); 
		return String.valueOf(year) ; 
	}
	
	public static String getMonthFromStringDate(String date) {
		LocalDate desiredDate = LocalDate.parse(date);
		int month = desiredDate.getMonthValue(); 
		return String.valueOf(month) ; 
	}
	
	public static String getDayFromStringDate(String date) {
		LocalDate desiredDate = LocalDate.parse(date);
		int month = desiredDate.getDayOfMonth(); 
		return String.valueOf(month) ; 
	}
	
	public static ModelAndView sendGeneralError(String errormsg){
		ModelAndView mav = new ModelAndView("Errors/generalError");
		mav.addObject("msg", errormsg);
		return mav ; 
	}
	
	
	public static ModelAndView sendSuccessMsg(String msg){
		ModelAndView mav = new ModelAndView("success/sucMsg");
		mav.addObject("msg", msg);
		return mav ; 
	}
	
	public static void addPermissionsToPermissionService(List<String> methodsNames) {
		PermissionsService.addPermissionsToPermissionsList(methodsNames);
	}
}
