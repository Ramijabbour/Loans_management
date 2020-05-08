package com.example.Banks.Stats.ChartsHandler;

import com.example.MasterService;

public class TimeSpanModel {

	private int stdate = 1957; 
	private int fndate = MasterService.getCurrDate().getYear();
	
	
	public TimeSpanModel() {}
	
	public TimeSpanModel(int stdate, int fndate) {
		super();
		this.stdate = stdate;
		this.fndate = fndate;
	}
	public int getStdate() {
		return stdate;
	}
	public void setStdate(int stdate) {
		this.stdate = stdate;
	}
	public int getFndate() {
		return fndate;
	}
	public void setFndate(int fndate) {
		this.fndate = fndate;
	} 
	
	
	
}
