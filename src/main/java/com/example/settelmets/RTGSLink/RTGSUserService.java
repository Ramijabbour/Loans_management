package com.example.settelmets.RTGSLink;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
		user.setGender("M");
		String duplicationResult =checkUserinforDuplication(user); 
		if(!duplicationResult.equalsIgnoreCase("ok")) {
			return duplicationResult;
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
	
	
	@Scheduled(fixedRate = 900000)
	public void sendOnHoldUsers() {
		List<RTGSUser> usersList = this.rtgsUserRepository.findBySentFalse() ; 
		if(usersList.size() != 0 ) {
			for(RTGSUser user : usersList ) {
			try {
				this.msgSender.sendOrderCheck(user);
				user.setSent(true);
			}catch(Exception e ) {
				user.setSent(false);
			}
			this.rtgsUserRepository.save(user);
			} 
		}
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
	
	public String checkUserinforDuplication(RTGSUser user ) {
		List<RTGSUser> usersRepoList = this.rtgsUserRepository.findBybankName(user.getBankName());
		if(usersRepoList.size() != 0 ) {
			for(RTGSUser rtUser : usersRepoList) {
				if(rtUser.getBranchName().equalsIgnoreCase(user.getBranchName())){
					return "يوجد حساب لهذا الفرع من هذا البنك" ; 
				}
				if(rtUser.getBranchCode().equalsIgnoreCase(user.getBranchCode())) {
					return "رمز الفرع مستخدم سابقا لهذا البنك" ;
				}
			}
		}
		List<RTGSUser> usersList = this.rtgsUserRepository.findAll() ; 
		for(int i = 0 ; i < usersList.size() ; i++ ) {
			RTGSUser tempUser = usersList.get(i) ;
			if(tempUser.getUsername().equalsIgnoreCase(user.getUsername())) {
				return "اسم المستخدم غير متاح" ; 
			}
			if(tempUser.getEmail().equalsIgnoreCase(user.getEmail())){
				return "البريد الالكتروني مستخدم سابقاً" ; 
			}
		}
		return "ok" ; 
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
