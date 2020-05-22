package com.example.Banks.Stats.Handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD
import com.example.MasterService;
=======
>>>>>>> d4df773b7b6dd2cd6942fadf54cdbef5074a8a5a
import com.example.Allocations.Allocations;
import com.example.Allocations.AllocationsService;
import com.example.Banks.BankService;
import com.example.Banks.Banks;
import com.example.Banks.Stats.Models.AnalysisCompositeModel;
import com.example.Banks.Stats.Models.AnalysisModel;
import com.example.Loans.LoanService;
import com.example.Loans.Loans;
<<<<<<< HEAD
=======
import com.example.SiteConfig.MasterService;

>>>>>>> d4df773b7b6dd2cd6942fadf54cdbef5074a8a5a
import java.util.List ; 
import java.util.ArrayList ; 


@RestController
public class MultiBanksAnalysisController {

	
	//Attributes section 
	private static int yearSpanStart ; 
	private static int yearSpanEnd ;

	
	private static List<Banks> banksList = new ArrayList<Banks>(); 
	private static List<Integer> years = new ArrayList<Integer>();
	private static List<Integer> loansyears = new ArrayList<Integer>();

	
	private int[][] dataArray ;
	private int[] loansDataArray ; 
	private int[][] loansOrderData; 
	private int[] financeTypes ; 
	private int[] loansCount ; 
	private int[] intrestRateDataArray ; 
	private boolean loansOrderReady = false;
	private boolean financeOrderReady = false ; 
	private boolean intrestDataReady = false ; 
	private boolean loansCountReady = false; 
	//End of Attributes section  
	
	
	//Autowired Services 
	@Autowired 
	private AllocationsService allocationsService ; 

	@Autowired
	private LoanService loansService ; 
		
