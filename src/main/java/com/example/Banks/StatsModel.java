package com.example.Banks;

import java.util.List ;

import com.example.Allocations.Allocations;
import com.example.BankBranches.Branches;
import com.example.CloseLoans.CloseLoans;
import com.example.Loans.Loans;
import com.example.OpenLoans.OpenLoans;

import java.util.ArrayList ; 

public class StatsModel {

	private Banks Bank ; 
	private int BranchesCount = 0 ; 
	private List<Branches> BranchesList ; 

	private List<Allocations> AllAllocations ; 
	
	private List<Loans> OverAllLoansList ;
	
	private List<CloseLoans> ClosedLoansList ; 
	
	private List<OpenLoans> OpenLoansList ;
	
	private double TotalAllocations = 0  ; 	
	private int SumAllocations = 0  ; 
	private int SumAllLoans = 0 ; 
	private int SumOpenLoans = 0 ; 
	private int SumClosedLoans = 0 ; 
	private double TotalLoansValue = 0 ; 
	
	private float ClosedPercentage = 0 ; 
	private float OpenPercentage = 0 ;
	
	public StatsModel() {
		this.BranchesList = new ArrayList<Branches>(); 
		this.AllAllocations = new ArrayList<Allocations>();
		this.OverAllLoansList = new ArrayList<Loans>();
		this.ClosedLoansList = new ArrayList<CloseLoans>();
		this.OpenLoansList = new ArrayList<OpenLoans>();
		this.TotalAllocations = 0 ; 
	}

	public Banks getBank() {
		return Bank;
	}



	public void setBank(Banks bank) {
		Bank = bank;
	}



	public int getBranchesCount() {
		return BranchesCount;
	}



	public void setBranchesCount(int branchesCount) {
		BranchesCount = branchesCount;
	}



	public List<Branches> getBranchesList() {
		return BranchesList;
	}



	public void setBranchesList(List<Branches> branchesList) {
		BranchesList = branchesList;
	}



	public List<Allocations> getAllAllocations() {
		return AllAllocations;
	}



	public void setAllAllocations(List<Allocations> allAllocations) {
		AllAllocations = allAllocations;
	}



	public List<Loans> getOverAllLoansList() {
		return OverAllLoansList;
	}



	public void setOverAllLoansList(List<Loans> overAllLoansList) {
		OverAllLoansList = overAllLoansList;
	}



	public List<CloseLoans> getClosedLoansList() {
		return ClosedLoansList;
	}



	public void setClosedLoansList(List<CloseLoans> list) {
		ClosedLoansList = list;
	}



	public List<OpenLoans> getOpenLoansList() {
		return OpenLoansList;
	}



	public void setOpenLoansList(List<OpenLoans> list) {
		OpenLoansList = list;
	}



	public double getTotalAllocations() {
		return TotalAllocations;
	}



	public void setTotalAllocations(double totalAllocations) {
		TotalAllocations = totalAllocations;
	}



	public int getSumAllocations() {
		return SumAllocations;
	}



	public void setSumAllocations(int sumAllocations) {
		SumAllocations = sumAllocations;
	}



	public int getSumAllLoans() {
		return SumAllLoans;
	}



	public void setSumAllLoans(int sumAllLoans) {
		SumAllLoans = sumAllLoans;
	}



	public int getSumOpenLoans() {
		return SumOpenLoans;
	}



	public void setSumOpenLoans(int sumOpenLoans) {
		SumOpenLoans = sumOpenLoans;
	}



	public int getSumClosedLoans() {
		return SumClosedLoans;
	}



	public void setSumClosedLoans(int sumClosedLoans) {
		SumClosedLoans = sumClosedLoans;
	}



	public double getTotalLoansValue() {
		return TotalLoansValue;
	}



	public void setTotalLoansValue(double totalLoansValue) {
		TotalLoansValue = totalLoansValue;
	}



	public float getClosedPercentage() {
		return ClosedPercentage;
	}



	public void setClosedPercentage(float closedPercentage) {
		ClosedPercentage = closedPercentage;
	}



	public float getOpenPercentage() {
		return OpenPercentage;
	}



	public void setOpenPercentage(float openPercentage) {
		OpenPercentage = openPercentage;
	}



	public void calculateBankStats() {
		this.SumAllocations = this.AllAllocations.size() ; 	
		this.SumAllLoans = this.OverAllLoansList.size();
		this.SumOpenLoans = this.OpenLoansList.size() ; 
		this.SumClosedLoans = this.ClosedLoansList.size(); 
		if(this.SumAllLoans != 0 ) {
		this.ClosedPercentage = this.SumClosedLoans/this.SumAllLoans*100; 
		this.OpenPercentage = this.SumOpenLoans/this.SumAllLoans*100;
		}
		this.BranchesCount = this.BranchesList.size();
		for(Allocations allocation : this.AllAllocations) {
			this.TotalAllocations += Double.valueOf(allocation.getAllocationAmmount());
		}
		
		for(Loans loan : this.OverAllLoansList) {
			this.TotalLoansValue += Double.valueOf(loan.getTotalAmmount());	
		}
	}
	
}
