package com.example.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dataBase.UserRepository;
import com.example.models.User;

@Service
public class UserService {

	@Autowired 
	UserRepository userRepository ; 
	
	 
	public void addUser(User user ) {
		if(true ) {
			//trow duplicate user exception 
		}else {
			
		}
	}
	
	
	public boolean checkUserinfoDuplication(User user ) {
		
		
		
		return false ; 
	}
	
	
}
