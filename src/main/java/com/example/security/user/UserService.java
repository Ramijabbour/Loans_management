package com.example.security.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.aspect.Exceptions;
import com.example.dataBase.UserRepository;
import com.example.models.User;

@Service
public class UserService {

	@Autowired 
	UserRepository userRepository ; 
	
	
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
		if(checkUserinfoDuplication(user)) {
			throw new Exceptions(-405,"user data duplication error");
		}else {
			this.userRepository.save(user); 
		}
	}
	
	//update current user // 
	public void updateUser(User user) {
		if(checkUserinfoDuplication(user)) {
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
	public boolean checkUserinfoDuplication(User user ) {
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

}
