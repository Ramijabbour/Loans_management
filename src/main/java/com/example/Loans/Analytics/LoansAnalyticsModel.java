package com.example.Loans.Analytics;

import java.util.HashMap;
import java.util.List;

import com.example.Loans.Loans;


public class LoansAnalyticsModel {
	
	
	private HashMap<String, Float> FinanaceTypesYear = new HashMap<String, Float>();
	
	private HashMap<String, Float> FinanaceTypesMonth = new HashMap<String, Float>();
	
	
	private HashMap<String, Float> LoansTypesYear = new HashMap<String, Float>();
	
	private HashMap<String, Float> LoansTypesMonth = new HashMap<String, Float>();
	
	
	
	private HashMap<String, Long> MostBanksYear = new HashMap<String, Long>();
	
	private HashMap<String, Long> MostBanksMonth = new HashMap<String, Long>();
	
	
	private HashMap<String, Integer> BankLoansCountYear = new HashMap<String, Integer>();
	
	private HashMap<String, Integer> BankLoansCountMonth = new HashMap<String, Integer>();

	
	private HashMap<String, Long> BanksAllocationsYear = new HashMap<String, Long>();


	private String yearOfAnalytics;

	private String MonthOfAnalytics;
	
	private int loansCounterYear = 0 ; 
	
	private int loansCounterMonth = 0 ;
	
	
	public LoansAnalyticsModel(){
		FinanaceTypesYear.put("مواسم استراتيجية", 0f);FinanaceTypesYear.put("طويل الامد", 0f);FinanaceTypesYear.put("قصير الامد", 0f);
		FinanaceTypesMonth.put("مواسم استراتيجية", 0f);FinanaceTypesMonth.put("طويل الامد", 0f);FinanaceTypesMonth.put("قصير الامد", 0f);
		LoansTypesYear.put("مرخص", 0f);LoansTypesYear.put("معفى", 0f);
		LoansTypesMonth.put("مرخص", 0f);LoansTypesMonth.put("معفى", 0f);
	}
	

	//process 
	
	public void calculateYearData(List<Loans> loansOfThisYear) {
		loansCounterYear += loansOfThisYear.size(); 
		for(Loans loan : loansOfThisYear){
			FinanaceTypesYear.put(loan.getFinanceType().getTypeName(), FinanaceTypesYear.get(loan.getFinanceType().getTypeName())+1);
			LoansTypesYear.put(loan.getLoanType().getTypeName(),LoansTypesYear.get(loan.getLoanType().getTypeName())+1);
			
			String bankName = loan.getBranche().getBank().getBankName() ; 
			
			if(MostBanksYear.containsKey(bankName)) {
				long currentAmout = MostBanksYear.get(bankName);
				currentAmout += Long.valueOf(loan.getTotalAmmount());
				MostBanksYear.put(bankName, currentAmout);
				
				int currLoansCounter = BankLoansCountYear.get(bankName);
				currLoansCounter++; 
				BankLoansCountYear.put(bankName,currLoansCounter);
				
			}else {
				long amount = Long.valueOf(loan.getTotalAmmount());
				MostBanksYear.put(bankName, amount);
				BankLoansCountYear.put(bankName,1);
			}
		
		}		
	}


	public void calculateMonthData(List<Loans> loansOfThisYear) {
		for(Loans loan : loansOfThisYear){
			if(loan.getLoanMonth().equalsIgnoreCase(MonthOfAnalytics)) {
				loansCounterMonth ++ ; 
				
				String finanaceType = loan.getFinanceType().getTypeName() ;  
				FinanaceTypesMonth.put(finanaceType,FinanaceTypesMonth.get(finanaceType)+1);
				
				String loanType = loan.getLoanType().getTypeName();
				LoansTypesMonth.put(loanType, LoansTypesMonth.get(loanType)+1);
				
	
				String bankName = loan.getBranche().getBank().getBankName();
				if(MostBanksMonth.containsKey(bankName)) {
					long currentAmount = MostBanksMonth.get(bankName);
					currentAmount += Long.valueOf(loan.getTotalAmmount());
					MostBanksMonth.put(bankName, currentAmount);
					int currCounter = BankLoansCountMonth.get(bankName);
					currCounter++;
					BankLoansCountMonth.put(bankName, currCounter);
				}else {
					long amount = Long.valueOf(loan.getTotalAmmount());
					MostBanksMonth.put(bankName, amount);
					BankLoansCountMonth.put(bankName, 1);
				}
				
			}			
		}	
	}
	
	
	
