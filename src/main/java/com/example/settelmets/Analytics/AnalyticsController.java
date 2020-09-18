package com.example.settelmets.Analytics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AnalyticsController {

	@Autowired
	private AnalyticsService analyticsService; 
	
	
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
