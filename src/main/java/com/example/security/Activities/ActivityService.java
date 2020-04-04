package com.example.security.Activities;

import java.io.File;
import java.io.FileWriter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MasterService;

@Service
public class ActivityService {

	
	@Autowired 
	private UserActivityRepository userActivityRepository ; 
	
	
	public void addActivityLog(int userID , String userName ,String activity ) {
		UserActivity userActivity = new UserActivity(userID, userName, activity);
		this.userActivityRepository.save(userActivity); 
	}
	
	public List<UserActivity> getActivityLog(){
		return this.userActivityRepository.findAll(); 
	}

	
	public List<UserActivity> getUserActivity(int userId){
		List<UserActivity> userActivity = new ArrayList<UserActivity>() ; 
		List<UserActivity> allActivity = this.userActivityRepository.findAll() ;
		for(UserActivity userLog : allActivity) {
			if(userLog.getUserId() == userId) {
				userActivity.add(userLog);
			}
		}
		return userActivity; 
	}
	
	
	public void listLogs() {
	
		List<UserActivity> allActivities = this.userActivityRepository.findAll() ;
		
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm-ss");  
			String fileName =  System.getProperty("user.dir")+"\\Log_"+dtf.format(MasterService.getCurrDateTime()) +".txt";
			File myObj = new File(fileName);
			System.out.println("file name "+fileName);
			myObj.createNewFile();
			System.out.println("File created: " + myObj.getName());
			FileWriter fileWriter = new FileWriter(fileName);
			String line = "" ;
			for(UserActivity userActivity : allActivities ) {
				line = userActivity.getUserName() + " with id : "+userActivity.getUserId() +" " + userActivity.getActivity() + " at : "+userActivity.getTimeStamp() + System.getProperty( "line.separator" );  
				fileWriter.write(line);
				line = ""; 
			}
			fileWriter.close();
		}catch(Exception e ) {
			System.out.println("Log listing failed error trace : ");
			e.printStackTrace();
			
		}
		
		
	}
	
	public void clearLogs() {
		this.listLogs();
		this.userActivityRepository.deleteAll();  
	}
}


