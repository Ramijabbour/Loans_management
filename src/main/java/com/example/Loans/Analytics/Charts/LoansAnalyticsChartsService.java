package com.example.Loans.Analytics.Charts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Loans.Analytics.LoansAnalyticsModel;
import com.example.Loans.Analytics.LoansAnalyticsService;

@Service
public class LoansAnalyticsChartsService {
	
	@Autowired
	private LoansAnalyticsService loansAnalyticsService ; 
	
	LoansAnalyticsModel analyticsModel =  new LoansAnalyticsModel();
	
	public void processData() {
		analyticsModel = loansAnalyticsService.processLoansAnalysisData(); 
		
		
	}
	
	
}
