package com.example;

import org.springframework.web.servlet.ModelAndView;

public class SiteConfiguration {

	// initial values  can be changed 
	
	private static int PageSize = 20 ;
	
	
	public static int getPageSize() {
		return PageSize;
	}

	public static void setPageSize(int pageSize) {
		PageSize = pageSize;
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
	
}
