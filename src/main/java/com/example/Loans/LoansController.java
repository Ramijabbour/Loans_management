package com.example.Loans;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.example.Banks.BankService;
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
import com.example.Vouchers.VoucherService;

@RestController
public class LoansController {

	@Autowired
	LoanService loansService;
	@Autowired 
	ClientService clientService; 
	@Autowired 
	BankService bankService ;
	@Autowired 
	LoansTypeService loansTypeService;
	@Autowired 
	FinanceTypeService financeTypeService ;
	
	@Autowired
	OpenLoansService openLoanService; 
	@Autowired
	CloseLoanService closeLoanService ;
	
	@Autowired
	VoucherService voucherService;
	
	//get All Loans ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/all")
	public ModelAndView ShowAllLoans() { 
		ModelAndView mav = new ModelAndView("Loans/AllLoans");
		List<Loans> allloans=loansService.getAllLoans();
		mav.addObject("allloans",allloans);
		return mav; 
	}
	// -------------------------------------------------------------------

	
	//add loan ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/addLoan")
	public ModelAndView AddLoan() { 
		ModelAndView mav = new ModelAndView("Loans/AddLoan");
		List<Clients> allclient = clientService.GetAllClients();
		List<Banks> allbank=bankService.GetAllBanks();
		List<LoansType> allloanType = loansTypeService.getAllType();
		List<FinanceType> allfinanceType= financeTypeService.getAllFinanceType();
	    mav.addObject("allclient",allclient);
	    mav.addObject("loan",new Loans());
	    mav.addObject("allbank",allbank);
	    mav.addObject("allloantype",allloanType);
	    mav.addObject("allfinancetype",allfinanceType);
		return mav; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value="/Loans/addLoan")
	public void addNewLoan(@ModelAttribute Loans loan,HttpServletResponse response) throws IOException {
		System.out.println("posted to /Loans/addLoan "); 
		loansService.addLoan(loan);
		
		OpenLoans ol =new OpenLoans(loan);
		openLoanService.addOpenLoan(ol);
		response.sendRedirect("/Loans/all");
	}
	// -------------------------------------------------------------------
	
	//display loan ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/Loan/{id}")
	public ModelAndView ShowLoan(@PathVariable int id) { 
		ModelAndView mav = new ModelAndView("Loans/oneLoan");
		  Loans l = loansService.getOneByID(id);
		  System.out.println(l.getLoanID()+"------------------------ ");
		  mav.addObject("loan",l);
		return mav; 
	}
	// -------------------------------------------------------------------
	
	// update Loan--------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/edit/{id}")
	public ModelAndView ShowUpdateLoan(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("Loans/UpdateLoan");
		Loans loan=loansService.getOneByID(id);
		List<Clients> allclient = clientService.GetAllClients();
		List<Banks> allbank=bankService.GetAllBanks();
		List<LoansType> allloanType = loansTypeService.getAllType();
		List<FinanceType> allfinanceType= financeTypeService.getAllFinanceType();
		mav.addObject("allbank",allbank);
		mav.addObject("loan",loan);
		mav.addObject("allclient",allclient);
	    mav.addObject("allbank",allbank);
	    mav.addObject("allloantype",allloanType);
	    mav.addObject("allfinancetype",allfinanceType);
		return mav; 
	} 
	
	
	@RequestMapping(method = RequestMethod.POST , value="/Loans/update/{id}")
	public void UpdateLoan(@Valid Loans loan,HttpServletResponse response) throws IOException {
		System.out.println("posted to /Loans/update/id ");
		loansService.updateLoan(loan);
		response.sendRedirect("/Loans/all");
	}
	

	
	//----------------------------------------------------------------
	
	//delete Bank ----------------------------------------------------------  
	@RequestMapping(method = RequestMethod.GET, value="/Loans/delete/{id}")
	public void deleteLoan(@PathVariable int id,HttpServletResponse response) throws IOException
	{
		loansService.DeleteLoan(id);
		response.sendRedirect("/Loans/all");
	}
	
	@RequestMapping(method = RequestMethod.GET , value="/Loans/CheckCloseLoan/{id}")
	public void CheckCloseLoan(@PathVariable int id ,HttpServletResponse response) throws IOException
	{
		Loans loan = loansService.getOneByID(id);
		OpenLoans open=openLoanService.getOpenLoanFromLoan(id);
		CloseLoans closeloan=new CloseLoans(loan);
		if (voucherService.AllVoucherPaid(id))
		{
			closeLoanService.addLoan(closeloan);
			openLoanService.DeleteOpenLoan(open);
		}
		response.sendRedirect("/Vouchers/all/"+loan.getLoanID());
	}
	
	
	// check to Close Loan 
	

	

	
}
