package com.example.settelmets.Analytics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.settelmets.Analytics.charts.SettlementChartsService;
import com.example.settelmets.RTGSLink.RTGSUserService;
import com.example.settelmets.Services.SettlementService;

@Service
public class SettlementServicePool {
	
	@Autowired
	private RTGSUserService rtgsUsersService ; 
	
	@Autowired
	private SettlementService settlementService ;

	@Autowired
	private AnalyticsService analyticsService; 
	
	@Autowired
	private SettlementChartsService settlementChartsService ; 
	
	
	public RTGSUserService getRtgsUsersService() {
		return rtgsUsersService;
	}

	public SettlementService getSettlementService() {
		return settlementService;
	}

	public AnalyticsService getAnalyticsService() {
		return analyticsService;
	}

	public SettlementChartsService getSettlementChartsService() {
		return settlementChartsService;
	} 

	
}
