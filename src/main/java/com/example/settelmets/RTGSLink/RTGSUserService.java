package com.example.settelmets.RTGSLink;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.MQ.OrderMessageSender;
import com.example.SiteConfig.MasterService;


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
	
	public void addRTGSAdmin() {
		LocalDate now = LocalDate.now();
		RTGSUser admin = new RTGSUser("mohammed_.1996@live.com",passwordEncoder.encode("admin123"),"admin","دمشق", 
				"المركزي","#cbr0","male","ACCESS_TEST1","ADMIN",now.toString(),true);
		try {
			this.msgSender.sendOrderCheck(admin);
			admin.setSent(true);
		}catch(Exception e ) {
			admin.setSent(false);
		}
		this.rtgsUserRepository.save(admin);
	
	
		admin = this.rtgsUserRepository.save(admin);
		List<RTGSUser> usersList = addUsers();
	
		for(RTGSUser user : usersList ) {
			try {
				this.msgSender.sendOrderCheck(user);
				user.setSent(true);
			}catch(Exception e ) {
				user.setSent(false);
			}
		}
		this.rtgsUserRepository.saveAll(usersList);
	}

	private List<RTGSUser> addUsers() {
		List<RTGSUser> usersList = new ArrayList<RTGSUser>();
		
		RTGSUser b1 = new RTGSUser("build1@gmail.com",passwordEncoder.encode("admin123"),"user1","المزرعة", 
				"التجاري","#cbr1","male","EMPLOYEE","USER",MasterService.getDateAsString(),true);
		b1 = this.rtgsUserRepository.save(b1);
		usersList.add(b1);
		
		RTGSUser b2 = new RTGSUser("build2@gmail.com",passwordEncoder.encode("admin123"),"user2","المزة", 
				"التجاري","#cbr2","male","EMPLOYEE","USER",MasterService.getDateAsString(),true);
		b2 = this.rtgsUserRepository.save(b2);
		usersList.add(b2);
		
		RTGSUser b3 = new RTGSUser("build3@gmail.com",passwordEncoder.encode("admin123"),"user3","المزة", 
				"العقاري","#cbr3","male","EMPLOYEE","USER",MasterService.getDateAsString(),true);
		b3 = this.rtgsUserRepository.save(b3);
		usersList.add(b3);
		
		RTGSUser b4 = new RTGSUser("build4@gmail.com",passwordEncoder.encode("admin123"),"user4","العدوي", 
				"العقاري","#cbr4","male","EMPLOYEE","USER",MasterService.getDateAsString(),true);
		b4 = this.rtgsUserRepository.save(b4);
		usersList.add(b4);
		
		
		RTGSUser b5 = new RTGSUser("build5@gmail.com",passwordEncoder.encode("admin123"),"user5","ركن الدين", 
				"الزراعي","#cbr5","male","EMPLOYEE","USER",MasterService.getDateAsString(),true);
		b5 = this.rtgsUserRepository.save(b5);
		usersList.add(b5);
		
		RTGSUser b6 = new RTGSUser("build6@gmail.com",passwordEncoder.encode("admin123"),"user6","الزاهرة", 
				"الزراعي","#cbr6","male","EMPLOYEE","USER",MasterService.getDateAsString(),true);
		b6 = this.rtgsUserRepository.save(b6);
		usersList.add(b6);
		
		RTGSUser b7 = new RTGSUser("build7@gmail.com",passwordEncoder.encode("admin123"),"user7","المرجة", 
				"البركة","#cbr7","male","EMPLOYEE","USER",MasterService.getDateAsString(),true);
		b7 = this.rtgsUserRepository.save(b7);
		usersList.add(b7);
		
		RTGSUser b8 = new RTGSUser("build8@gmail.com",passwordEncoder.encode("admin123"),"user8","ركن الدين", 
				"البركة","#cbr8","male","EMPLOYEE","USER",MasterService.getDateAsString(),true);
		b8 = this.rtgsUserRepository.save(b8);
		usersList.add(b8);
		
		RTGSUser b9 = new RTGSUser("build9@gmail.com",passwordEncoder.encode("admin123"),"user9","المرجة", 
				"سوريا و الخليج","#cbr9","male","EMPLOYEE","USER",MasterService.getDateAsString(),true);
		b9 = this.rtgsUserRepository.save(b9);
		usersList.add(b9);
		
		RTGSUser b10 = new RTGSUser("build10@gmail.com",passwordEncoder.encode("admin123"),"user10","العباسيين", 
				"سوريا و الخليج","#cbr10","male","EMPLOYEE","USER",MasterService.getDateAsString(),true);
		b10 = this.rtgsUserRepository.save(b10);
		usersList.add(b10);
	
		return usersList ; 
	}	

}
