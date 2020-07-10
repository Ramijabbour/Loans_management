package com.example.WareHouse;

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
	
	public String DD ;
	
	public String MM ; 
	
	public String YY ;
	
	
	

	public Time_Dim(){
		
	}
		
	
	public Time_Dim(String date, String dD, String mM, String yY) {
		super();
		Date = date;
		DD = dD;
		MM = mM;
		YY = yY;
	}


	public int getTimeID() {
		return TimeID;
	}

	public String getDD() {
		return DD;
	}



	public void setDD(String dD) {
		DD = dD;
	}



	public String getMM() {
		return MM;
	}



	public void setMM(String mM) {
		MM = mM;
	}



	public String getYY() {
		return YY;
	}



	public void setYY(String yY) {
		YY = yY;
	}


	
	
	
	public String getDate() {
		return Date;
	}




	public void setDate(String date) {
		Date = date;
	}


	
	
	
}
