package com.example.SiteConfig;

import org.springframework.web.servlet.ModelAndView;

public class SiteConfiguration {

	// initial values  can be changed 
	
	private static int PageSize = 20 ;
	
	
	private static int AnalatycsPageSize = 1000 ; 
	
	private static int allocationsAnalyticsPageSize = 100 ; 
	
	private static int DayStartHour = 1 ; 
	private static int DayEndHour = 23 ; 
	
	
	public static int getAllocationsAnalyticsPageSize() {
		return allocationsAnalyticsPageSize;
	}

	public static void setAllocationsAnalyticsPageSize(int allocationsAnalyticsPageSize) {
		SiteConfiguration.allocationsAnalyticsPageSize = allocationsAnalyticsPageSize;
	}

	public static int getPageSize() {
		return PageSize;
	}

	public static void setPageSize(int pageSize) {
		PageSize = pageSize;
	}
	

	public static int getAnalatycsPageSize() {
		return AnalatycsPageSize;
	}

	public static void setAnalatycsPageSize(int analatycsPageSize) {
		AnalatycsPageSize = analatycsPageSize;
	}

	public static void addSequesnceVaraibles(ModelAndView mav,int index) {
		index++ ;
    	mav.addObject("nxt",index);
    	int prev = index - 2 ;
    	if(prev < 0 ) {
    		prev = 0 ; 
    	}
    	mav.addObject("prev",prev);
	}

	public static int getDayStartHour() {
		return DayStartHour;
	}

	public static void setDayStartHour(int dayStartHour) {
		DayStartHour = dayStartHour;
	}

	public static int getDayEndHour() {
		return DayEndHour;
	}

	public static void setDayEndHour(int dayEndHour) {
		DayEndHour = dayEndHour;
	}
	
	
}
