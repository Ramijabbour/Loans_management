package com.example.security.user;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
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
	
	List<String> ServicesNames = Arrays.asList() ; 
	
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
		this.userRepository.deleteById(user.getUserID());
	}
	
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

	@Transactional
	public void addPermissionsToUser(User user , List<Permissions> permissions ) {
		if(permissions.isEmpty()) {
			throw new Exceptions(-406,"Empty Permissions List");
		}
		for(Permissions permission : permissions ) {
			if(!user.hasPermission(permission.getPermissionName())) {
				user.addPermission(permission.getPermissionName());
			}else {continue ;}
		}
	}
	
	@Transactional 
	public void addRolesToUser(User user , List<Roles> roles) {
		if(roles.isEmpty()) {
			throw new Exceptions(-406,"Empty roles List");
		}
		for(Roles role : roles ) {
			if(!user.hasRole(role.getRoleName())) {
				user.addRole(role.getRoleName());
			}else {continue ;}
		}
	}
	
}
