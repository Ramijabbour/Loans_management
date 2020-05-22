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
import com.example.Banks.Banks;
import com.example.Banks.Stats.Models.AnalysisCompositeModel;
import com.example.Banks.Stats.Models.AnalysisModel;
import com.example.CloseLoans.CloseLoanService;
import com.example.CloseLoans.CloseLoans;
import com.example.Loans.LoanService;
import com.example.Loans.Loans;
import com.example.OpenLoans.OpenLoans;
import com.example.OpenLoans.OpenLoansService;
<<<<<<< HEAD
=======
import com.example.SiteConfig.MasterService;
>>>>>>> d4df773b7b6dd2cd6942fadf54cdbef5074a8a5a

import java.util.List ;
import java.time.LocalDate;
import java.util.ArrayList ; 

@RestController
public class SingleBankAnalysisController {
	
	//essential attributes  
	private static int timeSpanStart ; 
	private static int timeSpanEnd ; 
	private static Banks bank ; 
	
	@Autowired 
	private AllocationsService AllocationsService ; 

	@Autowired
	private LoanService loansService ; 
	
	@Autowired 
	private OpenLoansService openLoansService ;
	
	@Autowired 
	private CloseLoanService closedLoansService ; 
	
	
	//========================================================================
	
	//data formation methods // the routes are called by the JS script inside the Charts view	
	
	
	@RequestMapping("/ajax/getAllocationsAnalysisData")
	public AnalysisCompositeModel getAllocationsAnalysisData() {
		List<Allocations> allocationsList = this.AllocationsService.getBankAllocations(bank); 
		List<Allocations> filteredAllocations = new ArrayList<Allocations>() ; 
		List<String> allocationsYears = new ArrayList<String>() ; 
		
		for(Allocations allocation : allocationsList ) {
			if(checkDateSpan(allocation.getAllocationDate())) {
				filteredAllocations.add(allocation);
				allocationsYears.add(MasterService.getYearFromStringDate(allocation.getAllocationDate()));
			}
		}
		
		List<AnalysisModel> analysisModelList = new ArrayList<AnalysisModel>();
		
		for(Allocations allocation : filteredAllocations) {
			AnalysisModel AM = new AnalysisModel(); 
			AM.setName(MasterService.getYearFromStringDate(allocation.getAllocationDate()));
			List<Integer> allocationAmount = new ArrayList<Integer>(); 
			allocationAmount.add(Integer.valueOf(allocation.getAllocationAmmount()));
			AM.setData(allocationAmount);
			analysisModelList.add(AM);
		}
		System.out.println("years size : "+allocationsYears.size());
		List<String> bankList = new ArrayList<String>(); 
		bankList.add(bank.getBankName());
		AnalysisCompositeModel ACM = new AnalysisCompositeModel() ; 
		ACM.setCategories(bankList);
		ACM.setTitle("Allocations Amount for "+bank.getBankName() + " between years "+timeSpanStart+" - "+timeSpanEnd);
		ACM.setSeries(analysisModelList);
		
		return ACM ; 
	
	}
	
	@RequestMapping("/ajax/getLoansAnalysisData")
	public AnalysisCompositeModel getLoansAnalysisData() {
		int pageNum = 0 ;
		Page<Loans> loansPage = this.loansService.getAllLoansSequence(pageNum, null);
		List<Loans> filteredLoansList = new ArrayList<Loans>() ; 
		List<String> loansYears = new ArrayList<String>() ; 
		while(loansPage != null) {
			for(Loans loan : loansPage.getContent()) {
				if(loan.getBranche().getBank().getBankName().equalsIgnoreCase(bank.getBankName()))
			if(checkDateSpan(loan.getLoanDate())) {
				filteredLoansList.add(loan);
				loansYears.add(MasterService.getYearFromStringDate(loan.getLoanDate()));
				}
			}
			pageNum++ ; 
			loansPage = this.loansService.getAllLoansSequence(pageNum, loansPage);
		}
		List<AnalysisModel> analysisModelList = new ArrayList<AnalysisModel>();
		
		for(Loans loan : filteredLoansList) {
			AnalysisModel AM = new AnalysisModel(); 
			AM.setName(MasterService.getYearFromStringDate(loan.getLoanDate()));
			List<Integer> loansAmount = new ArrayList<Integer>(); 
			loansAmount.add(Integer.valueOf(loan.getTotalAmmount()));
			AM.setData(loansAmount);
			analysisModelList.add(AM);
		}
		
		List<String> bankList = new ArrayList<String>(); 
		bankList.add(bank.getBankName());
		AnalysisCompositeModel ACM = new AnalysisCompositeModel() ; 
		ACM.setCategories(bankList);
		ACM.setTitle("Loans Amount for "+bank.getBankName() + " between years "+timeSpanStart+" - "+timeSpanEnd);
		ACM.setSeries(analysisModelList);
		
		return ACM ; 
	}
	