	public void calcRatios() {
		
		float ratio = ( FinanaceTypesYear.get("مواسم استراتيجية")/loansCounterYear)*100;
		FinanaceTypesYear.put("مواسم استراتيجية", ratio);
		
		ratio = ( FinanaceTypesYear.get("طويل الامد")/loansCounterYear)*100;
		FinanaceTypesYear.put("طويل الامد",ratio);
		
		ratio = ( FinanaceTypesYear.get("قصير الامد")/loansCounterYear)*100;
		FinanaceTypesYear.put("قصير الامد",ratio );
		
		ratio = ( LoansTypesYear.get("مرخص")/loansCounterYear)*100;
		LoansTypesYear.put("مرخص", ratio);
		
		ratio = ( LoansTypesYear.get("معفى")/loansCounterYear)*100;
		LoansTypesYear.put("معفى",ratio );
		
		
		ratio = ( FinanaceTypesMonth.get("مواسم استراتيجية")/loansCounterMonth)*100;
		FinanaceTypesMonth.put("مواسم استراتيجية", ratio);
		
		ratio = ( FinanaceTypesMonth.get("طويل الامد")/loansCounterMonth)*100;
		FinanaceTypesMonth.put("طويل الامد", ratio );
		
		ratio = ( FinanaceTypesMonth.get("قصير الامد")/loansCounterMonth)*100;
		FinanaceTypesMonth.put("قصير الامد", ratio );
		
		ratio = ( LoansTypesMonth.get("مرخص")/loansCounterMonth)*100;
		LoansTypesMonth.put("مرخص",ratio );
		
		ratio = ( LoansTypesMonth.get("معفى")/loansCounterMonth)*100;
		LoansTypesMonth.put("معفى",ratio );
		
	}
	
	//getters - setters 

	public HashMap<String, Long> getMostBanksYear() {
		return MostBanksYear;
	}


	public void setMostBanksYear(HashMap<String, Long> mostBanksYear) {
		MostBanksYear = mostBanksYear;
	}


	public HashMap<String, Long> getMostBanksMonth() {
		return MostBanksMonth;
	}


	public void setMostBanksMonth(HashMap<String, Long> mostBanksMonth) {
		MostBanksMonth = mostBanksMonth;
	}

	public HashMap<String, Long> getBanksAllocationsYear() {
		return BanksAllocationsYear;
	}


	public void setBanksAllocationsYear(HashMap<String, Long> banksAllocationsYear) {
		BanksAllocationsYear = banksAllocationsYear;
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



	public HashMap<String, Float> getFinanaceTypesYear() {
		return FinanaceTypesYear;
	}



	public void setFinanaceTypesYear(HashMap<String, Float> finanaceTypesYear) {
		FinanaceTypesYear = finanaceTypesYear;
	}



	public HashMap<String, Float> getFinanaceTypesMonth() {
		return FinanaceTypesMonth;
	}



	public void setFinanaceTypesMonth(HashMap<String, Float> finanaceTypesMonth) {
		FinanaceTypesMonth = finanaceTypesMonth;
	}



	public HashMap<String, Float> getLoansTypesYear() {
		return LoansTypesYear;
	}



	public void setLoansTypesYear(HashMap<String, Float> loansTypesYear) {
		LoansTypesYear = loansTypesYear;
	}



	public HashMap<String, Float> getLoansTypesMonth() {
		return LoansTypesMonth;
	}



	public void setLoansTypesMonth(HashMap<String, Float> loansTypesMonth) {
		LoansTypesMonth = loansTypesMonth;
	}



	public HashMap<String, Integer> getBankLoansCountYear() {
		return BankLoansCountYear;
	}



	public void setBankLoansCountYear(HashMap<String, Integer> bankLoansCountYear) {
		BankLoansCountYear = bankLoansCountYear;
	}



	public HashMap<String, Integer> getBankLoansCountMonth() {
		return BankLoansCountMonth;
	}



	public void setBankLoansCountMonth(HashMap<String, Integer> bankLoansCountMonth) {
		BankLoansCountMonth = bankLoansCountMonth;
	}



	public int getLoansCounterYear() {
		return loansCounterYear;
	}



	public void setLoansCounterYear(int loansCounterYear) {
		this.loansCounterYear = loansCounterYear;
	}



	public int getLoansCounterMonth() {
		return loansCounterMonth;
	}



	public void setLoansCounterMonth(int loansCounterMonth) {
		this.loansCounterMonth = loansCounterMonth;
	}
		
	
	
}
