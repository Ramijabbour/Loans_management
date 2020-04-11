package com.example.security.user;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.MasterBackUpService;
import com.example.MasterService;
import com.example.aspect.DataDuplicationException;
import com.example.security.UserRoles.UserRoleService;
import com.example.security.permissions.Permissions;
import com.example.security.permissions.PermissionsService;
import com.example.security.roles.Roles;
import com.example.security.userPermissions.UserPermissionsService;

@Service
public class UserService extends MasterService implements MasterBackUpService {

	@Autowired 
	UserRepository userRepository ; 	
	
	@Autowired
	UserRoleService userRoleService ; 
	
	@Autowired
	UserPermissionsService userPermissionsService ;
	
	private PasswordEncoder passwordEncoder ;
	
	//Service permissions Injection 
	public UserService(PasswordEncoder passwordEncoder ) {
		System.out.println("user service init ------------------------>>>>>>>>");
		this.passwordEncoder = passwordEncoder ; 
		/*we add all the services to permissions service */
		Method[] methods =  this.getClass().getDeclaredMethods();
		List<String> methodsNames = new ArrayList<String>(); 
		for(Method method : methods) {
			System.out.println("method name from service : "+method.getName());
			methodsNames.add(method.getName());
		}
		methodsNames.add(this.getClass().getSimpleName());
		System.out.println("ls service : "+this.getClass().getSimpleName());
		PermissionsService.addPermissionsToPermissionsList(methodsNames);
		/*permissions added and need to be committed to permissions table in the data base at route /permissions/commit*/
	}
	//
	
	
	//all Users// 
	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}
	
	//find user by id // 
	public User getUserByID(int id ) {	
		List<User> allUsers = this.userRepository.findAll() ; 
		if(allUsers.isEmpty()) {
			System.out.println("empty UsersList ");
			return null ;  
		}
		for(User user : allUsers) {
			if(user.getUserID() == id ){
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
	public void addUser(User user ) {
		user.flatUserDetailes();
		if(checkUserinforDuplication(user)) {
			throw new DataDuplicationException();
		}else {
			user = (User) super.encryptData(user);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setActive(false);
			this.userRepository.save(user);
			super.notificationsService.addNotification("New User need Activation", "/adminstration/users/nonactive", "ADMIN,SUPER");
			
		}
		
	}
	
	//update current user // 
	public void updateUser(User user) {
		System.out.println("trace Update User with object : ");
		user.flatUserDetailes();
		try {
			if(this.userRepository.findById(user.getUserID()) != null) {
					this.userRepository.save(user); 
				}
		}catch(Exception e ) {
			System.out.println("NullPointerException Handled at User Service / Update User -- call for null User ");
			e.printStackTrace();
		}
	}
	
	//delete user//
	public void deleteUser(User user ) {
		this.userPermissionsService.deleteUser(user);
		this.userRoleService.deleteUser(user);
		this.userRepository.deleteById(user.getUserID());
	}
	
	
	//User Duplication Check 
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
	
}
