package com.example.Loans.Analytics.Charts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Banks.Stats.Models.AnalysisCompositeModel;

@RestController
public class LoansAnalyticsAjaxController {
	
	@Autowired
	private LoansAnalyticsChartsService loansAnalyticsService ; 
	

	
	@RequestMapping("/ajax/getFinanaceTypesYear")
	public AnalysisCompositeModel getFinanaceTypesYear() {
		return  loansAnalyticsService.getFinanaceTypesYear() ; 
	}
	
	@RequestMapping("/ajax/getFinanaceTypesMonth")
	public AnalysisCompositeModel getFinanaceTypesMonth() {
		
		return loansAnalyticsService.getFinanaceTypesMonth() ; 
	}
	
	
	
	@RequestMapping("/ajax/getLoansTypesYear")
	public AnalysisCompositeModel getLoansTypesYear() {
		return loansAnalyticsService.getLoansTypesYear() ; 
	}
	
	@RequestMapping("/ajax/getLonasTypesMonth")
	public AnalysisCompositeModel getLonasTypesMonth() {
		return loansAnalyticsService.getLonasTypesMonth() ; 
	}
	
	
	
	@RequestMapping("/ajax/getMostBanksYear")
	public AnalysisCompositeModel getMostBanksYear() {
		return loansAnalyticsService.getMostBanksYear() ; 
	}
	
	@RequestMapping("/ajax/getMostBanksMonth")
	public AnalysisCompositeModel getMostBanksMonth() {
		return loansAnalyticsService.getMostBanksMonth() ; 
	}
	
	
	
	@RequestMapping("/ajax/getBankLoansCountYear")
	public AnalysisCompositeModel getBankLoansCountYear() {
		return loansAnalyticsService.getBankLoansCountYear() ; 
	}
	
	@RequestMapping("/ajax/getBankLoansCountMonth")
	public AnalysisCompositeModel getBankLoansCountMonth() {
		return loansAnalyticsService.getBankLoansCountMonth() ; 
	}
	
	
}
