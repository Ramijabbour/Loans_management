package com.example.security.Activities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.MasterService;

@Entity
public class UserActivity {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int EntryId ; 
	private int UserId ; 
	private String UserName ; 
	private String Activity ; 
	private LocalDateTime TimeStamp ;
	
	
	
	public UserActivity() {
		super();
		this.TimeStamp = MasterService.getCurrDateTime() ; 
	}
	public UserActivity(int userId, String userName , String activity ) {
		super();
		UserId = userId;
		UserName = userName;
		this.Activity = activity ; 
		this.TimeStamp= MasterService.getCurrDateTime() ; 
	}
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int userId) {
		UserId = userId;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public LocalDateTime getTimeStamp() {
		return TimeStamp;
	}
	public void setTimeStamp(LocalDateTime timeStamp) {
		TimeStamp = timeStamp;
	}
	public String getActivity() {
		return Activity;
	}
	public void setActivity(String activity) {
		Activity = activity;
	} 
	
	
	
	
}
