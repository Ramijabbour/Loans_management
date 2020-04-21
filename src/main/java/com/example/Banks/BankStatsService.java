package com.example.Banks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Allocations.AllocationsService;
import com.example.CloseLoans.CloseLoanService;
import com.example.Loans.LoanService;
import com.example.OpenLoans.OpenLoansService;

@Service 
public class BankStatsService {

	@Autowired 
	private BankService bankService ; 
	
	@Autowired 
	private AllocationsService allocationsService ;
	
	@Autowired 
	private LoanService loansService ; 
	
	@Autowired
	private CloseLoanService closedLoansService ; 
	
	@Autowired 
	private OpenLoansService openLoansService ; 
	
	
	
	public BankStatsService() {
	}
	
	public StatsModel getBankStats(Banks bank) {
		StatsModel statusModel = new StatsModel(); 
		statusModel.setBank(bank);
		statusModel.setBranchesList(this.bankService.getBankBranches(bank));
		statusModel.setAllAllocations(this.allocationsService.getBankAllocations(bank));
	//	statusModel.setOverAllLoansList(this.loansService.getBankLoans(bank));
		//statusModel.setOpenLoansList(this.openLoansService.getBankOpenLoans(bank));
		//statusModel.setClosedLoansList(this.closedLoansService.getBankClosedLoans(bank));
		statusModel.calculateBankStats(); 
		return statusModel; 
	}
	
	
}
