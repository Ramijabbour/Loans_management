package com.example.Loans;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.SiteConfiguration;
import com.example.BankBranches.BrancheService;
import com.example.BankBranches.Branches;
import com.example.Banks.Banks;
import com.example.Clients.ClientService;
import com.example.Clients.Clients;
import com.example.CloseLoans.CloseLoanService;
import com.example.CloseLoans.CloseLoans;
import com.example.FinanceType.FinanceType;
import com.example.FinanceType.FinanceTypeService;
import com.example.LoansType.LoansType;
import com.example.LoansType.LoansTypeService;
import com.example.OpenLoans.OpenLoans;
import com.example.OpenLoans.OpenLoansService;
import com.example.ReScheduleLoans.ReScheduleLoans;
import com.example.ReScheduleLoans.ReScheduleLoansService;
import com.example.Vouchers.VoucherController;
import com.example.Vouchers.VoucherService;
import com.example.Vouchers.Vouchers;

@RestController
public class LoansController {

	@Autowired
	LoanService loansService;
	@Autowired 
	ClientService clientService; 
	@Autowired 
	BrancheService brancheService ;
	@Autowired 
	LoansTypeService loansTypeService;
	@Autowired 
	FinanceTypeService financeTypeService ; 
	
	@Autowired
	OpenLoansService openLoanService; 
	@Autowired
	CloseLoanService closeLoanService ;
	@Autowired
	ReScheduleLoansService reScheduleLoanService;
	
	@Autowired
	VoucherService voucherService;
	
	@Autowired 
	VoucherController voucherController ; 
	
