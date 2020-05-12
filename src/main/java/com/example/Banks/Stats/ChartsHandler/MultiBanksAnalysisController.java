package com.example.Banks.Stats.ChartsHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MasterService;
import com.example.Allocations.Allocations;
import com.example.Allocations.AllocationsService;
import com.example.Banks.BankService;
import com.example.Banks.Banks;
import com.example.Loans.LoanService;
import com.example.Loans.Loans;
import java.util.List ; 
import java.util.ArrayList ; 


@RestController
public class MultiBanksAnalysisController {

	private static int yearSpanStart ; 
	private static int yearSpanEnd ;
	private static int monthSpanStart ; 
	private static int monthSpanEnd ;
	
	private static List<Banks> banksList = new ArrayList<Banks>(); 
	private static List<Integer> years = new ArrayList<Integer>();
	private static List<Integer> loansyears = new ArrayList<Integer>();

	
	private int[][] dataArray ;
	private int[] loansDataArray ; 
	private int[][] loansOrderData; 
	
	private boolean loansOrderReady = false;
	
	@Autowired 
	private AllocationsService allocationsService ; 

	@Autowired
	private LoanService loansService ; 
		
	@Autowired 
	private BankService banksService ; 
	
	@RequestMapping("/ajax/getMultiBankPagingAllocationsAnalysisData")
	public AnalysisCompositeModel getAllocationsNewAnalysisData() {
		int pageNum = 0 ;
		setYearsList();
		dataArray = new int[years.size()][banksList.size()]; 
		initDataArray();
		List<Integer> banksIds = new ArrayList<Integer>() ; 
		for(Banks bank : banksList ) {
			banksIds.add(bank.BankID);
		}
		Page<Allocations> allocationsPage  = this.allocationsService.getAllocationsChuck(pageNum, null); 	
		
		while(allocationsPage != null ) {
			processData(years,banksIds,allocationsPage);
			pageNum++;
			allocationsPage  = this.allocationsService.getAllocationsChuck(pageNum, allocationsPage);;
		}
		//data array is full with allocations data 
		//trim the data from zero rows
		List<Integer> tempYearList = years ; 
		trimDataArray(tempYearList);
		List<AnalysisModel> amList = new ArrayList<AnalysisModel>();
		for(int i = 0 ; i < tempYearList.size() ; i ++ ) {
			AnalysisModel Am = new AnalysisModel() ; 
			int yearVal = tempYearList.get(i);
			if(yearVal != -1 ) {
				Am.setName(String.valueOf(tempYearList.get(i)));
				for(int j = 0 ; j < banksList.size() ; j ++) {		
					Am.addDataEntry(dataArray[i][j]);
				}
				amList.add(Am);
			}
		}
		AnalysisCompositeModel ACM = new AnalysisCompositeModel() ; 
		ACM.setTitle("Banks Allocations in the period between "+yearSpanStart+"-"+yearSpanEnd);
		for(Banks bank : banksList) {
			ACM.addCat(bank.getBankName());
		}
		ACM.setSeries(amList);
		return ACM ;
	}
	

