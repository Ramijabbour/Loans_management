package com.example.Banks.Stats.Models;

import com.example.SiteConfig.MasterService;

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
