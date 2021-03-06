package com.example.Banks.Stats.Handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ServicesPool;
import com.example.Allocations.Allocations;
import com.example.Banks.Banks;
import com.example.Banks.Stats.Models.AnalysisCompositeModel;
import com.example.Banks.Stats.Models.AnalysisModel;
import com.example.Loans.Loans;
import com.example.SiteConfig.MasterService;

@RestController
public class MultiBanksSingleYearAnalysisController {	
	//Attributes section 
	/*
	private static int year ; 
	private static int MonthStart ; 
	private static int MonthEnd ;
	
	private static List<Banks> BanksList = new ArrayList<Banks>(); 
	private static List<Integer> MonthsList = new ArrayList<Integer>();
	private int[] AllocationsDataArray ; 
	private int[] LoansDataArray ; 
	private int[] financeTypes ; 
	private int[][] loansOrderData;
	private int[] loansCount ; 
	private int[] intrestRateDataArray ; 
	
	private boolean loansOrderReady = false;
	private boolean financeOrderReady = false ;
	private boolean intrestDataReady = false ; 
	private boolean loansCountReady = false; 
	//End Of attributes section
	
	//Autowired Section 

	@Autowired 
	private ServicesPool servicePool ; 
	
	
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
		Page<Allocations> allocationsPage  = servicePool.getAllocationsService().getAllocationsChuck(pageNum, null); 	
		
		while(allocationsPage != null ) {
			processAllocationsDataArray(allocationsPage,banksIds);
			pageNum++;
			allocationsPage  = servicePool.getAllocationsService().getAllocationsChuck(pageNum, allocationsPage);;
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
		ACM.setTitle("مخصصات البنوك في سنة "+year);
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
		intrestDataReady = false ; 
		loansCountReady = false ; 
		financeTypes = new int[3];
		financeTypes[0] = 0 ;financeTypes[1] = 0 ;financeTypes[2] = 0 ;
		int pageNum = 0 ;
		initMonthsList();
		LoansDataArray = new int[MonthsList.size()];
		loansCount = new int[MonthsList.size()];
		intrestRateDataArray = new int[MonthsList.size()]; 
		initLoansDataArray();
		initLoansCountArray();
		initIntrestDataArray();
		List<Integer> banksIds = new ArrayList<Integer>() ; 
		for(Banks bank : BanksList ) {
			banksIds.add(bank.BankID);
		}
		loansOrderData = new int[banksIds.size()][2];
		initOrderArray(banksIds);
		Page<Loans> loansPage = servicePool.getLoansService().getAllLoansSequence(pageNum, null);
		while(loansPage != null ) {
			processLoansData(loansPage,banksIds);
			pageNum++;
			loansPage = servicePool.getLoansService().getAllLoansSequence(pageNum, loansPage);
		}
		sortOrderArray(banksIds);
		//to sync other methods 
		financeOrderReady = true;
		loansOrderReady = true;
		intrestDataReady = true ; 
		loansCountReady = true ; 
		List<AnalysisModel> amList = new ArrayList<AnalysisModel>();
		for(int i = 0 ; i < MonthsList.size() ; i ++ ) {
				AnalysisModel Am = new AnalysisModel() ; 
				Am.setName(String.valueOf(MonthsList.get(i)));
				Am.addDataEntry(LoansDataArray[i]);
				amList.add(Am);
		}
		AnalysisCompositeModel ACM = new AnalysisCompositeModel() ; 
		ACM.setTitle("قيم السلف الممنوحة في  "+getQuarterOrder()+" من سنة "+year);
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
		ACM.setTitle("سنب التمويل على السلف في  "+getQuarterOrder()+" من سنة "+year);
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
		for(int i = 0 ; i < BanksList.size() ; i ++ ) {
			System.out.println(loansOrderData[i][0] + " " + loansOrderData[i][1]);
		}
		List<AnalysisModel> amList = new ArrayList<AnalysisModel>();
		for(int i = 0 ; i < BanksList.size() ; i ++ ) {
			if(loansOrderData[i][1] == 0 ) {
				continue  ; 
			}
			AnalysisModel Am = new AnalysisModel() ; 
			Am.setName(servicePool.getBankService().getBankById(loansOrderData[i][0]).getBankName());
			Am.addDataEntry(loansOrderData[i][1]);
			amList.add(Am);
		}
		AnalysisCompositeModel ACM = new AnalysisCompositeModel() ; 
		ACM.setTitle("البنوك الأكثر طلبا للسلف في  "+getQuarterOrder()+" من سنة "+year);
		ACM.setSeries(amList);
		for(AnalysisModel am : amList) {
			ACM.addCat(" ");
		}
		loansOrderReady = false;
		return ACM ;
 
	}
	
	@RequestMapping("/ajax/getSingleYearIntrestData")
	public AnalysisCompositeModel getSingleYearIntrestData() {
		while(!intrestDataReady) {}
		this.processIntrestRateData();
		List<AnalysisModel> amList = new ArrayList<AnalysisModel>() ; 
		for(int i = 0 ; i < MonthsList.size() ; i ++) {
			AnalysisModel am = new AnalysisModel() ; 
			am.setName(String.valueOf(MonthsList.get(i)));
			am.addDataEntry(intrestRateDataArray[i]);
			amList.add(am);
		}
		AnalysisCompositeModel ACM = new AnalysisCompositeModel() ; 
		ACM.setTitle("متوسط نسب الفائدة على السلف في "+getQuarterOrder() + " من سنة "+year);
		ACM.setSeries(amList);
		ACM.addCat(" ");
		intrestDataReady = false ; 
		return ACM; 
	}
	
	@RequestMapping("/ajax/getSingleYearLoansCountData")
	public AnalysisCompositeModel getSingleYearLoansCounttData() {
		while(!loansCountReady) {}
		List<AnalysisModel> amList = new ArrayList<AnalysisModel>(); 
		for(int i = 0 ; i < MonthsList.size(); i ++) {
			AnalysisModel Am = new AnalysisModel() ; 
			Am.setName(String.valueOf(MonthsList.get(i)));
			Am.addDataEntry(this.loansCount[i]);
			amList.add(Am);
		}
		AnalysisCompositeModel ACM = new AnalysisCompositeModel() ; 
		ACM.setTitle("عدد السلف الممنوحة في  "+getQuarterOrder()+" من سنة "+year);
		ACM.setSeries(amList);
		ACM.addCat(" ");
		loansCountReady = false ; 
		return ACM ;   
	}
	
	//End Of routes section 
	
	//data process section 

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
							loansCount[monthIndex]++ ; 
							intrestRateDataArray[monthIndex] =+ Integer.valueOf(loan.getInterestRate());
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
	
	private void processIntrestRateData() {
		for(int i = 0 ; i < MonthsList.size() ; i ++) {
			if(this.loansCount[i] == 0 ) {
				this.intrestRateDataArray[i] = 0 ; 
				continue ; 
			}
			this.intrestRateDataArray[i] = Math.round(this.intrestRateDataArray[i] / this.loansCount[i]);
		}
	}

	//end of data process section 
	

	
	//service methods 
	
	public static void flushLists() {
		 BanksList = new ArrayList<Banks>(); 
		 MonthsList = new ArrayList<Integer>();	
	}
	
	public String getQuarterOrder() {
		switch(MonthStart) {
		case 1 :{
			return "الربع الأول";
		}case 4 :{
			return "الربع الثاني";
		}case 7 :{
			return "الربع الثالث";
		}case 10 :{
			return "الربع الرابع";
		}
		}
		return ""; 
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
	
	//end of service methods 
	
	//init data arrays 
	
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
	
	private void intiAllocationsDataArray() {
		for(int i = 0 ; i < BanksList.size() ; i ++ ) {
			this.AllocationsDataArray[i] = 0 ; 
		}
		
	}
	
	private void initOrderArray(List<Integer> banksIds) {
		for(int i = 0 ; i< BanksList.size() ; i++) {
			loansOrderData[i][0] = banksIds.get(i);
			loansOrderData[i][1] = 0;
		}
	}
	
	private void initLoansCountArray() {
		for(int i = 0 ; i < MonthsList.size() ; i++) {
			loansCount[i] = 0 ;
		}
		
	}

	private void initIntrestDataArray() {
		for(int i = 0 ; i < MonthsList.size() ; i++) {
			intrestRateDataArray[i] = 0 ; 
		}
		
	}
	
	//end of init data arrays 
	
	
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
	public static void addBanksToBanksList(Banks bank) {
		for(Banks bankk : BanksList) {
			if(bankk.getBankID() == bank.getBankID()) {
				return  ;
				}
			}
		BanksList.add(bank);
	}
	
	public static boolean containsBank(Banks bank ) {
		for(Banks bankk : BanksList) {
			if(bankk.getBankID() == bank.getBankID()) {
				return true ; 
			}
		}
		return false ; 
	}
	//End Of setters - getters section 
	*/
}
