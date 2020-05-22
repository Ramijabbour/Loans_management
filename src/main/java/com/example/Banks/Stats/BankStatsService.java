package com.example.Banks.Stats;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Allocations.AllocationsService;
import com.example.BankBranches.BrancheService;
import com.example.BankBranches.Branches;
import com.example.Banks.BankService;
import com.example.Banks.Banks;
import com.example.Banks.Stats.Models.DashModel;
import com.example.Banks.Stats.Models.StatsModel;
import com.example.Clients.ClientService;
import com.example.CloseLoans.CloseLoanService;
import com.example.CloseLoans.CloseLoans;
import com.example.Loans.LoanService;
import com.example.Loans.Loans;
import com.example.OpenLoans.OpenLoans;
import com.example.OpenLoans.OpenLoansService;
import com.example.ReScheduleLoans.ReScheduleLoansService;
import com.example.security.user.UserService;

@Service 
public class BankStatsService {
	@Autowired 
	private AllocationsService allocationsService ;

	@Autowired
	private CloseLoanService closedLoansService ; 
	
	@Autowired 
	private OpenLoansService openLoansService ; 
	
	@Autowired 
	private BrancheService brachesService ; 
	
	@Autowired 
	private BankService banksService; 
	
	@Autowired 
	private ClientService clintsService ; 
	
	@Autowired 
	private UserService usersService ; 
	
	@Autowired
	private ReScheduleLoansService ResService ; 
	
	@Autowired
	private LoanService LoansService ; 
	
	
	public BankStatsService() {
	}
	
	public StatsModel getBankStats(Banks bank) {
		StatsModel statusModel = new StatsModel(); 
		statusModel.setBank(bank);
		statusModel.setBranchesList(this.brachesService.getBankBranches(bank));
		statusModel.setAllAllocations(this.allocationsService.getBankAllocations(bank));
		
		List<OpenLoans> openLoansList = new ArrayList<OpenLoans>(); 
		List<CloseLoans> closedLoansList = new ArrayList<CloseLoans>() ; 
		List<Loans> allLoansList = new ArrayList<Loans>() ; 
		
		for(Branches branch :statusModel.getBranchesList()) {
			for(OpenLoans loan : this.openLoansService.getBrancheOpenLoans(branch)) {
				openLoansList.add(loan);
				allLoansList.add(loan.getLoan());
			}
			for(CloseLoans closedLoan : this.closedLoansService.getBrancheClosedLoans(branch)) {
				closedLoansList.add(closedLoan);
				allLoansList.add(closedLoan.getLoan());
			}
		}
		statusModel.setOpenLoansList(openLoansList);
		statusModel.setClosedLoansList(closedLoansList);
		statusModel.setOverAllLoansList(allLoansList);
		
		statusModel.calculateBankStats(); 
		return statusModel; 
	}
	
	
	public DashModel getDashDataObject() {
		DashModel DM = new DashModel() ; 
		DM.setTotalBanksCount(this.banksService.getBanksCount());
		DM.setTotalClientsCount(this.clintsService.getClientsCount());
		DM.setClosedLoansCount(this.closedLoansService.getClosedLoansCount());
		DM.setOpenLoansCount(this.openLoansService.getOpenLoansCount());
		DM.setTotalBranchesCount(this.brachesService.getBranchesCount());
		DM.setTotalSystemUsers(this.usersService.getUsersCount());
		DM.setResLoansCount(this.ResService.getResLoansCount());
		DM.setTotalLoansCount(this.LoansService.getLoansCount());
		DM.setLoansValue(this.LoansService.getTotalLoansValue());
		DM.setClosedLoansValue(this.closedLoansService.getTotalLoansValue());
		DM.setOpenLoansValue(this.openLoansService.getTotalLoansValue());
		DM.setResLoansValue(this.ResService.getTotalLoansValue());
		return DM; 
	}
	
	
}
