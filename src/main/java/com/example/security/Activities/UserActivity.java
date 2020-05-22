package com.example.security.Activities;

import java.time.LocalDateTime;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.MasterService;
import com.example.aspect.EncryptDecrypt.IntEncryptDecryptConverter;
import com.example.aspect.EncryptDecrypt.StringEncryptDecryptConverter;

@Entity
public class UserActivity {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Convert(converter = IntEncryptDecryptConverter.class)
	private int EntryId ; 
	
	@Convert(converter = IntEncryptDecryptConverter.class)
	private int UserId ; 
	
	@Convert(converter = StringEncryptDecryptConverter.class)
	private String UserName ; 
	
	@Convert(converter = StringEncryptDecryptConverter.class)
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
