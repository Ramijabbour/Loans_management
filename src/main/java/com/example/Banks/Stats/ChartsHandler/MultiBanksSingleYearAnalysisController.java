package com.example.Banks.Stats.ChartsHandler;

import java.util.ArrayList;
import java.util.List;

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

@RestController
public class MultiBanksSingleYearAnalysisController {	
	//Attributes section 
	
	private static int year ; 
	private static int MonthStart ; 
	private static int MonthEnd ;
	
	private static List<Banks> BanksList = new ArrayList<Banks>(); 
	private static List<Integer> MonthsList = new ArrayList<Integer>();
	private int[] AllocationsDataArray ; 
	private int[] LoansDataArray ; 
	private int[] financeTypes ; 
	private int[][] loansOrderData;
	
	private boolean loansOrderReady = false;
	private boolean financeOrderReady = false ; 
	//End Of attributes section
	
	//Autowired Section 
	
	@Autowired 
	private AllocationsService allocationsService ; 

	@Autowired
	private LoanService loansService ; 
		
	@Autowired 
	private BankService banksService ;
	//End Of Autowired Section 
	
	//routes section 
	
	@RequestMapping("/ajax/getSingleYearMultiBankAllocationsData")
	public AnalysisCompositeModel getAllocationsAnalysisData() {
		int pageNum = 0 ;
		AllocationsDataArray = new int[BanksList.size()];
		intiAllocationsDataArray();
		List<Integer> banksIds = new ArrayList<Integer>() ; 
		for(Banks bank : BanksList ) {
			banksIds.add(bank.BankID);
		}
		Page<Allocations> allocationsPage  = this.allocationsService.getAllocationsChuck(pageNum, null); 	
		
		while(allocationsPage != null ) {
			processAllocationsDataArray(allocationsPage,banksIds);
			pageNum++;
			allocationsPage  = this.allocationsService.getAllocationsChuck(pageNum, allocationsPage);;
		}
		//data array is full with allocations data 
		//trim the data from zero rows
		List<AnalysisModel> amList = new ArrayList<AnalysisModel>();
		for(int i = 0 ; i < BanksList.size() ; i ++ ) {
			AnalysisModel Am = new AnalysisModel() ; 
			Am.setName(BanksList.get(i).getBankName());
			Am.addDataEntry(AllocationsDataArray[i]);
			amList.add(Am);
		}
		AnalysisCompositeModel ACM = new AnalysisCompositeModel() ; 
		ACM.setTitle("Banks Allocations in "+year);
		for(Banks bank : BanksList) {
			ACM.addCat(bank.getBankName());
		}
		ACM.setSeries(amList);
		return ACM ;
	}
	