	@RequestMapping("/ajax/getOpenLoansAnalysisData")
	public AnalysisCompositeModel getOpenLoansAnalysisData() {
		
		int pageNum = 0 ;
		Page<OpenLoans> loansPage = this.openLoansService.getAllLoansSequence(pageNum, null);
		List<Loans> filteredLoansList = new ArrayList<Loans>() ; 
		List<String> loansYears = new ArrayList<String>() ; 
		while(loansPage != null) {
			for(OpenLoans loan : loansPage.getContent()) {
				if(loan.getLoan().getBranche().getBank().getBankName().equalsIgnoreCase(bank.getBankName()))
			if(checkDateSpan(loan.getLoan().getLoanDate())) {
				filteredLoansList.add(loan.getLoan());
				loansYears.add(MasterService.getYearFromStringDate(loan.getLoan().getLoanDate()));
				}
			}
			pageNum++ ; 
			loansPage = this.openLoansService.getAllLoansSequence(pageNum, loansPage);
		}
		List<AnalysisModel> analysisModelList = new ArrayList<AnalysisModel>();
		
		for(Loans loan : filteredLoansList) {
			AnalysisModel AM = new AnalysisModel(); 
			AM.setName(MasterService.getYearFromStringDate(loan.getLoanDate()));
			List<Integer> loansAmount = new ArrayList<Integer>(); 
			loansAmount.add(Integer.valueOf(loan.getTotalAmmount()));
			AM.setData(loansAmount);
			analysisModelList.add(AM);
		}
		
		List<String> bankList = new ArrayList<String>(); 
		bankList.add(bank.getBankName());
		AnalysisCompositeModel ACM = new AnalysisCompositeModel() ; 
		ACM.setCategories(bankList);
		ACM.setTitle("Value of Open loans for "+bank.getBankName() + " between years "+timeSpanStart+" - "+timeSpanEnd);
		ACM.setSeries(analysisModelList);
		
		return ACM ; 
	}
	
	@RequestMapping("/ajax/getClosedLoansAnalysisData")
	public AnalysisCompositeModel getClosedLoansAnalysisData() {
		int pageNum = 0 ;
		Page<CloseLoans> loansPage = this.closedLoansService.getAllLoansSequence(pageNum, null);
		List<Loans> filteredLoansList = new ArrayList<Loans>() ; 
		List<String> loansYears = new ArrayList<String>() ; 
		while(loansPage != null) {
			for(CloseLoans loan : loansPage.getContent()) {
				if(loan.getLoan().getBranche().getBank().getBankName().equalsIgnoreCase(bank.getBankName()))
			if(checkDateSpan(loan.getLoan().getLoanDate())) {
				filteredLoansList.add(loan.getLoan());
				loansYears.add(MasterService.getYearFromStringDate(loan.getLoan().getLoanDate()));
				}
			}
			pageNum++ ; 
			loansPage = this.closedLoansService.getAllLoansSequence(pageNum, loansPage);
		}
		List<AnalysisModel> analysisModelList = new ArrayList<AnalysisModel>();
		
		for(Loans loan : filteredLoansList) {
			AnalysisModel AM = new AnalysisModel(); 
			AM.setName(MasterService.getYearFromStringDate(loan.getLoanDate()));
			List<Integer> loansAmount = new ArrayList<Integer>(); 
			loansAmount.add(Integer.valueOf(loan.getTotalAmmount()));
			AM.setData(loansAmount);
			analysisModelList.add(AM);
		}
		
		List<String> bankList = new ArrayList<String>(); 
		bankList.add(bank.getBankName());
		AnalysisCompositeModel ACM = new AnalysisCompositeModel() ; 
		ACM.setCategories(bankList);
		ACM.setTitle("Value of cloed Loans for "+bank.getBankName() + " between years "+timeSpanStart+" - "+timeSpanEnd);
		ACM.setSeries(analysisModelList);
		
		return ACM ; 
	}
	
	
	
	//=========================================================================

	
	//setters - getters - data check methods 
	
	public static void setTimeSpan(int start,int end) {
		timeSpanStart = start ; 
		timeSpanEnd = end ; 
	}
	
	public boolean checkDateSpan(String date) {
		LocalDate desiredDate = LocalDate.parse(date);
		int year = desiredDate.getYear(); 
		if(year >= timeSpanStart && year <= timeSpanEnd) {
			return true ; 
		}
		return false ; 
	}
		
	
	public static int getTimeSpanStart() {
		return timeSpanStart;
	}

	public static void setTimeSpanStart(int timeSpanStart) {
		SingleBankAnalysisController.timeSpanStart = timeSpanStart;
	}

	public static int getTimeSpanEnd() {
		return timeSpanEnd;
	}

	public static void setTimeSpanEnd(int timeSpanEnd) {
		SingleBankAnalysisController.timeSpanEnd = timeSpanEnd;
	}

	public static Banks getBank() {
		return bank;
	}

	public static void setBank(Banks bank) {
		SingleBankAnalysisController.bank = bank;
	}


	
	
	//===========================================================================
}
