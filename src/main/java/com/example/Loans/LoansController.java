package com.example.Loans;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoansController {

	
	
	//get All Loans ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/all")
	public ModelAndView ShowAllLoans() { 
		ModelAndView mav = new ModelAndView("Loans/AllLoans");
		  //List<Banks> allbank=bankservice.GetAllBanks();
		//mav.addObject("allbank",allbank);
		return mav; 
	}
	// -------------------------------------------------------------------

	
	//add loan ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/addLoan")
	public ModelAndView AddLoan() { 
		ModelAndView mav = new ModelAndView("Loans/AddLoan");
		  //List<Banks> allbank=bankservice.GetAllBanks();
		//mav.addObject("allbank",allbank);
		return mav; 
	}
	// -------------------------------------------------------------------
	
	//display loan ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/Loan/{id}")
	public ModelAndView ShowLoan(@PathVariable int id ) { 
		ModelAndView mav = new ModelAndView("Loans/oneLoan");
		  //List<Banks> allbank=bankservice.GetAllBanks();
		//mav.addObject("allbank",allbank);
		return mav; 
	}
	// -------------------------------------------------------------------
	
	
}
