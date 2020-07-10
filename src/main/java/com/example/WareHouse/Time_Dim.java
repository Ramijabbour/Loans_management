package com.example.WareHouse;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Time_Dim {
	
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		public int TimeID ;
		
		public String Date ; 
		
	    public int  Day  ;
		
	    public int month ; 
	    
	    public int year ;
	    
	    public Time_Dim() {}
	    

		public Time_Dim(int day, int month, int year,String Date) {
			super();
			Day = day;
			this.month = month;
			this.year = year;
			this.Date= Date; 
		}

		public int getTimeID() {
			return TimeID;
		}

		public void setTimeID(int timeID) {
			TimeID = timeID;
		}

		public int getDay() {
			return Day;
		}

		public void setDay(int day) {
			Day = day;
		}

		public int getMonth() {
			return month;
		}

		public void setMonth(int month) {
			this.month = month;
		}

		public int getYear() {
			return year;
		}

		public void setYear(int year) {
			this.year = year;
		}


		public String getDate() {
			return Date;
		}


		public void setDate(String date) {
			Date = date;
		} 
	    
	    
	    
	
}
