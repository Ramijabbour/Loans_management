package com.example.settelmets.Analytics;

import java.util.HashMap;

import com.example.settelmets.Models.Chaque;
import com.example.settelmets.Models.SettledChaque;


public class AnalyticsModel {
	
	private int NumberOfChecksPerDay = 0 ; 
	
	private int NumberOfChecksPerMonth  = 0; 
	
	private int NumberOfChecksPerYear  = 0; 
	
	
	private long ValueOfChecksPerDay  = 0; 
	
	private long ValueOfChecksPerMonth  = 0; 
	
	private long ValueOfChecksPerYear  = 0;
	
	
	private long DailyExchangeValue  = 0; 
	
	private long MonthlyExchangeValue  = 0; 
	
	private long YearExchangeValue  = 0; 
	

	private int BanksCount  = 0;
	
	
	private HashMap<String, Long> MostBanksYear = new HashMap<String, Long>();
	
	private HashMap<String, Long> MostBanksMonth = new HashMap<String, Long>();
	
	
	public AnalyticsModel() {

	}

	
	public void addYearCheck(Chaque check) {
		NumberOfChecksPerYear++;
		ValueOfChecksPerYear += check.getAmount() ; 
		String bankName = check.getSecondBankName() ; 
		if(MostBanksYear.containsKey(bankName)) {
			MostBanksYear.put(bankName,MostBanksYear.get(bankName)+check.getAmount());
		}else {
			MostBanksYear.put(bankName,check.getAmount());
		}
		
	} 
	

	public void addMonthCheck(Chaque check) {
		NumberOfChecksPerMonth++;
		ValueOfChecksPerMonth += check.getAmount() ; 
		String bankName = check.getSecondBankName() ; 
		if(MostBanksMonth.containsKey(bankName)) {
			MostBanksMonth.put(bankName,MostBanksMonth.get(bankName)+check.getAmount());
		}else {
			MostBanksMonth.put(bankName,check.getAmount());
		}
	}
	
	
	public void addDayCheck(Chaque check) {
		NumberOfChecksPerDay++;
		ValueOfChecksPerDay += check.getAmount() ; 
	}	
	
	
	public void addYearSettledCheck(SettledChaque settledCheck) {
		YearExchangeValue += settledCheck.getAmount() ; 
	}


	public void addMonthSettledCheck(SettledChaque settledCheck) {
		MonthlyExchangeValue += settledCheck.getAmount();
	}


	public void addDaySettledCheck(SettledChaque settledCheck) {
		DailyExchangeValue += settledCheck.getAmount();
	}
	
	
	
	
	
	
	
	public int getNumberOfChecksPerDay() {
		return NumberOfChecksPerDay;
	}

	public void setNumberOfChecksPerDay(int numberOfChecksPerDay) {
		NumberOfChecksPerDay = numberOfChecksPerDay;
	}

	public int getNumberOfChecksPerMonth() {
		return NumberOfChecksPerMonth;
	}

	public void setNumberOfChecksPerMonth(int numberOfChecksPerMonth) {
		NumberOfChecksPerMonth = numberOfChecksPerMonth;
	}

	public int getNumberOfChecksPerYear() {
		return NumberOfChecksPerYear;
	}

	public void setNumberOfChecksPerYear(int numberOfChecksPerYear) {
		NumberOfChecksPerYear = numberOfChecksPerYear;
	}

	public long getValueOfChecksPerDay() {
		return ValueOfChecksPerDay;
	}

	public void setValueOfChecksPerDay(int valueOfChecksPerDay) {
		ValueOfChecksPerDay = valueOfChecksPerDay;
	}

	public long getValueOfChecksPerMonth() {
		return ValueOfChecksPerMonth;
	}

	public void setValueOfChecksPerMonth(int valueOfChecksPerMonth) {
		ValueOfChecksPerMonth = valueOfChecksPerMonth;
	}

	public long getValueOfChecksPerYear() {
		return ValueOfChecksPerYear;
	}

	public void setValueOfChecksPerYear(int valueOfChecksPerYear) {
		ValueOfChecksPerYear = valueOfChecksPerYear;
	}

	public long getDailyExchangeValue() {
		return DailyExchangeValue;
	}

	public void setDailyExchangeValue(long dailyExchangeValue) {
		DailyExchangeValue = dailyExchangeValue;
	}

	public long getMonthlyExchangeValue() {
		return MonthlyExchangeValue;
	}

	public void setMonthlyExchangeValue(long monthlyExchangeValue) {
		MonthlyExchangeValue = monthlyExchangeValue;
	}

	public long getYearExchangeValue() {
		return YearExchangeValue;
	}

	public void setYearExchangeValue(long yearExchangeValue) {
		YearExchangeValue = yearExchangeValue;
	}

	public int getBanksCount() {
		return BanksCount;
	}

	public void setBanksCount(int banksCount) {
		BanksCount = banksCount;
	}


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

	
}