	@RequestMapping("/ajax/getSingleYearMultiBankLoansData")
	public AnalysisCompositeModel getLoansAnalysisData() {
		loansOrderReady = false;
		financeOrderReady = false ; 
		financeTypes = new int[3];
		financeTypes[0] = 0 ;financeTypes[1] = 0 ;financeTypes[2] = 0 ;
		int pageNum = 0 ;
		initMonthsList();
		LoansDataArray = new int[MonthsList.size()];
		initLoansDataArray();
		List<Integer> banksIds = new ArrayList<Integer>() ; 
		for(Banks bank : BanksList ) {
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
		financeOrderReady = true;
		loansOrderReady = true;
		List<AnalysisModel> amList = new ArrayList<AnalysisModel>();
		for(int i = 0 ; i < MonthsList.size() ; i ++ ) {
				AnalysisModel Am = new AnalysisModel() ; 
				Am.setName(String.valueOf(MonthsList.get(i)));
				Am.addDataEntry(LoansDataArray[i]);
				amList.add(Am);
		}
		AnalysisCompositeModel ACM = new AnalysisCompositeModel() ; 
		ACM.setTitle("Loans given in the "+getQuarterOrder()+" of "+year);
		for(int y : MonthsList) {
			ACM.addCat(" ");
		}
		ACM.setSeries(amList);
		return ACM ; 
	}
	
	@RequestMapping("/ajax/getSingleYearMultiBankFinanceOrderData")
	public AnalysisCompositeModel getLoansFinanceData() {
		while(!financeOrderReady) {	}
		processFinanceData() ; 
		List<AnalysisModel> amList = new ArrayList<AnalysisModel>();
		int counter = 0 ; 
		while(counter < 3 ) {
			AnalysisModel Am = new AnalysisModel() ; 
			String fType = "";
			switch(counter) {
			case 0 : {
				fType = "مواسم استراتيجية";
				Am.setName(fType);
				Am.addDataEntry(financeTypes[0]);
				break ; 
			}
			case 1 : {
				fType = "طويل الامد";
				Am.setName(fType);
				Am.addDataEntry(financeTypes[1]);
				break ; 
			}
			case 2 : {
				fType = "قصير الامد";
				Am.setName(fType);
				Am.addDataEntry(financeTypes[2]);
				break;
			}
			}
			amList.add(Am);
			counter++; 
		}
		AnalysisCompositeModel ACM = new AnalysisCompositeModel() ; 
		ACM.setTitle("Loans Finance Types ratio in "+getQuarterOrder()+" of "+year);
		ACM.setSeries(amList);
		ACM.addCat(" ");
		financeOrderReady = false ; 
		return ACM ;
 
	}

	@RequestMapping("/ajax/getSingleYearMultiBankLoansOrderData")
	public AnalysisCompositeModel getLoansOrderData() {
		//wait for the loans data to be processed
		while(!loansOrderReady) {
			
		}
		List<AnalysisModel> amList = new ArrayList<AnalysisModel>();
		for(int i = 0 ; i < BanksList.size() ; i ++ ) {
			if(loansOrderData[i][1] == 0 ) {
				break ; 
			}
			AnalysisModel Am = new AnalysisModel() ; 
			Am.setName(banksService.getBankById(loansOrderData[i][0]).getBankName());
			Am.addDataEntry(loansOrderData[i][1]);
			amList.add(Am);
		}
		AnalysisCompositeModel ACM = new AnalysisCompositeModel() ; 
		ACM.setTitle("banks order based on loans taken in "+getQuarterOrder()+" of "+year);
		ACM.setSeries(amList);
		for(AnalysisModel am : amList) {
			ACM.addCat(" ");
		}
		loansOrderReady = false;
		return ACM ;
 
	}
	
	
	//End Of routes section 
	
	//data process section 
	
	private void initLoansDataArray() {
		for(int j = 0 ; j < MonthsList.size() ; j ++) {
				LoansDataArray[j] = 0 ; 
		}
	}
	
	private void initMonthsList() {
		MonthsList = new ArrayList<Integer>() ; 
		int mCounter = MonthStart ;
		while(mCounter < MonthEnd +1 ) {
			MonthsList.add(mCounter);
			mCounter++ ; 
		}
	}
	
	private void processLoansData(Page<Loans> loansPage, List<Integer> banksIds) {
		for(Loans loan : loansPage.getContent() ) {
			int loanMonth =Integer.valueOf(MasterService.getMonthFromStringDate(loan.getLoanDate())) ;
			int loanYear = Integer.valueOf(MasterService.getYearFromStringDate(loan.getLoanDate())) ;
			int bankIndex = banksIds.indexOf(loan.getBranche().getBank().getBankID());
			int monthIndex = MonthsList.indexOf(loanMonth);
			if(bankIndex != -1 && monthIndex != -1 ) {		 
				if( loanYear == year ) {
					if(loanMonth >= MonthStart ) {
						if(loanMonth <= MonthEnd) {
							loansOrderData[bankIndex][1] += Integer.valueOf(loan.TotalAmmount);
							LoansDataArray[monthIndex] += Integer.valueOf(loan.getTotalAmmount());
							if(loan.getFinanceType().getTypeName().equalsIgnoreCase("مواسم استراتيجية")) {
								financeTypes[0]++ ; 
							}else if (loan.getFinanceType().getTypeName().equalsIgnoreCase("طويل الامد")) {
								financeTypes[1]++; 
							}else {
								financeTypes[2]++;
							}
						}
					}
				}
			}
		}
		
	}

	private void intiAllocationsDataArray() {
		for(int i = 0 ; i < BanksList.size() ; i ++ ) {
			this.AllocationsDataArray[i] = 0 ; 
		}
		
	}
	
	private void processAllocationsDataArray(Page<Allocations> allocationsPage,List<Integer> banksIdsList) {
		for(Allocations allocation : allocationsPage.getContent()) {
			int bankIndex = banksIdsList.indexOf(allocation.getBank().getBankID());
			if(bankIndex != -1 ) {
				if(Integer.valueOf(MasterService.getYearFromStringDate(allocation.getAllocationDate())) == year ) {
					AllocationsDataArray[bankIndex]+= Integer.valueOf(allocation.getAllocationAmmount());
				}
			}
		}
	}

	private void processFinanceData() {
		int total = financeTypes[0]+financeTypes[1]+financeTypes[2] ; 
		if(total != 0 ) {
		financeTypes[0] = Math.round(financeTypes[0]*100/total);
		financeTypes[1] = Math.round(financeTypes[1]*100/total);
		financeTypes[2] = Math.round(financeTypes[2]*100/total);
		}else {
			financeTypes[0] = 0;
			financeTypes[1] = 0;
			financeTypes[2] = 0;
		}
	}
	
	private void initOrderArray(List<Integer> banksIds) {
		for(int i = 0 ; i< BanksList.size() ; i++) {
			loansOrderData[i][0] = banksIds.get(i);
			loansOrderData[i][1] = 0;
		}
	}
	
	private void sortOrderArray(List<Integer> banksIds) {
		boolean sorted = false;
		while(!sorted) {
			sorted = true ; 
		for(int j = 0 ; j < BanksList.size() -1   ; j++) {
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
	
	//service methods 
	
	public static void flushLists() {
		 BanksList = new ArrayList<Banks>(); 
		 MonthsList = new ArrayList<Integer>();	
	}
	
	public String getQuarterOrder() {
		switch(MonthStart) {
		case 1 :{
			return "Q1";
		}case 4 :{
			return "Q2";
		}case 7 :{
			return "Q3";
		}case 10 :{
			return "Q4";
		}
		}
		return ""; 
	}
	
	//End Of data process section  
	
	
	
	//setters - getters section 
	
	public static int getYear() {
		return year;
	}
	public static void setYear(int year) {
		MultiBanksSingleYearAnalysisController.year = year;
	}
	public static int getMonthStart() {
		return MonthStart;
	}
	public static void setMonthStart(int monthStart) {
		MonthStart = monthStart;
	}
	public static int getMonthEnd() {
		return MonthEnd;
	}
	public static void setMonthEnd(int monthEnd) {
		MonthEnd = monthEnd;
	}
	public static List<Banks> getBanksList() {
		return BanksList;
	}
	public static void setBanksList(List<Banks> banksList) {
		BanksList = banksList;
	} 
	
	//End Of setters - getters section 
	
}
