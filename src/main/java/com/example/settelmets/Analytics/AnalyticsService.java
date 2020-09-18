package com.example.settelmets.Analytics;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ServicesPool;
import com.example.SiteConfig.MasterService;
import com.example.settelmets.Models.Chaque;
import com.example.settelmets.Models.SettledChaque;


@Service
public class AnalyticsService {

		
	@Autowired
	private SettlementServicePool facade ; 

	private LocalDate localDate = MasterService.getCurrDate();
		
	
	public AnalyticsModel analyticsSequence() {
		//variables
		this.localDate = MasterService.getCurrDate();
		boolean endOfData = false ; 
		int index = 0 ;
		AnalyticsModel analyticsModel = new AnalyticsModel(); 
		
		//process 
		while(!endOfData) {
			List<Chaque> checkList = getDataChuck(index);
			if(checkList == null) {
				endOfData = true ; 
				break ; 
			}
			checkList = calcYearAnalytics(checkList,analyticsModel);  
			checkList = calcMonthAnalytics(checkList,analyticsModel); 
			calcDayAnalytics(checkList,analyticsModel);  
			index++; 
		}
		
		analyticsModel.setBanksCount(facade.getRtgsUsersService().getallUsers().size());
		
		//settled Checks process 
		index = 0 ; 
		endOfData = false ; 
		while(!endOfData) {
			List<SettledChaque> settledCheckList = facade.getSettlementService().getSettledChecks(index);
			if(settledCheckList.size() == 0) {
				endOfData = true ; 
				break ; 
			}
			settledCheckList = calcSettledChecksYearAnalysis(settledCheckList,analyticsModel);
			settledCheckList = calcSettledChecksMonthAnalysis(settledCheckList,analyticsModel);
			calcSettledChecksDayAnalysis(settledCheckList,analyticsModel);
			index++;  
		}
		
		return analyticsModel ; 
	}
	

	public List<Chaque> getDataChuck(int index ) {
		List<Chaque> checksList = facade.getSettlementService().getAllChecks(index);
		if(checksList.size() != 0 ) {
			return checksList ;  
		}else {
			return null ; 
		}
	}
	
	
	private List<Chaque> calcYearAnalytics(List<Chaque> checkList, AnalyticsModel analyticsModel) {
		List<Chaque> filteredChecksList = new ArrayList<Chaque>();
		for(Chaque check : checkList ) {
			String currYear = MasterService.getYearFromStringDate(localDate.toString());
			String checkYear = MasterService.getYearFromStringDate(check.getCheckEntryDate());
			if(currYear.equalsIgnoreCase(checkYear)) {
				filteredChecksList.add(check);
				analyticsModel.addYearCheck(check);
			}
			
		}
		return filteredChecksList;
	}

	private List<Chaque> calcMonthAnalytics(List<Chaque> checkList, AnalyticsModel analyticsModel) {
		List<Chaque> filteredChecksList = new ArrayList<Chaque>();
		for(Chaque check : checkList ) {
			String currYear = MasterService.getMonthFromStringDate(localDate.toString());
			String checkYear = MasterService.getMonthFromStringDate(check.getCheckEntryDate());
			if(currYear.equalsIgnoreCase(checkYear)) {
				filteredChecksList.add(check);
				analyticsModel.addMonthCheck(check);
			}
		}
		return filteredChecksList;
	}

	private void calcDayAnalytics(List<Chaque> checkList, AnalyticsModel analyticsModel) {
		for(Chaque check : checkList ) {
			String currYear = MasterService.getDayFromStringDate(localDate.toString());
			String checkYear = MasterService.getDayFromStringDate(check.getCheckEntryDate());
			if(currYear.equalsIgnoreCase(checkYear)) {
				analyticsModel.addDayCheck(check);
			}
		}		
	}
	
	
	private List<SettledChaque> calcSettledChecksYearAnalysis(List<SettledChaque> settledCheckList, AnalyticsModel analyticsModel) {
		List<SettledChaque> filteredChecksList = new ArrayList<SettledChaque>();
		for(SettledChaque settledCheck : settledCheckList ) {
			String currYear = MasterService.getYearFromStringDate(localDate.toString());
			String checkYear = MasterService.getYearFromStringDate(settledCheck.getLocalDateTime());
			if(currYear.equalsIgnoreCase(checkYear)) {
				filteredChecksList.add(settledCheck);
				analyticsModel.addYearSettledCheck(settledCheck);
			}
			
		}
		return filteredChecksList;
	}


	private List<SettledChaque> calcSettledChecksMonthAnalysis(List<SettledChaque> settledCheckList,AnalyticsModel analyticsModel) {
		List<SettledChaque> filteredChecksList = new ArrayList<SettledChaque>();
		for(SettledChaque settledCheck : settledCheckList ) {
			String currMonth = MasterService.getMonthFromStringDate(localDate.toString());
			String checkMonth = MasterService.getMonthFromStringDate(settledCheck.getLocalDateTime());
			if(currMonth.equalsIgnoreCase(checkMonth)) {
				filteredChecksList.add(settledCheck);
				analyticsModel.addMonthSettledCheck(settledCheck);
			}
			
		}
		return filteredChecksList;
	}


	private void calcSettledChecksDayAnalysis(List<SettledChaque> settledCheckList,AnalyticsModel analyticsModel) {
		for(SettledChaque settledCheck : settledCheckList ) {
			String currDay = MasterService.getMonthFromStringDate(localDate.toString());
			String checkDay = MasterService.getMonthFromStringDate(settledCheck.getLocalDateTime());
			if(currDay.equalsIgnoreCase(checkDay)) {
				analyticsModel.addDaySettledCheck(settledCheck);
			}
			
		}
	}
	
	
}