	//get All Open Loans ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/all/Open")
	public ModelAndView ShowAllOpenLoans(@Param(value ="index") int index) { 
		ModelAndView mav = new ModelAndView("Loans/AllOpenLoans");
		List<OpenLoans> allOpenloans=openLoanService.GetAllOpenLoan(index);
		mav.addObject("allloans",allOpenloans);
		if(allOpenloans.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav; 
	}
	// -------------------------------------------------------------------

	//get All Close Loans ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/all/Close")
	public ModelAndView ShowAllCloseLoans(@Param(value ="index") int index) { 
		ModelAndView mav = new ModelAndView("Loans/AllCloseLoan");
		List<CloseLoans> allCloseloans=closeLoanService.GetAllCloseLoan(index);
		mav.addObject("allloans",allCloseloans);
		if(allCloseloans.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav; 
	}
	// -------------------------------------------------------------------
	
	//get All ReSchedule Loans ------------------------------------------------------
		@RequestMapping(method = RequestMethod.GET , value="/Loans/all/ReSchedule")
		public ModelAndView ShowAllReScheduleLoans(@Param(value ="index") int index) { 
			ModelAndView mav = new ModelAndView("Loans/AllReScheduleLoan");
			List<ReScheduleLoans> allReScheduleloans=reScheduleLoanService.getAllReScheduleLoans(index);
			mav.addObject("allloans",allReScheduleloans);
			if(allReScheduleloans.size() > 0 ) {
				SiteConfiguration.addSequesnceVaraibles(mav, index);
			}else {
				SiteConfiguration.addSequesnceVaraibles(mav, -1);
			}
			return mav; 
		}
	// -------------------------------------------------------------------

	
	//add loan ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/addLoan")
	public ModelAndView AddLoan() { 
		ModelAndView mav = new ModelAndView("Loans/AddLoan");
		List<Clients> allclient = clientService.GetAllClientsNoPage();
		List<Branches> allbranche=brancheService.getAllBrancheNoPage();
		List<LoansType> allloanType = loansTypeService.getAllType();
		List<FinanceType> allfinanceType= financeTypeService.getAllFinanceType();
	    mav.addObject("allclient",allclient);
	    mav.addObject("loan",new Loans());
	    mav.addObject("allbranche",allbranche);
	    mav.addObject("allloantype",allloanType);
	    mav.addObject("allfinancetype",allfinanceType);
		return mav; 
	}
	
	@Transactional
	@RequestMapping(method = RequestMethod.POST , value="/Loans/addLoan")
	public ModelAndView addNewLoan(@ModelAttribute Loans loan,HttpServletResponse response) throws IOException {
	
		Banks bank=loan.getBranche().getBank();
		
		int Amount=Integer.parseInt(loan.getNetAmmount());
		int bankAlloacation  =Integer.parseInt(bank.getFinancialAllocations());
		int NewAllocation=bankAlloacation-Amount;
		
		if(NewAllocation>=0) {
			System.out.println("posted to /Loans/addLoan "); 
			bank.setFinancialAllocations(Integer.toString(NewAllocation));
			loansService.addLoan(loan);	
		    OpenLoans ol =new OpenLoans(loan);
		    openLoanService.addOpenLoan(ol);
		    return this.voucherController.addVoucherSequence(loan.getId(),Integer.valueOf(loan.getNumberOfVoucher()));
			//response.sendRedirect("/Loans/all/Open");
		}
			
		
		else {
			System.out.println("NullPointerException Handled at loan Service / Add loan -- call for low allocation");
			//response.sendRedirect("/Loans/addLoan/Error");
			ModelAndView mav = new ModelAndView("Loans/Error");
			return mav; 
		}
	}
	//ReSchedule Loan-----------------------------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/ReSchedule/{id}")
	public ModelAndView ShowRescheduleLoan(@PathVariable int id)
	{
		ModelAndView mav = new ModelAndView("Loans/ReSchedule");
		Loans loan=loansService.getOneByID(id);
		mav.addObject("loan",loan);
		return mav ;
		
		
	}
	@RequestMapping(method = RequestMethod.POST , value="/Loans/ReSchedule/{id}")
	public void RescheduleLoan(@ModelAttribute Loans loan,@PathVariable int id, HttpServletResponse response) throws IOException
	{
		Loans oldLoan=loansService.getOneByID(id);
		
		OpenLoans open = openLoanService.getOpenLoanFromLoan(id);
		openLoanService.DeleteOpenLoan(open);
		
		CloseLoans close = new CloseLoans(oldLoan);
		close.setStatus("مرسوم لنقل السلفة الى السلف المجدولة");
		closeLoanService.addLoan(close);
		
		
		Loans NewLoan=new Loans(oldLoan.getName(),oldLoan.getFirstSide(),oldLoan.getSecondSide(),oldLoan.getLoanNumber(),oldLoan.getInterestRate(),oldLoan.getDelayInterestRate(),oldLoan.getClearanceNumber(),oldLoan.getTotalAmmount(),oldLoan.getTotalAmmountAsString()
		,oldLoan.getNetAmmount(),oldLoan.getNetAmmountAsString(),oldLoan.getNumberOfVoucherAsString(),oldLoan.getNumberOfVoucher(),oldLoan.getPurpose(),oldLoan.getClient(),oldLoan.getBranche(),oldLoan.getUser(),oldLoan.getLoanType(),oldLoan.getFinanceType());
		NewLoan.setLoanDate(loan.getLoanDate());
		Date currentDate = new Date();
		NewLoan.setWorkDate(currentDate.toString());
		loansService.addLoan(NewLoan);
		
		ReScheduleLoans scheduleLoan=new ReScheduleLoans(NewLoan);
		reScheduleLoanService.addLoan(scheduleLoan);
		
		List<Vouchers> loanVouchers=voucherService.getVoucherForThisLoan(id);
		
		
		response.sendRedirect("/Loans/all/ReSchedule?index=0");
	}
	//Allocation small -------------------------------------------------------------------
	
	
	@RequestMapping(method = RequestMethod.GET , value="/Loans/addLoan/Error")
	public ModelAndView ShowErrorLoans() { 
		ModelAndView mav = new ModelAndView("Loans/Error");
		return mav; 
	}
	// -------------------------------------------------------------------

	//display loan ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/Loan/{id}")
	public ModelAndView ShowLoan(@PathVariable int id) { 
		ModelAndView mav = new ModelAndView("Loans/oneLoan");
		  Loans l = loansService.getOneByID(id);
		  boolean checkLoan=false ;
		  OpenLoans o = openLoanService.getOpenLoanFromLoan(id);
		  if(o!=null)
			  checkLoan=true;
		  
		  mav.addObject("checkLoan", checkLoan);
		  mav.addObject("loan",l);
		return mav; 
	}
	// -------------------------------------------------------------------
	
	// update Loan--------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/edit/{id}")
	public ModelAndView ShowUpdateLoan(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("Loans/UpdateLoan");
		Loans loan=loansService.getOneByID(id);
		List<Clients> allclient = clientService.GetAllClientsNoPage();
		List<Branches> allbranche=brancheService.getAllBrancheNoPage();
		List<LoansType> allloanType = loansTypeService.getAllType();
		List<FinanceType> allfinanceType= financeTypeService.getAllFinanceType();
		mav.addObject("allbranche",allbranche);
		mav.addObject("loan",loan);
		mav.addObject("allclient",allclient);
	    mav.addObject("allloantype",allloanType);
	    mav.addObject("allfinancetype",allfinanceType);
		return mav; 
	} 
	

	@RequestMapping(method = RequestMethod.POST , value="/Loans/update/{id}")
	public void UpdateLoan(@Valid Loans loan,HttpServletResponse response) throws IOException {
		System.out.println("posted to /Loans/update/id ");
		loansService.updateLoan(loan);
		if(openLoanService.getOpenLoanFromLoan(loan.getId())!=null)
			response.sendRedirect("/Loans/all/Open?index=0");
		else 
			response.sendRedirect("/Loans/all/Close?index=0");
	}
	

	
	//----------------------------------------------------------------
	
	//delete Loan ----------------------------------------------------------  
	@RequestMapping(method = RequestMethod.GET, value="/Loans/delete/{id}")
	public void deleteLoan(@PathVariable int id,HttpServletResponse response) throws IOException
	{
		loansService.DeleteLoan(id);
		
		if(openLoanService.getOpenLoanFromLoan(id)!=null)
			response.sendRedirect("/Loans/all/Open");
		else 
			response.sendRedirect("/Loans/all/Close");
	}
	
	@RequestMapping(method = RequestMethod.GET , value="/Loans/CheckCloseLoan/{id}")
	public void CheckCloseLoan(@PathVariable int id ,HttpServletResponse response) throws IOException
	{
		Loans loan = loansService.getOneByID(id);
		OpenLoans open=openLoanService.getOpenLoanFromLoan(id);
		CloseLoans closeloan=new CloseLoans(loan);
		closeloan.setStatus("اغلقت وسددت بالكامل");
		if (voucherService.AllVoucherPaid(id))
		{
			closeLoanService.addLoan(closeloan);
			openLoanService.DeleteOpenLoan(open);
		}
		response.sendRedirect("/Vouchers/all/"+loan.getId());
	}
	


	
}
