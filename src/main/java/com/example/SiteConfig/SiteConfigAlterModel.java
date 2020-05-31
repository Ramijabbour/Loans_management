package com.example.SiteConfig;

public class SiteConfigAlterModel {

	
	private int PageSize ;
	
	
	private int AnalatycsPageSize ; 
	
	private int allocationsAnalyticsPageSize ; 
	
	private int DayStartHour ; 
	private int DayEndHour  ;
	
	public SiteConfigAlterModel() {
		PageSize = SiteConfiguration.getPageSize();
		AnalatycsPageSize = SiteConfiguration.getAnalatycsPageSize();
		allocationsAnalyticsPageSize = SiteConfiguration.getAllocationsAnalyticsPageSize();
		DayStartHour = SiteConfiguration.getDayStartHour();
		DayEndHour = SiteConfiguration.getDayEndHour() ; 
	}
	
	
	public SiteConfigAlterModel(int pageSize, int analatycsPageSize, int allocationsAnalyticsPageSize, int dayStartHour,
			int dayEndHour) {
		super();
		PageSize = pageSize;
		AnalatycsPageSize = analatycsPageSize;
		this.allocationsAnalyticsPageSize = allocationsAnalyticsPageSize;
		DayStartHour = dayStartHour;
		DayEndHour = dayEndHour;
	}
	public int getPageSize() {
		return PageSize;
	}
	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}
	public int getAnalatycsPageSize() {
		return AnalatycsPageSize;
	}
	public void setAnalatycsPageSize(int analatycsPageSize) {
		AnalatycsPageSize = analatycsPageSize;
	}
	public int getAllocationsAnalyticsPageSize() {
		return allocationsAnalyticsPageSize;
	}
	public void setAllocationsAnalyticsPageSize(int allocationsAnalyticsPageSize) {
		this.allocationsAnalyticsPageSize = allocationsAnalyticsPageSize;
	}
	public int getDayStartHour() {
		return DayStartHour;
	}
	public void setDayStartHour(int dayStartHour) {
		DayStartHour = dayStartHour;
	}
	public int getDayEndHour() {
		return DayEndHour;
	}
	public void setDayEndHour(int dayEndHour) {
		DayEndHour = dayEndHour;
	} 
	
}
