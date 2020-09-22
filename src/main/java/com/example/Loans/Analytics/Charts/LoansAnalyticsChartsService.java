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
import com.example.SiteConfig.MasterService;

@Service
public class LoansAnalyticsChartsService {
	
	@Autowired
	private LoansAnalyticsService loansAnalyticsService ; 
	
	LoansAnalyticsModel analyticsModel =  new LoansAnalyticsModel();
	
	String Year = MasterService.getYearFromStringDate(MasterService.getDateAsString());
	String Month = MasterService.getMonthFromStringDate(MasterService.getDateAsString());
	
	public void processData() {
		analyticsModel = loansAnalyticsService.processLoansAnalysisData(); 	
		Year = loansAnalyticsService.getYearOfAnalytics() ; 
		Month = loansAnalyticsService.getMonthOfAnalytics();
	}
	
	
	
	public AnalysisCompositeModel getFinanaceTypesYear() {
		AnalysisCompositeModel ACM = new AnalysisCompositeModel();
		ACM.setTitle("نسب أنواع التمويل على السلف خلال سنة "+Year);
		ACM.addCat(analyticsModel.getYearOfAnalytics());
	
		List<AnalysisModel> modelList = new ArrayList<AnalysisModel>();
	
		for (Map.Entry<String,Float> entry : analyticsModel.getFinanaceTypesYear().entrySet()){
			AnalysisModel AM = new AnalysisModel();
			AM.setName(entry.getKey());
			float amount = entry.getValue() ; 
			long amountAsInt = (long)amount;
			AM.addDataEntry(amountAsInt);
			modelList.add(AM);
		}
		ACM.setSeries(modelList);
		return ACM ; 
	}
	
	public AnalysisCompositeModel getFinanaceTypesMonth() {
		AnalysisCompositeModel ACM = new AnalysisCompositeModel();
		ACM.setTitle("نسب أنواع التمويل على السلف خلال شهر "+Month);
		ACM.addCat(analyticsModel.getMonthOfAnalytics());
		
		List<AnalysisModel> modelList = new ArrayList<AnalysisModel>();
		
		for (Map.Entry<String,Float> entry : analyticsModel.getFinanaceTypesMonth().entrySet()){
			AnalysisModel AM = new AnalysisModel();
			AM.setName(entry.getKey());
			float amount = entry.getValue() ; 
			long amountAsInt = (long)amount;
			AM.addDataEntry(amountAsInt);
			modelList.add(AM);
		}
		ACM.setSeries(modelList);
		return ACM ;
	}
	
	
	
	public AnalysisCompositeModel getLoansTypesYear() {
		AnalysisCompositeModel ACM = new AnalysisCompositeModel();
		ACM.setTitle("نسب أنواع السلف خلال سنة"+Year);
		ACM.addCat(analyticsModel.getYearOfAnalytics());
		
		List<AnalysisModel> modelList = new ArrayList<AnalysisModel>();
		
		for (Map.Entry<String,Float> entry : analyticsModel.getLoansTypesYear().entrySet()){
			AnalysisModel AM = new AnalysisModel();
			AM.setName(entry.getKey());
			float amount = entry.getValue() ; 
			long amountAsInt = (long)amount;
			AM.addDataEntry(amountAsInt);
			modelList.add(AM);
		}
		ACM.setSeries(modelList);
		return ACM ; 
	}
	
	public AnalysisCompositeModel getLonasTypesMonth() {
		AnalysisCompositeModel ACM = new AnalysisCompositeModel();
		ACM.setTitle("نسب أنواع السلف خلال شهر"+Month);
		ACM.addCat(analyticsModel.getMonthOfAnalytics());
		
		List<AnalysisModel> modelList = new ArrayList<AnalysisModel>();
		for (Map.Entry<String,Float> entry : analyticsModel.getLoansTypesMonth().entrySet()){
			AnalysisModel AM = new AnalysisModel();
			AM.setName(entry.getKey());
			float amount = entry.getValue() ; 
			long amountAsInt = (long)amount;
			AM.addDataEntry(amountAsInt);
			modelList.add(AM);
		}
		ACM.setSeries(modelList);
		return ACM ;
	}
	
	
	
	public AnalysisCompositeModel getMostBanksYear() {
		AnalysisCompositeModel ACM = new AnalysisCompositeModel();
		ACM.setTitle("قيم السلف لكل بنك خلال سنة "+Year);
		ACM.addCat(analyticsModel.getYearOfAnalytics());
		
		List<AnalysisModel> modelList = new ArrayList<AnalysisModel>();
		for (Map.Entry<String,Long> entry : analyticsModel.getMostBanksYear().entrySet()){
			AnalysisModel AM = new AnalysisModel();
			AM.setName(entry.getKey());
			long amount = entry.getValue() ; 
			//int amountAsInt = Math.toIntExact(amount);
			AM.addDataEntry(amount);
			modelList.add(AM);
		}
		ACM.setSeries(modelList);
		return ACM ; 
	}
	
	public AnalysisCompositeModel getMostBanksMonth() {
		AnalysisCompositeModel ACM = new AnalysisCompositeModel();
		ACM.setTitle("قيم السلف لكل بنك خلال شهر "+Month);
		ACM.addCat(analyticsModel.getMonthOfAnalytics());
		
		List<AnalysisModel> modelList = new ArrayList<AnalysisModel>();
		for (Map.Entry<String,Long> entry : analyticsModel.getMostBanksMonth().entrySet()){
			AnalysisModel AM = new AnalysisModel();
			AM.setName(entry.getKey());
			long amount = entry.getValue() ; 
			//int amountAsInt = Math.toIntExact(amount);
			AM.addDataEntry(amount);
			modelList.add(AM);
		}
		ACM.setSeries(modelList);
		return ACM ;
	}
	
	
	
	public AnalysisCompositeModel getBankLoansCountYear() {
		AnalysisCompositeModel ACM = new AnalysisCompositeModel();
		ACM.setTitle("عدد السلف الممنوحة لكل بنك خلال سنة "+Year);
		ACM.addCat(analyticsModel.getYearOfAnalytics());
		
		List<AnalysisModel> modelList = new ArrayList<AnalysisModel>();
		for (Map.Entry<String,Integer> entry : analyticsModel.getBankLoansCountYear().entrySet()){
			AnalysisModel AM = new AnalysisModel();
			AM.setName(entry.getKey());
			long amount = entry.getValue() ; 
			AM.addDataEntry(amount);
			modelList.add(AM);
		}
		ACM.setSeries(modelList);
		return ACM ;
	}
	
	public AnalysisCompositeModel getBankLoansCountMonth() {
		AnalysisCompositeModel ACM = new AnalysisCompositeModel();
		ACM.setTitle("عدد السلف الممنوحة لكل بنك خلال شهر"+Month);
		ACM.addCat(analyticsModel.getMonthOfAnalytics());
		
		List<AnalysisModel> modelList = new ArrayList<AnalysisModel>();
		for (Map.Entry<String,Integer> entry : analyticsModel.getBankLoansCountMonth().entrySet()){
			AnalysisModel AM = new AnalysisModel();
			AM.setName(entry.getKey());
			long amount = entry.getValue() ; 
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



	public String getYear() {
		return Year;
	}



	public void setYear(String year) {
		Year = year;
	}



	public String getMonth() {
		return Month;
	}



	public void setMonth(String month) {
		Month = month;
	}
	
	
}
