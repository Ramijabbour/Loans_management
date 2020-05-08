package com.example.Banks.Stats.ChartsHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Allocations.Allocations;
import com.example.Allocations.AllocationsService;
import com.example.Banks.Banks;

import java.util.List ;
import java.time.LocalDate;
import java.util.ArrayList ; 

@RestController
public class AnalysisController {
	
	
	private static int timeSpanStart ; 
	private static int timeSpanEnd ; 
	private static Banks bank ; 
	
	@Autowired 
	private AllocationsService AllocationsService ; 

	@RequestMapping("/ajax/getAllocationsAnalysisData")
	public AnalysisCompositeModel getAllocationsAnalysisData() {
		System.out.println("analysis controller invoked");
		List<Allocations> allocationsList = this.AllocationsService.getBankAllocations(bank); 
		List<Allocations> filteredAllocations = new ArrayList<Allocations>() ; 
		List<String> allocationsYears = new ArrayList<String>() ; 
		
		for(Allocations allocation : allocationsList ) {
			if(checkDateSpan(allocation.getAllocationDate())) {
				filteredAllocations.add(allocation);
				allocationsYears.add(getYearFromStringDate(allocation.getAllocationDate()));
			}
		}
		
		List<AnalysisModel> analysisModelList = new ArrayList<AnalysisModel>();
		
		for(Allocations allocation : filteredAllocations) {
			AnalysisModel AM = new AnalysisModel(); 
			AM.setName(getYearFromStringDate(allocation.getAllocationDate()));
			List<Integer> allocationAmount = new ArrayList<Integer>(); 
			allocationAmount.add(Integer.valueOf(allocation.getAllocationAmmount()));
			AM.setData(allocationAmount);
			analysisModelList.add(AM);
		}
		
		List<String> bankList = new ArrayList<String>(); 
		bankList.add(bank.getBankName());
		AnalysisCompositeModel ACM = new AnalysisCompositeModel() ; 
		ACM.setCategories(bankList);
		ACM.setTitle("Allocations Amount for "+bank.getBankName() + " between years "+timeSpanStart+" - "+timeSpanEnd);
		ACM.setSeries(analysisModelList);
		
		return ACM ; 
	}
	
	
	

	public static void setTimeSpan(int start,int end) {
		timeSpanStart = start ; 
		timeSpanEnd = end ; 
	}
	
	public boolean checkDateSpan(String date) {
		LocalDate desiredDate = LocalDate.parse(date);
		int year = desiredDate.getYear(); 
		if(year >= timeSpanStart && year <= timeSpanEnd) {
			return true ; 
		}
		return false ; 
	}
	
	
	public void setAllocationsYears(List<String> allocationsYears ) { 
		int counter = 0 ; 
		while (true) {
			if((timeSpanStart + counter) <= timeSpanEnd) {
				allocationsYears.add(String.valueOf(timeSpanStart + counter)) ; 
				counter++ ; 
			}else {
				break ; 
			}
		}
	}




	public static int getTimeSpanStart() {
		return timeSpanStart;
	}




	public static void setTimeSpanStart(int timeSpanStart) {
		AnalysisController.timeSpanStart = timeSpanStart;
	}




	public static int getTimeSpanEnd() {
		return timeSpanEnd;
	}




	public static void setTimeSpanEnd(int timeSpanEnd) {
		AnalysisController.timeSpanEnd = timeSpanEnd;
	}




	public static Banks getBank() {
		return bank;
	}




	public static void setBank(Banks bank) {
		AnalysisController.bank = bank;
	}
	
	public String getYearFromStringDate(String date) {
		LocalDate desiredDate = LocalDate.parse(date);
		int year = desiredDate.getYear(); 
		return String.valueOf(year) ; 
	}
	
}
