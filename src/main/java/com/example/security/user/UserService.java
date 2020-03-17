package com.example.security.user;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.aspect.Exceptions;
import com.example.security.UserRoles.UserRoleService;
import com.example.security.permissions.Permissions;
import com.example.security.permissions.PermissionsService;
import com.example.security.roles.Roles;
import com.example.security.userPermissions.UserPermissionsService;

@Service
public class UserService{

	@Autowired 
	UserRepository userRepository ; 	
	
	@Autowired
	UserRoleService userRoleService ; 
	
	@Autowired
	UserPermissionsService userPermissionsService ;
	
	//Service permissions Injection 
	public UserService() {
		System.out.println("user service init ------------------------>>>>>>>>");
		
		/*we add all the services to permissions service */
		Method[] methods =  this.getClass().getDeclaredMethods();
		List<String> methodsNames = new ArrayList<String>(); 
		for(Method method : methods) {
			System.out.println("method name from service : "+method.getName());
			methodsNames.add(method.getName());
		}
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
			throw new Exceptions(-405,"user data duplication error");
		}else {
			user.setUserRoles("none");
			user.setUserPermissions("none");
			user.setActive(false);
			this.userRepository.save(user); 
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
	public void addPermissionsToUser(User user , List<Permissions> permissions ) {
		if(permissions.isEmpty()) {
			return ; 
		}
		if(user.getUserPermissions().equalsIgnoreCase("none")) {
			user.setUserPermissions("");
		}
		for(Permissions permission : permissions ) {
			if(!user.hasPermission(permission.getPermissionName())) {
				user.addPermission(permission.getPermissionName());
			}else {continue ;}
		}
		this.userRepository.save(user);
	}
	
	@Transactional 
	public void addRolesToUser(User user , List<Roles> roles) {
		if(roles.isEmpty()) {
			return ; 
		}
		if(user.getUserRoles().equalsIgnoreCase("none")) {
			user.setUserRoles("");
		}
		for(Roles role : roles ) {
			if(!user.hasRole(role.getRoleName())) {
				user.addRole(role.getRoleName());
			}else {continue ;}
		}
		this.userRepository.save(user);
	}
	
	@Transactional
	public void revokeRoleFromUser(User user , Roles role) {
		if(!user.hasRole(role.getRoleName())) {
			return ; 
		}else{
			user.revokeRoleFromUser(role.getRoleName());
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

}
