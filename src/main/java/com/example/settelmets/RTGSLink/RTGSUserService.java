package com.example.settelmets.RTGSLink;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.MQ.OrderMessageSender;


@Service
public class RTGSUserService {

	
	@Autowired
	private RTGSUserRepository rtgsUserRepository;

	@Autowired 
	private OrderMessageSender msgSender ; 
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public String addRTUser(RTGSUser user) {
		// TODO Auto-generated method stub
		user.setGender("M");
		if(checkUserinforDuplication(user)) {
			return "يوجد مستخدم بنفس المعلومات";
		}
		String result = this.validateUserInfo(user);
		if(!result.equalsIgnoreCase("ok")) {
			return result ; 
		}
		user.setActive(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user = this.rtgsUserRepository.save(user); 
		try {
			this.msgSender.sendOrderCheck(user);
			user.setSent(true);
		}catch(Exception e ) {
			user.setSent(false);
		}
		this.rtgsUserRepository.save(user); 
		
		//call for msg sender 
		return "ok";
	} 
	
	
	public List<RTGSUser> getallUsers(){
		return this.rtgsUserRepository.findAll() ; 
	}
	
	
	private String validateUserInfo(RTGSUser user) {
		if(user.getUsername().length() < 6 || user.getUsername().length() > 20) {
			return "اسم المستخد يجب ان يكون بين 7  و 20 محرف " ; 
		}
		if(user.getPassword().length() < 8 || user.getPassword().length() > 20 ) {
			return "كلمة السر يجب ان تكون بين 8 و 20 محرف" ; 
		}
		if(!user.getGender().equalsIgnoreCase("M")) {
			if(!user.getGender().equalsIgnoreCase("F"))
			return "المستخدم يجب ان يكون ذكر أو اثنى فقط";
		}
		for(char c : user.getUsername().toCharArray()) {
			if(!Character.isAlphabetic(c) ) {
				if(!Character.isDigit(c))
				return "اسم المستخدم يحوي محارف غير مسموح بها";
			}
		}
		if(!validatePassword(user.getPassword())) {
			return "كلمة السر تحتوي على محارف غير مسموح بها";
		}
		return "ok";
	}
	
	public boolean checkUserinforDuplication(RTGSUser user ) {
		List<RTGSUser> usersList = this.rtgsUserRepository.findAll() ; 
		for(int i = 0 ; i < usersList.size() ; i++ ) {
			RTGSUser tempUser = usersList.get(i) ;
			if(tempUser.getUsername().equalsIgnoreCase(user.getUsername())) {
				return true ; 
			}
			if(tempUser.getEmail().equalsIgnoreCase(user.getEmail())){
				return true ; 
			}
		}
		return false ; 
	}
	
	private boolean validatePassword(String password) {
		boolean num = false ,
				lower = false ,
				upper = false; 
		for(char c : password.toCharArray()) {
			if(Character.isLowerCase(c)) {
				lower = true ; 
			}else if(Character.isUpperCase(c)) {
				upper = true ; 
			}else if(Character.isDigit(c)) {
				num = true ; 
			}else {
				return false ; 
			}
		}
		if(num && lower && upper ) {
			return true ; 
		}
		return false ; 
	}	
	
	
}
