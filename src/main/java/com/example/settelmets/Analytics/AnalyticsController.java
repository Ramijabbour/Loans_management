package com.example.settelmets.Analytics;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AnalyticsController {

	@Autowired
	private AnalyticsService analyticsService; 
	
	
	@RequestMapping(method=RequestMethod.GET,value ="/rtgs/analytics/timeSet")
	public ModelAndView setRTGSAnalyticsTime() {
		ModelAndView mav = new ModelAndView("Analytics/timeSet");
		mav.addObject("model", new TimeModel());
		return mav ; 
	}
	
	@RequestMapping(method=RequestMethod.POST,value ="/rtgs/analytics/timeSet")
	public void setRTGSAnalyticsTime(@ModelAttribute TimeModel timeModel ,HttpServletResponse response) throws IOException {
		this.analyticsService.setYear(String.valueOf(timeModel.getYear()));
		this.analyticsService.setMonth(String.valueOf(timeModel.getMonth()));
		this.analyticsService.setDay(String.valueOf(timeModel.getDay()));
		response.sendRedirect("/rtgs/analytics");
		//return getRTGSAnalytics(String.valueOf(timeModel.getYear()),String.valueOf(timeModel.getMonth())) ; 
	}
	
	
	@RequestMapping(method=RequestMethod.GET,value ="/rtgs/analytics")
	public ModelAndView getRTGSAnalytics() {
		ModelAndView mav = new ModelAndView("Analytics/all");
		mav.addObject("model", this.analyticsService.analyticsSequence());
		return mav ; 
	}
	
	
	@RequestMapping(method=RequestMethod.GET,value ="/rtgs/analytics/continue")
	public ModelAndView getRTGSAnalyticsNext() {
		ModelAndView mav = new ModelAndView("Analytics/next");
		return mav ; 
	}
	
}
