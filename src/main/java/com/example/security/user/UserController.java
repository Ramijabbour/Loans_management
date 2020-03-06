package com.example.security.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.User;

@RestController
public class UserController {

	@RequestMapping(method = RequestMethod.GET , value="/adminstration/users/adduser")
	public void addUser(User user) {
		
	}
	
}
