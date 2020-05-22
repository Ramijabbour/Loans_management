package com.example.SiteConfig;

import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class SiteConfigController {

	
	@RequestMapping(method = RequestMethod.GET , value = "/config/currConfig")
	public ModelAndView getSiteConfigView() {
		ModelAndView mav = new ModelAndView("config/currConfig") ; 
		mav.addObject("currConfig", new SiteConfigAlterModel());
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/config/currConfig/alter")
	public ModelAndView getSiteConfigAlterView() {
		ModelAndView mav = new ModelAndView("config/alterConfiguration") ; 
		mav.addObject("currConfig", new SiteConfigAlterModel());
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/config/currConfig/alter/commit")
	@Transactional
	public ModelAndView alterSiteConfiguration(@ModelAttribute SiteConfigAlterModel sitenewConfig) {
		String status = validateNewData(sitenewConfig); 
		if(!status.equalsIgnoreCase("ok")) {
			return MasterService.sendGeneralError(status);
		}
		SiteConfiguration.setAllocationsAnalyticsPageSize(sitenewConfig.getAllocationsAnalyticsPageSize());
		SiteConfiguration.setAnalatycsPageSize(sitenewConfig.getAnalatycsPageSize());
		SiteConfiguration.setDayEndHour(sitenewConfig.getDayEndHour());
		SiteConfiguration.setDayStartHour(sitenewConfig.getDayStartHour());
		SiteConfiguration.setPageSize(sitenewConfig.getPageSize());
		return getSiteConfigView();
	}
	
	
	private String validateNewData(SiteConfigAlterModel sitenewConfig) {
		if(sitenewConfig.getDayEndHour() <= 0 )
			return "ساعة نهاية اليوم لا يمكن ان تكون قيمة سالبة";
		if(sitenewConfig.getDayStartHour() <= 0 )
			return "ساعة بداية اليوم لا يمكن ان تكون قيمة سالبة";
		if(sitenewConfig.getDayEndHour() < sitenewConfig.getDayStartHour())
			return "ساعة نهاية اليوم لا يمكن ان تكون اقل من ساعة بداية اليوم";
		if(sitenewConfig.getPageSize() <= 0 )
			return "حجم صفحات العرض لا يمكن ان تكون قيمة سالبة";
		if(sitenewConfig.getAllocationsAnalyticsPageSize() <= 0 )
			return "لا يمكن اسناد قيم سالبة";
		if(sitenewConfig.getAnalatycsPageSize() <= 0 )
			return "لا يمكن اسناد قيم سالبة";
		return "ok";
	}

	@RequestMapping(method = RequestMethod.GET , value = "/config/currConfig/default")
	public ModelAndView resetSiteConfiguration() {
		SiteConfiguration.setAllocationsAnalyticsPageSize(100);
		SiteConfiguration.setAnalatycsPageSize(1000);
		SiteConfiguration.setDayEndHour(16);
		SiteConfiguration.setDayStartHour(8);
		SiteConfiguration.setPageSize(20);
		return getSiteConfigView(); 
	}
	
}
