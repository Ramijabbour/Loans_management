package com.example.Banks;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.MasterService;
import com.example.Banks.Stats.BankStatsService;
import com.example.Banks.Stats.ChartsHandler.MultiBanksAnalysisController;
import com.example.Banks.Stats.ChartsHandler.SingleBankAnalysisController;
import com.example.Banks.Stats.ChartsHandler.TimeSpanModel;
import com.example.security.user.User;



@RestController
public class BankController {

	@Autowired
	private BankService bankservice;
	
		
	@RequestMapping("/Banks/{id}")
	public Banks GetBank(@PathVariable int id)
	{
		Optional<Banks> bank=bankservice.GetBank(id);
		if(bank.isPresent())
		{
			return bank.get();
		}
		else 
			throw new RuntimeException("Bank"+ id + "is not Exist " );
	}
		
	
	//add new Bank -------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Banks/addBank")
	public ModelAndView addBankRequest() {
		ModelAndView mav = new ModelAndView("Banks/AddBank");
		mav.addObject("bank",new Banks());
		return mav; 
	}
	 
	
	@RequestMapping(method = RequestMethod.POST , value="/Banks/addBank")
	public void addNewBank(@ModelAttribute Banks bank,HttpServletResponse response) throws IOException {
		System.out.println("posted to /Banks/addBank ");
		bank.setFinancialAllocations("0");
		bankservice.addBank(bank);
		response.sendRedirect("/Banks/all");
	}
	// -----------------------------------------------------------------------   
	   
	
	
	//get All Banks ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Banks/all")
	public ModelAndView ShowAllBank() {
		ModelAndView mav = new ModelAndView("Banks/AllBanks");
		  List<Banks> allbank=bankservice.GetAllBanks();
		mav.addObject("allbank",allbank);
		return mav; 
	}
	// -------------------------------------------------------------------
	    
	// update bank--------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Banks/edit/{id}")
	public ModelAndView ShowUpdateBank(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("Banks/updateBank");
		Banks bank=bankservice.getBankById(id);
		mav.addObject("bank",bank);
		return mav; 
	} 
	
	
	@RequestMapping(method = RequestMethod.POST , value="/Banks/update/{id}")
	public void UpdateBank(@Valid Banks bank,HttpServletResponse response) throws IOException {
		System.out.println("posted to /Banks/update/id ");
		bankservice.updateBank(bank);
		response.sendRedirect("/Banks/all");
	}
	

	
	//----------------------------------------------------------------
	
	//delete Bank ----------------------------------------------------------  
	@RequestMapping(method = RequestMethod.GET, value="/Banks/delete/{id}")
	public void deleteBank(@PathVariable int id,HttpServletResponse response) throws IOException
	{
		bankservice.deleteBank(id);
		response.sendRedirect("/Banks/all");
	}

}



