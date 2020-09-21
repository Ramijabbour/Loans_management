package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Allocations.AllocationsService;
import com.example.BankBranches.BrancheService;
import com.example.Banks.BankService;
import com.example.Clients.ClientService;
import com.example.CloseLoans.CloseLoanService;
import com.example.FinanceType.FinanceTypeService;
import com.example.Loans.LoanService;
import com.example.LoansType.LoansTypeService;
import com.example.OpenLoans.OpenLoansService;
import com.example.ReScheduleLoans.ReScheduleLoansService;
import com.example.ReportsAndContracts.ReportService;
import com.example.ReportsAndContracts.ReportsLoanService;
import com.example.Vouchers.VoucherService;

@Service
public class ServicesPool {

	@Autowired
	private AllocationsService allocationsService ; 
	
	@Autowired
	private BrancheService branchesService ; 
	
	@Autowired
	private BankService bankService ; 
	
	@Autowired
	private ClientService clientService ; 
	
	@Autowired
	private CloseLoanService closedLoansService ; 
	
	@Autowired
	private FinanceTypeService financeTypeService ; 
	
	@Autowired
	private LoanService loansService ; 
	
	@Autowired
	private LoansTypeService loanTypesService ; 
	
	@Autowired
	private OpenLoansService openLoansService ; 
	
	@Autowired
	private ReportsLoanService reportsLoansService ;
	
	@Autowired
	private ReScheduleLoansService resLoansService ;
	
	@Autowired
	private VoucherService voucherService ;
	
	@Autowired
	private ReportService reportsService ; 
	
	
	public AllocationsService getAllocationsService() {
		return allocationsService;
	}

	public void setAllocationsService(AllocationsService allocationsService) {
		this.allocationsService = allocationsService;
	}

	public BrancheService getBranchesService() {
		return branchesService;
	}

	public void setBranchesService(BrancheService branchesService) {
		this.branchesService = branchesService;
	}

	public BankService getBankService() {
		return bankService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public ClientService getClientService() {
		return clientService;
	}

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}

	public CloseLoanService getClosedLoansService() {
		return closedLoansService;
	}

	public void setClosedLoansService(CloseLoanService closedLoansService) {
		this.closedLoansService = closedLoansService;
	}

	public FinanceTypeService getFinanceTypeService() {
		return financeTypeService;
	}

	public void setFinanceTypeService(FinanceTypeService financeTypeService) {
		this.financeTypeService = financeTypeService;
	}

	public LoanService getLoansService() {
		return loansService;
	}

	public void setLoansService(LoanService loansService) {
		this.loansService = loansService;
	}

	public LoansTypeService getLoanTypesService() {
		return loanTypesService;
	}

	public void setLoanTypesService(LoansTypeService loanTypesService) {
		this.loanTypesService = loanTypesService;
	}

	public OpenLoansService getOpenLoansService() {
		return openLoansService;
	}

	public void setOpenLoansService(OpenLoansService openLoansService) {
		this.openLoansService = openLoansService;
	}

	public ReportsLoanService getReportsLoansService() {
		return reportsLoansService;
	}

	public void setReportsLoansService(ReportsLoanService reportsLoansService) {
		this.reportsLoansService = reportsLoansService;
	}

	public ReScheduleLoansService getResLoansService() {
		return resLoansService;
	}

	public void setResLoansService(ReScheduleLoansService resLoansService) {
		this.resLoansService = resLoansService;
	}

	public VoucherService getVoucherService() {
		return voucherService;
	}

	public void setVoucherService(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	public ReportService getReportsService() {
		return reportsService;
	}

	public void setReportsService(ReportService reportsService) {
		this.reportsService = reportsService;
	} 
	
	

}
