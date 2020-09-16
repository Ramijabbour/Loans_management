package com.example.Banks.Stats;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ServicesPool;
import com.example.BankBranches.Branches;
import com.example.Banks.Banks;
import com.example.Banks.Stats.Models.DashModel;
import com.example.Banks.Stats.Models.StatsModel;
import com.example.CloseLoans.CloseLoans;
import com.example.Loans.Loans;
import com.example.OpenLoans.OpenLoans;

@Service 
public class BankStatsService {
	
	@Autowired
	private ServicesPool servicePool ; 
	
	public BankStatsService() {
	}
	
	public StatsModel getBankStats(Banks bank) {
		StatsModel statusModel = new StatsModel(); 
		statusModel.setBank(bank);
		statusModel.setBranchesList(servicePool.getBranchesService().getBankBranches(bank));
		statusModel.setAllAllocations(servicePool.getAllocationsService().getBankAllocations(bank));
		
		List<OpenLoans> openLoansList = new ArrayList<OpenLoans>(); 
		List<CloseLoans> closedLoansList = new ArrayList<CloseLoans>() ; 
		List<Loans> allLoansList = new ArrayList<Loans>() ; 
		
		for(Branches branch :statusModel.getBranchesList()) {
			for(OpenLoans loan : servicePool.getOpenLoansService().getBrancheOpenLoans(branch)) {
				openLoansList.add(loan);
				allLoansList.add(loan.getLoan());
			}
			for(CloseLoans closedLoan : servicePool.getClosedLoansService().getBrancheClosedLoans(branch)) {
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
		DM.setTotalBanksCount(servicePool.getBankService().getBanksCount());
		DM.setTotalClientsCount(servicePool.getClientService().getClientsCount());
		DM.setClosedLoansCount(servicePool.getClosedLoansService().getClosedLoansCount());
		DM.setOpenLoansCount(servicePool.getOpenLoansService().getOpenLoansCount());
		DM.setTotalBranchesCount(servicePool.getBranchesService().getBranchesCount());
		//DM.setTotalSystemUsers(this.usersService.getUsersCount());
		DM.setTotalSystemUsers(5);
		DM.setResLoansCount(servicePool.getResLoansService().getResLoansCount());
		DM.setTotalLoansCount(servicePool.getLoansService().getLoansCount());
		DM.setLoansValue(servicePool.getLoansService().getTotalLoansValue());
		DM.setClosedLoansValue(servicePool.getClosedLoansService().getTotalLoansValue());
		DM.setOpenLoansValue(servicePool.getOpenLoansService().getTotalLoansValue());
		DM.setResLoansValue(servicePool.getResLoansService().getTotalLoansValue());
		return DM; 
	}
	
	
}