	@RequestMapping("/ajax/getMultiBankLoansAnalysisData")
	public AnalysisCompositeModel getLoansAnalysisData() {
		loansOrderReady = false;
		int pageNum = 0 ;
		setLoansYearsList();
		loansDataArray =  new int[loansyears.size()] ;  
		initLoansDataArray() ; 
		List<Integer> banksIds = new ArrayList<Integer>() ; 
		for(Banks bank : banksList ) {
			banksIds.add(bank.BankID);
		}
		loansOrderData = new int[banksIds.size()][2]; 
		initOrderArray(banksIds);
		Page<Loans> loansPage = this.loansService.getAllLoansSequence(pageNum, null);
		while(loansPage != null ) {
			processLoansData(loansPage,banksIds);
			pageNum++;
			loansPage = this.loansService.getAllLoansSequence(pageNum, loansPage);
		}
		sortOrderArray(banksIds);
		loansOrderReady = true;
		List<Integer> tempYearList = loansyears ; 
		trimLoansDataArray(tempYearList);
		List<AnalysisModel> amList = new ArrayList<AnalysisModel>();
		for(int i = 0 ; i < tempYearList.size() ; i ++ ) {
			AnalysisModel Am = new AnalysisModel() ; 
			int yearVal = tempYearList.get(i);
			if(yearVal != -1 ) {
				Am.setName(String.valueOf(tempYearList.get(i)));
				Am.addDataEntry(loansDataArray[i]);
				amList.add(Am);
			}
		}
		AnalysisCompositeModel ACM = new AnalysisCompositeModel() ; 
		ACM.setTitle("Loans given in the period between "+yearSpanStart+"-"+yearSpanEnd);
		for(int y : tempYearList) {
			if(y != -1 )
			ACM.addCat(" ");
		}
		ACM.setSeries(amList);
		return ACM ;
 
	}
	
	
	@RequestMapping("/ajax/getMultiBankLoansOrderData")
	public AnalysisCompositeModel getLoansOrderData() {
		//wait for the loans data to be processed
		while(!loansOrderReady) {
			
		}
		List<AnalysisModel> amList = new ArrayList<AnalysisModel>();
		for(int i = 0 ; i < banksList.size() ; i ++ ) {
			if(loansOrderData[i][1] == 0 ) {
				break ; 
			}
			AnalysisModel Am = new AnalysisModel() ; 
			Am.setName(banksService.getBankById(loansOrderData[i][0]).getBankName());
			Am.addDataEntry(loansOrderData[i][1]);
			amList.add(Am);
		}
		AnalysisCompositeModel ACM = new AnalysisCompositeModel() ; 
		ACM.setTitle("banks order based on loans taken in the period between "+yearSpanStart+"-"+yearSpanEnd);
		ACM.setSeries(amList);
		for(AnalysisModel am : amList) {
			ACM.addCat(" ");
		}
		return ACM ;
 
	}
	//general methods 
	
	//fill the years list with the years between yearsSpanStart and YearsSpanEnd 
	public void setYearsList() {
		int tmpYearSt = yearSpanStart; 
		while(true) {
			if(tmpYearSt > yearSpanEnd) {
				break ; 
			}else {
				MultiBanksAnalysisController.years.add(tmpYearSt);
				tmpYearSt++;
				
			}
		}
	}
	
	public void setLoansYearsList() {
		loansyears = new ArrayList<Integer>() ; 
		int tmpYearSt = yearSpanStart; 
		while(true) {
			if(tmpYearSt > yearSpanEnd) {
				break ; 
			}else {
				MultiBanksAnalysisController.loansyears.add(tmpYearSt);
				tmpYearSt++;
			}
		}
	}

	//empty all the lists for the next use so the previous data does not affect the analytics 
	public static void flushLists() {
		banksList = new ArrayList<Banks>();
		years = new ArrayList<Integer>();
	}

	//end of general methods 
	
	
	
	//allocations process methods 
	
	//map the page data into the dataArray where each column is a year and each row is a bank 
	private int[][] processData(List<Integer> years, List<Integer> banksList,
			Page<Allocations> allocationsPage) {
		for(Allocations allocation : allocationsPage.getContent()) {
			int yearIndex = years.indexOf(Integer.valueOf(MasterService.getYearFromStringDate(allocation.getAllocationDate()))) ; 
			int bankIndex = banksList.indexOf(allocation.getBank().getBankID());
			if( yearIndex != -1 && bankIndex != -1) {
				dataArray[yearIndex][bankIndex] = Integer.valueOf(allocation.getAllocationAmmount());
			}
		}
		return dataArray ; 
	}
	
	private void initDataArray() {
		for(int i = 0 ; i < years.size() ; i ++) {
			for(int j = 0 ; j < banksList.size() ; j ++ ) {
				dataArray[i][j] = -1 ; 
			}
		}
	}
	
