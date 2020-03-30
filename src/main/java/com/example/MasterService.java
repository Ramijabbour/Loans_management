package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Notifications.NotificationsService;

@Service
public class MasterService {
	@Autowired
	protected NotificationsService notificationsService ; 
	
	//we can add error service or logging service. ...etc 
	
}
