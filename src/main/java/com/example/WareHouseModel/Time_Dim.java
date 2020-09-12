package com.example.WareHouseModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Time_Dim {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int TimeID;
	
	
	public String Date ;
	
	public int DD ;
	
	public int MM ; 
	
	public int YY ;
	
	public Time_Dim(){
		
	}

	public Time_Dim(String date, int dD, int mM, int yY) {
		super();
		Date = date;
		DD = dD;
		MM = mM;
		YY = yY;
	}

	public int getTimeID() {
		return TimeID;
	}

	public void setTimeID(int timeID) {
		TimeID = timeID;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public int getDD() {
		return DD;
	}

	public void setDD(int dD) {
		DD = dD;
	}

	public int getMM() {
		return MM;
	}

	public void setMM(int mM) {
		MM = mM;
	}

	public int getYY() {
		return YY;
	}

	public void setYY(int yY) {
		YY = yY;
	}
		
	


	
	
	
}
