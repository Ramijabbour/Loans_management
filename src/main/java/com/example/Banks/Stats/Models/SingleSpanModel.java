package com.example.Banks.Stats.Models;

<<<<<<< HEAD
import com.example.MasterService;
=======
import com.example.SiteConfig.MasterService;
>>>>>>> d4df773b7b6dd2cd6942fadf54cdbef5074a8a5a

public class SingleSpanModel {
	
	private int year = MasterService.getCurrDate().getYear() ;
	private int quarter = 1 ; 
	
	public SingleSpanModel() {}
	
	public SingleSpanModel(int year,int quarter) {
		super();
		this.year = year;
		this.quarter = quarter ; 
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getQuarter() {
		return quarter;
	}

	public void setQuarter(int quarter) {
		this.quarter = quarter;
	} 

	
	
	
}
