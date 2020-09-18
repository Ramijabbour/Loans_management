package com.example.settelmets.Analytics.charts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Banks.Stats.Models.AnalysisCompositeModel;

@RestController
public class SettlementsChartsAjaxController {

	
	@Autowired
	private SettlementChartsService chartsService ; 
	
	@RequestMapping("/ajax/getSettlementYearData")
	public AnalysisCompositeModel getSettlementYearData() {
		chartsService.getAnalyticsModel();
		return chartsService.getAnalysisCompositeModelYear() ; 
	}
	
	@RequestMapping("/ajax/getSettlementMonthData")
	public AnalysisCompositeModel getSettlementMonthData() {
		chartsService.getAnalyticsModel();
		return chartsService.getAnalysisCompositeModelMonth();
	}
	
	
}
