package com.example.security.userRoles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.aspect.Exceptions;

@Service
public class UserRolesService {

	
	@Autowired
	UserRolesRepository userRolesRepository ; 
	
	
	public void addUserRole(UserRoles userRoles) {
		if(this.userRolesRepository.findAll().contains(userRoles)) {
			throw new Exceptions(-405,"the requested user already have this role");
		}else {
			this.userRolesRepository.save(userRoles); 
		}
	}
	
	public void deleteUserRole(UserRoles userRoles) {
		this.userRolesRepository.delete(userRoles);
	}
	
	public void updateUserRole(UserRoles userRoles ) {
		if(!this.userRolesRepository.findAll().contains(userRoles)) {
			throw new Exceptions(-404,"the requested user-role cannot be found ");
		}else {
			this.userRolesRepository.save(userRoles); 
		}
	}
	
}
