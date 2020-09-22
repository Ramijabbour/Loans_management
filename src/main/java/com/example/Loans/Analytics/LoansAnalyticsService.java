package com.example.Loans.Analytics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ServicesPool;
import com.example.Loans.Loans;
import com.example.SiteConfig.MasterService;
import java.util.List;


@Service
public class LoansAnalyticsService {

	@Autowired 
	private ServicesPool pool ; 
	
	private String yearOfAnalytics =" " ;

	private String MonthOfAnalytics =" ";
	
	
	public LoansAnalyticsModel processLoansAnalysisData() {
		
		if(yearOfAnalytics.equalsIgnoreCase(" ") || MonthOfAnalytics.equalsIgnoreCase(" ")) {
			yearOfAnalytics = MasterService.getYearFromStringDate(MasterService.getDateAsString());
			MonthOfAnalytics = MasterService.getMonthFromStringDate(MasterService.getDateAsString());
		}
		
		boolean endOfData = false ; 
		int index = 0 ;
		
		LoansAnalyticsModel loansAnalyticsModel = new LoansAnalyticsModel() ;
		loansAnalyticsModel.setYearOfAnalytics(yearOfAnalytics);
		loansAnalyticsModel.setMonthOfAnalytics(MonthOfAnalytics);
		
		while(!endOfData) {
			List<Loans> LoansOfThisYear = getDataChuck(index);
			if(LoansOfThisYear == null ) {
				endOfData = true ;
				break ; 
			}else {
				loansAnalyticsModel.calculateYearData(LoansOfThisYear);
				loansAnalyticsModel.calculateMonthData(LoansOfThisYear);
				index ++ ;  
			}	
		}
		loansAnalyticsModel.calcRatios();
		
		return loansAnalyticsModel ; 
	}
	
	
	public List<Loans> getDataChuck(int index){
		List<Loans> LoansOfThisYear = pool.getLoansService().getLoansOfYear(yearOfAnalytics,index);
		if(LoansOfThisYear.size() != 0 )
			return LoansOfThisYear; 
		else 
			return null ; 
	}
	
	
	
	public String getYearOfAnalytics() {
		return yearOfAnalytics;
	}

	public void setYearOfAnalytics(String yearOfAnalytics) {
		this.yearOfAnalytics = yearOfAnalytics;
	}


	public String getMonthOfAnalytics() {
		return MonthOfAnalytics;
	}


	public void setMonthOfAnalytics(String monthOfAnalytics) {
		MonthOfAnalytics = monthOfAnalytics;
	}
	
	
}