	@Autowired 
	private BankService banksService ; 
	
	
	//End Of Autowired Services  
	
	
	//routing section : 
	
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
		ACM.setTitle("قيم المخصصات الممنوحة في الفترة بين  "+yearSpanStart+"-"+yearSpanEnd);
		for(Banks bank : banksList) {
			ACM.addCat(bank.getBankName());
		}
		ACM.setSeries(amList);
		return ACM ;
	}
	

	@RequestMapping("/ajax/getMultiBankLoansAnalysisData")
	public AnalysisCompositeModel getLoansAnalysisData() {
		loansOrderReady = false;
		financeOrderReady = false ; 
		int pageNum = 0 ;
		setLoansYearsList();
		loansDataArray =  new int[loansyears.size()] ;  
		financeTypes = new int[3];
		financeTypes[0] = 0 ;financeTypes[1] = 0 ;financeTypes[2] = 0 ;
		loansCount = new int[loansyears.size()];
		intrestRateDataArray = new int[loansyears.size()];
		initIntrestDataArray();
		initLoansCountArray();
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
		//to sync other methods that relay on processing loans data 
		loansOrderReady = true;
		financeOrderReady = true; 
		intrestDataReady = true ; 
		loansCountReady = true ; 
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
		ACM.setTitle("قيم السلف الممنوحة في الفترة بين "+yearSpanStart+"-"+yearSpanEnd);
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
		ACM.setTitle("البنوك الأكثر طلبا للسلف في الفترة بين "+yearSpanStart+"-"+yearSpanEnd);
		ACM.setSeries(amList);
		for(AnalysisModel am : amList) {
			ACM.addCat(" ");
		}
		loansOrderReady = false;
		return ACM ;
 
	}
	
	
	@RequestMapping("/ajax/getMultiBankFinanceOrderData")
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
		ACM.setTitle("نسب التمويل للسلف في الفترة بين "+yearSpanStart+"-"+yearSpanEnd);
		ACM.setSeries(amList);
		ACM.addCat(" ");
		financeOrderReady = false ; 
		return ACM ;
 
	}
	
	
	@RequestMapping("/ajax/getIntrestRateData")
	public AnalysisCompositeModel getIntrestRateData() {
		while(!intrestDataReady) {}
		processIntrestRateData();
		List<AnalysisModel> amList = new ArrayList<AnalysisModel>(); 
		for(int i = 0 ; i < loansyears.size() ; i ++ ) {
			if(loansyears.get(i) != -1) {
			AnalysisModel Am = new AnalysisModel() ; 
			Am.setName(String.valueOf(loansyears.get(i)));
			Am.addDataEntry(this.intrestRateDataArray[i]);
			amList.add(Am);
			}
		}
		
		AnalysisCompositeModel ACM = new AnalysisCompositeModel() ; 
		ACM.setTitle("متوسط معدل الفائدة على السلف في السنوات "+yearSpanStart + "-" + yearSpanEnd);
		ACM.setSeries(amList);
		ACM.addCat(" ");
		intrestDataReady = false ; 
		return ACM ; 
	}
	
	@RequestMapping("/ajax/getLoanCountData")
	public AnalysisCompositeModel getLoansCountData() {
		while(!loansCountReady) {}
		
		List<AnalysisModel> amList = new ArrayList<AnalysisModel>(); 
		for(int i = 0 ; i < loansyears.size(); i ++) {
			if(loansyears.get(i) != -1) {
			AnalysisModel Am = new AnalysisModel() ; 
			Am.setName(String.valueOf(loansyears.get(i)));
			Am.addDataEntry(this.loansCount[i]);
			amList.add(Am);
			}
		}
		AnalysisCompositeModel ACM = new AnalysisCompositeModel() ; 
		ACM.setTitle("عدد السلف الممنوحة في كل سنة بين "+yearSpanStart+"-"+yearSpanEnd);
		ACM.setSeries(amList);
		ACM.addCat(" ");
		loansCountReady = false ; 
		return ACM ;  
	}
	
	//End Of routing section 


	//general methods 
	
	//empty all the lists for the next use so the previous data does not affect the analytics 
	public static void flushLists() {
		banksList = new ArrayList<Banks>();
		years = new ArrayList<Integer>();
<<<<<<< HEAD
=======
		loansyears = new ArrayList<Integer>() ; 
>>>>>>> d4df773b7b6dd2cd6942fadf54cdbef5074a8a5a
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
	
	//end of general methods 
	
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

	
	//fill the years list with the years between yearsSpanStart and YearsSpanEnd 
	public void setYearsList() {
<<<<<<< HEAD
=======
			years = new ArrayList<Integer>(); 
>>>>>>> d4df773b7b6dd2cd6942fadf54cdbef5074a8a5a
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
	//End Of loans process methods 

	
	//arrays init section 
	
	private void initDataArray() {
		for(int i = 0 ; i < years.size() ; i ++) {
			for(int j = 0 ; j < banksList.size() ; j ++ ) {
				dataArray[i][j] = -1 ; 
			}
		}
	}
	
	private void initLoansCountArray() {
		for(int i = 0 ; i < loansyears.size() ; i++) {
			loansCount[i] = 0 ;
		}
		
	}

	private void initIntrestDataArray() {
		for(int i = 0 ; i < loansyears.size() ; i++) {
			intrestRateDataArray[i] = 0 ; 
		}
		
	}

	private void initLoansDataArray() {
<<<<<<< HEAD
=======
		System.out.println("initLoansDataArray "+years.size());
>>>>>>> d4df773b7b6dd2cd6942fadf54cdbef5074a8a5a
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
	
	//End of arrays init section
	
	
	//process section 
	
	private void processIntrestRateData() {
		for(int i = 0 ; i < loansyears.size() ; i ++ ) {
			if(this.loansCount[i] == 0 ) {
				this.intrestRateDataArray[i] = 0 ; 
				continue ; 
			}
			this.intrestRateDataArray[i] = Math.round(this.intrestRateDataArray[i] / this.loansCount[i]);  
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
	
	private void processLoansData(Page<Loans> loansPage, List<Integer> banksIds) {
		for(Loans loan : loansPage.getContent()) {
			int yearIndex = years.indexOf(Integer.valueOf(MasterService.getYearFromStringDate(loan.getLoanDate())));
			int bankIndex = banksIds.indexOf(Integer.valueOf(loan.getBranche().getBank().getBankID()));
			if(yearIndex != -1 ) {
				if(bankIndex != -1) {
				loansDataArray[yearIndex] += Integer.valueOf(loan.TotalAmmount);
				loansOrderData[bankIndex][1] += Integer.valueOf(loan.TotalAmmount);
				loansCount[yearIndex]++;
				intrestRateDataArray[yearIndex]+= Integer.valueOf(loan.getInterestRate());
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

	//End of process section 
	
	
	
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

	public static void addBankToBanksList(Banks bank) {
		for(Banks bankk : banksList) {
			if(bankk.getBankID() == bank.getBankID()) {
				return ; 
			}
		}
		banksList.add(bank);
	}
	
	public static boolean containsBank(Banks bank ) {
		for(Banks bankk : banksList) {
			if(bankk.getBankID() == bank.getBankID())
				return true;
		}
		return false ; 
	}
}
