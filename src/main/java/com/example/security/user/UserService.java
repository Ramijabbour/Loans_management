package com.example.security.user;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.MasterBackUpService;
import com.example.SiteConfig.MasterService;
import com.example.SiteConfig.SiteConfiguration;
import com.example.security.Dispatcher.ServiceDispatcher;
import com.example.security.permissions.Permissions;
import com.example.security.roles.Roles;

@Service
public class UserService extends MasterService implements MasterBackUpService {

	@Autowired 
	UserRepository userRepository ; 	
	
	@Autowired 
	ServiceDispatcher dispatcher ;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); ;

	public UserService() {

	}
	//
	
	//all Users// 
	public List<User> getAllUsers(int PageNumber) {
		Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize(), Sort.by("id"));
		Page<User> pagedResult = this.userRepo.findAll(paging);
		if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<User>();
        }
	}
	
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}
	
	
	//find user by id // 
	@Transactional
	public User getUserByID(int id ) {
		List<User> allUsers = this.userRepository.findAll() ; 
		if(allUsers.isEmpty()) {
			System.out.println("empty UsersList ");
			return null ;  
		}
		for(User user : allUsers) {
			if(user.getId() == id ){
				return user  ; 
			}
		}
		System.out.println("requested user not found ");
		return null ; 
	}
	
	//find User by userName 
	public User getUserByUserName(String userName) {
		for(User user : this.userRepository.findAll()) {
			if(user.getUsername().equalsIgnoreCase(userName)) {
				return user ; 
			}
		}
		return null ;
	}
	
	//add new user // 
	@Transactional
	public String addUser(User user ) {
		user.flatUserDetailes();
		if(checkUserinforDuplication(user)) {
			return "User already exist in the system";
		}
		else if(!validateUserInfo(user).equalsIgnoreCase("ok")) {
			return validateUserInfo(user); 
		}
		else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setActive(false);
			this.userRepository.save(user);
			return "ok";
		}
		
	}
	
	private String validateUserInfo(User user) {
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
	
	private User getUserBYId(int id ) {
		for(User user : this.userRepo.findAll()) {
			if(user.getId() == id ) {
				return user ; 
			}
		}
		return null ; 
	}
	
	//update current user // 
	@Transactional
	public String updateUser(User user) {
		String result = "";
		try {
			if(this.userRepository.findById(user.getId()) != null) {
				User dataUser = getUserBYId(user.getId()); 
				result = validateUserInfo(user); 
				if(result.equalsIgnoreCase("ok")){
					user.setPassword(passwordEncoder.encode(user.getPassword()));
					if(dataUser.isActive())
						user.setActive(true);
					this.userRepository.save(user); 
					return "ok";
				}
			}
		}catch(Exception e ) {
			System.out.println("NullPointerException Handled at User Service / Update User -- call for null User ");
			e.printStackTrace();
		}
		return result ;  
	}
	
	//delete user//
	public void deleteUser(User user ) {
		dispatcher.getUserPermissionsService().deleteUser(user);
		dispatcher.getUserRolesService().deleteUser(user);
		this.userRepository.deleteById(user.getId());
	}
	
	
	//User Info Check 
	//check if the user is currently in the system // 
	public boolean checkUserinforDuplication(User user ) {
		List<User> usersList = this.userRepository.findAll() ; 
		for(int i = 0 ; i < usersList.size() ; i++ ) {
			User tempUser = usersList.get(i) ;
			if(tempUser.getUsername().equalsIgnoreCase(user.getUsername())) {
				return true ; 
			}
			if(tempUser.getEmail().equalsIgnoreCase(user.getEmail())){
				return true ; 
			}
		}
		return false ; 
	}

	//check if the password contains illegal characters 
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
	
	//User Access Control Section 
	
	
	@Transactional
	public void addPermissionsToUser(User user , Permissions permission ) {
		if(permission == null ) {
			return ; 
		}
		if(user.getUserPermissions().equalsIgnoreCase(" ")) {
			user.setUserPermissions(" ");
		}
		if(!user.hasPermission(permission.getPermissionName())) {
				user.addPermission(permission.getPermissionName());	
				this.userRepository.save(user);
		}
	}
	
	
	public void addRolesToUser(User user , Roles role) {
		if(role == null) {
			return ; 
		}
		if(!user.hasRole(role.getRoleName())) {
				user.addRole(role.getRoleName());
				user.addPermission(role.getAssignedPermissions());
				this.userRepository.save(user);
		}
	}
	
	@Transactional
	public void revokeRoleFromUser(User user , Roles role) {
		if(!user.hasRole(role.getRoleName())) {
			return ; 
		}else{
			//remove role permissions from string /
			user.revokeRoleFromUser(role.getRoleName());
			for(String permission : role.getAssignedPermissions().split(",")) {
				user.revokePermissionFromUser(permission);	
			}
			user.flatUserDetailes();
			this.userRepository.save(user); 
		}
	}
	
	@Transactional
	public void revokePermissionFromUser(User user , Permissions permission) {
		if(!user.hasPermission(permission.getPermissionName())) {
			return ; 
		}else {
			user.revokePermissionFromUser(permission.getPermissionName());
			this.userRepository.save(user);
		}
	}

	
	public List<User> getNonActiveUsers() {
		List<User> allUsers = this.userRepository.findAll() ; 
		List<User> nonActiveUsers = new ArrayList<User>(); 
		for(User user : allUsers) {
			if(!user.isActive()) {
				nonActiveUsers.add(user);
			}
		}
		return nonActiveUsers; 
	}
	
	public List<User> getActiveUsers(){
		List<User> allUsers = this.userRepository.findAll() ; 
		List<User> ActiveUsers = new ArrayList<User>(); 
		for(User user : allUsers) {
			if(user.isActive()) {
				ActiveUsers.add(user);
			}
		}
		return ActiveUsers;
	}
	
	public void activateUser(int userid) {
		User user = this.getUserByID(userid);
		user.setActive(true);
		this.userRepository.save(user);
	}
	
	public void deActivateUser(int userid) {
		User user = this.getUserByID(userid);
		user.setActive(false);
		this.userRepository.save(user);		
	}


	@Override
	public void backUpData() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm-ss");  
		String fileName =  System.getProperty("user.dir")+"\\BackUp\\Users_BackUp_"+dtf.format(MasterService.getCurrDateTime()) +".ser";
		File myObj = new File(fileName);
		System.out.println("file name "+fileName);
		try {
			myObj.createNewFile();
			System.out.println("File created: " + myObj.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public int getUsersCount() {
		return this.userRepo.getUsersCount() ; 
	}
}
