package com.example.settelmets.Analytics.charts;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Banks.Stats.Models.AnalysisCompositeModel;
import com.example.Banks.Stats.Models.AnalysisModel;
import com.example.settelmets.Analytics.AnalyticsModel;
import com.example.settelmets.Analytics.SettlementServicePool;

@Service
public class SettlementChartsService {

	@Autowired
	private SettlementServicePool servicePool; 
	
	
	private AnalyticsModel analyticsModel = null ;

	private LocalDate date = null ; 
	
	public void getAnalyticsModel() {
		if(analyticsModel == null ) {
			analyticsModel = servicePool.getAnalyticsService().analyticsSequence();
		}
		date = servicePool.getAnalyticsService().getLocalDate();
	}

	
	public AnalysisCompositeModel getAnalysisCompositeModelYear() {
		AnalysisCompositeModel ACM = new AnalysisCompositeModel();
		
		ACM.setTitle("البنوك الأكثر استخداما لنظام التسوية خلال هذه السنة");
		ACM.addCat(String.valueOf(date.getYear()));
		
		
		List<AnalysisModel> modelList = new ArrayList<AnalysisModel>();
		
		for (Map.Entry<String,Long> entry : analyticsModel.getMostBanksYear().entrySet()){
			AnalysisModel AM = new AnalysisModel();
			AM.setName(entry.getKey());
			Long amount = entry.getValue() ; 
			//int amountAsInt = Math.toIntExact(amount);
			AM.addDataEntry(amount);
			modelList.add(AM);
		}
		ACM.setSeries(modelList);
		return ACM ; 
	}
	
	
	public AnalysisCompositeModel getAnalysisCompositeModelMonth() {		
		AnalysisCompositeModel ACM = new AnalysisCompositeModel();
		
		ACM.setTitle("البنوك الأكثر استخداما لنظام التسوية خلال هذا الشهر");
		ACM.addCat(String.valueOf(date.getMonthValue()));
		
		List<AnalysisModel> modelList = new ArrayList<AnalysisModel>();
		for (Map.Entry<String,Long> entry : analyticsModel.getMostBanksMonth().entrySet()){
			AnalysisModel AM = new AnalysisModel();
			AM.setName(entry.getKey());
			Long amount = entry.getValue() ; 
			//int amountAsInt = Math.toIntExact(amount);
			AM.addDataEntry(amount);
			modelList.add(AM);
		}
		ACM.setSeries(modelList);
		return ACM ; 
	}
	
	

	public void setAnalyticsModel(AnalyticsModel analyticsModel) {
		this.analyticsModel = analyticsModel;
	}	
	
}
