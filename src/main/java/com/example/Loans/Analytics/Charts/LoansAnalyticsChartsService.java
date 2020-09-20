package com.example.Loans.Analytics.Charts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Banks.Stats.Models.AnalysisCompositeModel;
import com.example.Banks.Stats.Models.AnalysisModel;
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
	
	
	
	public AnalysisCompositeModel getFinanaceTypesYear() {
		AnalysisCompositeModel ACM = new AnalysisCompositeModel();
		ACM.setTitle("نسب أنواع التمويل على السلف خلال هذه السنة");
		ACM.addCat(analyticsModel.getYearOfAnalytics());
	
		List<AnalysisModel> modelList = new ArrayList<AnalysisModel>();
	
		for (Map.Entry<String,Float> entry : analyticsModel.getFinanaceTypesYear().entrySet()){
			AnalysisModel AM = new AnalysisModel();
			AM.setName(entry.getKey());
			float amount = entry.getValue() ; 
			int amountAsInt = (int)amount;
			AM.addDataEntry(amountAsInt);
			modelList.add(AM);
		}
		ACM.setSeries(modelList);
		return ACM ; 
	}
	
	public AnalysisCompositeModel getFinanaceTypesMonth() {
		AnalysisCompositeModel ACM = new AnalysisCompositeModel();
		ACM.setTitle("نسب أنواع التمويل على السلف خلال هذا الشهر");
		ACM.addCat(analyticsModel.getMonthOfAnalytics());
		
		List<AnalysisModel> modelList = new ArrayList<AnalysisModel>();
		
		for (Map.Entry<String,Float> entry : analyticsModel.getFinanaceTypesMonth().entrySet()){
			AnalysisModel AM = new AnalysisModel();
			AM.setName(entry.getKey());
			float amount = entry.getValue() ; 
			int amountAsInt = (int)amount;
			AM.addDataEntry(amountAsInt);
			modelList.add(AM);
		}
		ACM.setSeries(modelList);
		return ACM ;
	}
	
	
	
	public AnalysisCompositeModel getLoansTypesYear() {
		AnalysisCompositeModel ACM = new AnalysisCompositeModel();
		ACM.setTitle("نسب أنواع السلف خلال هذه السنة");
		ACM.addCat(analyticsModel.getYearOfAnalytics());
		
		List<AnalysisModel> modelList = new ArrayList<AnalysisModel>();
		
		for (Map.Entry<String,Float> entry : analyticsModel.getLoansTypesYear().entrySet()){
			AnalysisModel AM = new AnalysisModel();
			AM.setName(entry.getKey());
			float amount = entry.getValue() ; 
			int amountAsInt = (int)amount;
			AM.addDataEntry(amountAsInt);
			modelList.add(AM);
		}
		ACM.setSeries(modelList);
		return ACM ; 
	}
	
	public AnalysisCompositeModel getLonasTypesMonth() {
		AnalysisCompositeModel ACM = new AnalysisCompositeModel();
		ACM.setTitle("نسب أنواع السلف خلال هذا الشهر");
		ACM.addCat(analyticsModel.getMonthOfAnalytics());
		
		List<AnalysisModel> modelList = new ArrayList<AnalysisModel>();
		for (Map.Entry<String,Float> entry : analyticsModel.getLoansTypesMonth().entrySet()){
			AnalysisModel AM = new AnalysisModel();
			AM.setName(entry.getKey());
			float amount = entry.getValue() ; 
			int amountAsInt = (int)amount;
			AM.addDataEntry(amountAsInt);
			modelList.add(AM);
		}
		ACM.setSeries(modelList);
		return ACM ;
	}
	
	
	
	public AnalysisCompositeModel getMostBanksYear() {
		AnalysisCompositeModel ACM = new AnalysisCompositeModel();
		ACM.setTitle("قيم السلف لكل بنك خلال هذه السنة");
		ACM.addCat(analyticsModel.getYearOfAnalytics());
		
		List<AnalysisModel> modelList = new ArrayList<AnalysisModel>();
		for (Map.Entry<String,Long> entry : analyticsModel.getMostBanksYear().entrySet()){
			AnalysisModel AM = new AnalysisModel();
			AM.setName(entry.getKey());
			long amount = entry.getValue() ; 
			int amountAsInt = Math.toIntExact(amount);
			AM.addDataEntry(amountAsInt);
			modelList.add(AM);
		}
		ACM.setSeries(modelList);
		return ACM ; 
	}
	
	public AnalysisCompositeModel getMostBanksMonth() {
		AnalysisCompositeModel ACM = new AnalysisCompositeModel();
		ACM.setTitle("قيم السلف لكل بنك خلال هذا الشهر");
		ACM.addCat(analyticsModel.getMonthOfAnalytics());
		
		List<AnalysisModel> modelList = new ArrayList<AnalysisModel>();
		for (Map.Entry<String,Long> entry : analyticsModel.getMostBanksMonth().entrySet()){
			AnalysisModel AM = new AnalysisModel();
			AM.setName(entry.getKey());
			long amount = entry.getValue() ; 
			int amountAsInt = Math.toIntExact(amount);
			AM.addDataEntry(amountAsInt);
			modelList.add(AM);
		}
		ACM.setSeries(modelList);
		return ACM ;
	}
	
	
	
	public AnalysisCompositeModel getBankLoansCountYear() {
		AnalysisCompositeModel ACM = new AnalysisCompositeModel();
		ACM.setTitle("عدد السلف الممنوحة لكل بنك خلال هذه السنة");
		ACM.addCat(analyticsModel.getYearOfAnalytics());
		
		List<AnalysisModel> modelList = new ArrayList<AnalysisModel>();
		for (Map.Entry<String,Integer> entry : analyticsModel.getBankLoansCountYear().entrySet()){
			AnalysisModel AM = new AnalysisModel();
			AM.setName(entry.getKey());
			int amount = entry.getValue() ; 
			AM.addDataEntry(amount);
			modelList.add(AM);
		}
		ACM.setSeries(modelList);
		return ACM ;
	}
	
	public AnalysisCompositeModel getBankLoansCountMonth() {
		AnalysisCompositeModel ACM = new AnalysisCompositeModel();
		ACM.setTitle("عدد السلف الممنوحة لكل بنك خلال هذا الشهر");
		ACM.addCat(analyticsModel.getMonthOfAnalytics());
		
		List<AnalysisModel> modelList = new ArrayList<AnalysisModel>();
		for (Map.Entry<String,Integer> entry : analyticsModel.getBankLoansCountMonth().entrySet()){
			AnalysisModel AM = new AnalysisModel();
			AM.setName(entry.getKey());
			int amount = entry.getValue() ; 
			AM.addDataEntry(amount);
			modelList.add(AM);
		}
		ACM.setSeries(modelList);
		return ACM ; 
	}
	
	
	
	public LoansAnalyticsModel getAnalyticsModel() {
		return analyticsModel;
	}

	public void setAnalyticsModel(LoansAnalyticsModel analyticsModel) {
		this.analyticsModel = analyticsModel;
	}
	
	
}
