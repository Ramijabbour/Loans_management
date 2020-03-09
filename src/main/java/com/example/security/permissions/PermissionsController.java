package com.example.security.permissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PermissionsController {
	
	@Autowired
	PermissionsService permissionsService ;
	
	@RequestMapping(method = RequestMethod.GET ,value = "/permissions/commit")
	public void commitPermissions() {
		this.permissionsService.commitPermissionsInjection();
	}
}