	private void trimDataArray(List<Integer> tempYears) {
		for(int i = 0 ; i < years.size() ; i ++) {
			boolean zeroRow = true ; 
			for(int j = 0 ; j < banksList.size() ; j ++) {
				if(dataArray[i][j] != -1 ) {
					zeroRow = false ; 
				}
			}
			if(zeroRow) {
				tempYears.set(i, -1); 
			}
		}
	}
	//End Of Allocations process methods 
	
	
	
	//loans process methods 
	private void initLoansDataArray() {
		for(int i = 0 ; i < years.size() ; i++) {
			this.loansDataArray[i] = 0 ; 
		}
	}
	
	private void initOrderArray(List<Integer> banksIds) {
		for(int i = 0 ; i< banksList.size() ; i++) {
			loansOrderData[i][0] = banksIds.get(i);
			loansOrderData[i][1] = 0;
		}
	}
	
	private void processLoansData(Page<Loans> loansPage, List<Integer> banksIds) {
		for(Loans loan : loansPage.getContent()) {
			int yearIndex = years.indexOf(Integer.valueOf(MasterService.getYearFromStringDate(loan.getLoanDate())));
			int bankIndex = banksIds.indexOf(Integer.valueOf(loan.getBranche().getBank().getBankID()));
			if(yearIndex != -1 ) {
				loansDataArray[yearIndex] += Integer.valueOf(loan.TotalAmmount);
				loansOrderData[bankIndex][1] += Integer.valueOf(loan.TotalAmmount);
			}
		}
	}
	
	private void trimLoansDataArray(List<Integer> tempYearList) {
		for(int i = 0 ; i < loansyears.size() ; i ++) {
			if(loansDataArray[i] == 0 ) {
				tempYearList.set(i,-1);
			}
		}
	}

	private void sortOrderArray(List<Integer> banksIds) {
		boolean sorted = false;
		while(!sorted) {
			sorted = true ; 
		for(int j = 0 ; j < banksList.size() -1   ; j++) {
			if(loansOrderData[j][1] < loansOrderData[j+1][1]) {
					int temp = loansOrderData[j][1] ; 
					loansOrderData[j][1] =  loansOrderData[j+1][1] ; 
					loansOrderData[j+1][1]  = temp ; 
					int tmpID = loansOrderData[j][0]; 
					loansOrderData[j][0] = loansOrderData[j+1][0];
					 loansOrderData[j+1][0] = tmpID ;
					 sorted = false ; 
				}
			}
		}
	}
	//End Of loans process methods 
	
	
	
	//setters - getters 

	public static int getYearSpanStart() {
		return yearSpanStart;
	}

	public static void setYearSpanStart(int yearSpanStart) {
		MultiBanksAnalysisController.yearSpanStart = yearSpanStart;
	}

	public static int getYearSpanEnd() {
		return yearSpanEnd;
	}

	public static void setYearSpanEnd(int yearSpanEnd) {
		MultiBanksAnalysisController.yearSpanEnd = yearSpanEnd;
	}

	public static int getMonthSpanStart() {
		return monthSpanStart;
	}

	public static void setMonthSpanStart(int monthSpanStart) {
		MultiBanksAnalysisController.monthSpanStart = monthSpanStart;
	}

	public static int getMonthSpanEnd() {
		return monthSpanEnd;
	}

	public static void setMonthSpanEnd(int monthSpanEnd) {
		MultiBanksAnalysisController.monthSpanEnd = monthSpanEnd;
	}

	public static List<Banks> getBanksList() {
		return banksList;
	}

	public static void setBanksList(List<Banks> banksList) {
		MultiBanksAnalysisController.banksList = banksList;
	}

	public static List<Integer> getYears() {
		return years;
	}

	public static void setYears(List<Integer> yearss) {
		years = yearss;
	}

}
