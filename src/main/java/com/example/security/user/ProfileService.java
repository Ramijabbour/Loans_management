package com.example.security.user;

import org.springframework.stereotype.Service;

import com.example.SiteConfig.MasterService;

@Service 
public class ProfileService extends MasterService  {

	public User getCurrUser() {
		return super.get_current_User() ; 
	}
	
}
