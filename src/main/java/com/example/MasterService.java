package com.example;


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

	//private static String secretKey = "boooooooooom!!!!";
	//private static String salt = "ssshhhhhhhhhhh!!!!";
	
	
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
	
	/*
	public  Object encryptData(Object object) {
		Field[] fields = object.getClass().getDeclaredFields() ; 
		System.out.println("object flat initiated with fileds :");
		for(Field field : fields  ) {
			encryptType(field,object);
		}
		return object; 
	}
	
	public  Object decryptData(Object object) {
		Field[] fields = object.getClass().getDeclaredFields() ; 
		System.out.println("object flat initiated with fileds :");
		for(Field field : fields  ) {
			decryptType(field,object);
		}
		return object; 
	}
	
	
	private Field encryptType(Field field,Object object) {
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
			field.set(object,encrypt((String) field.get(object),MasterService.secretKey));
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
	
	private Field decryptType(Field field,Object object) {
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
			field.set( object,decrypt((String)field.get(object),MasterService.secretKey));
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
	
	
	
	private static String encrypt(String strToEncrypt, String secret)
	{
		try
		{
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			IvParameterSpec ivspec = new IvParameterSpec(iv);

			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		}
		catch (Exception e)
		{
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}
	
	private static String decrypt(String strToDecrypt, String secret) {
		try
		{
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			IvParameterSpec ivspec = new IvParameterSpec(iv);

			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		}
		catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}
	*/
}
