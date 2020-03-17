package com.example.Banks;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.security.user.User;



@RestController
public class BankController {

	@Autowired
	private BankService bankservice;
	
	
	@RequestMapping("Banks")
	public List<Banks> GetAllBanks ()
	{
		return bankservice.GetAllBanks();
	}
	
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
	
	@RequestMapping(method = RequestMethod.POST , value="/addBank")
	public void addBank(@RequestBody Banks bank)
	{
		bankservice.addBank(bank);
	}
	
	@RequestMapping(method = RequestMethod.PUT , value="/Banks")
	public void updateBank(@RequestBody Banks bank)
	{
		bankservice.updateBank(bank);
		
	}
	

	
	
	///add new Bank(view only) ///
	@RequestMapping(method = RequestMethod.GET , value="/Banks/addBank")
	public ModelAndView addBankRequest() {
		ModelAndView mav = new ModelAndView("Banks/AddBank");
		mav.addObject("bank",new Banks());
		return mav; 
	}
	 
	//add Bank // 
	@RequestMapping(method = RequestMethod.POST , value="/Banks/addBank")
	public void addNewBank(@ModelAttribute Banks bank,HttpServletResponse response) throws IOException {
		System.out.println("posted to /Banks/addBank ");
		bankservice.addBank(bank);
		response.sendRedirect("/Banks/AllBanks");
	}
	   
	   
	@RequestMapping(method = RequestMethod.GET , value="/Banks/all")
	public ModelAndView ShowAllBank() {
		ModelAndView mav = new ModelAndView("Banks/AllBanks");
		  List<Banks> allbank=bankservice.GetAllBanks();
		mav.addObject("allbank",allbank);
		return mav; 
	}
	    
	  
	@RequestMapping(method = RequestMethod.DELETE, value="/Banks/{id}")
	public void deleteBank(@PathVariable int id,HttpServletResponse response) throws IOException
	{
		bankservice.deleteBank(id);
		response.sendRedirect("/Banks/all");
	}
	
	
}
