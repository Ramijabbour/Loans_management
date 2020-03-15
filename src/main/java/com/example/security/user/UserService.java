package com.example.security.user;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.aspect.Exceptions;
import com.example.security.permissions.Permissions;
import com.example.security.permissions.PermissionsService;
import com.example.security.roles.Roles;

@Service
public class UserService{

	@Autowired 
	UserRepository userRepository ; 	
	
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
	public Optional<User> getUserByID(int id ) {	
		if( this.userRepository.findById(id)== null ) {
			throw new Exceptions(-404,"cannot find the requested user ");
		}else 
			return this.userRepository.findById(id);	
	}
	
	//find User by userName 
	public User getUserByUserName(String userName) {
		for(User user : this.userRepository.findAll()) {
			if(user.getUserName().equalsIgnoreCase(userName)) {
				return user ; 
			}
		}
		return null ;
	}
	
	//add new user // 
	public void addUser(User user ) {
		if(checkUserinforDuplication(user)) {
			throw new Exceptions(-405,"user data duplication error");
		}else {
			this.userRepository.save(user); 
		}
	}
	
	//update current user // 
	public void updateUser(User user) {
		if(checkUserinforDuplication(user)) {
			throw new Exceptions(-405,"user data duplication error");
		}else {
			this.userRepository.save(user); 
		}
	}
	
	//delete user//
	public void deleteUser(User user ) {
		//should be modefied 
		this.userRepository.deleteById(user.getUserID());
	}
	
	
	//User Duplication Check 
	//check if the user is currently in the system // 
	public boolean checkUserinforDuplication(User user ) {
		List<User> usersList = this.userRepository.findAll() ; 
		for(int i = 0 ; i < usersList.size() ; i++ ) {
			User tempUser = usersList.get(i) ;
			if(tempUser.getUserName().equalsIgnoreCase(user.getUserName())) {
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
