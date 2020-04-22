package com.example.BankBranches;

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

@RestController
public class BracheController {

	@Autowired 
	BrancheService brancheService;
	@Autowired
	BankService bankservice;
	
	@RequestMapping(method = RequestMethod.GET , value="/Banks/addBranche/{id}")
	public ModelAndView addBrancheRequest(@PathVariable int id ) {
		ModelAndView mav = new ModelAndView("Branches/AddBranche");
		mav.addObject("branche",new Branches());
		Banks bank =bankservice.getBankById(id);
		mav.addObject("bank", bank);
		return mav; 
	}
	 
	
	@RequestMapping(method = RequestMethod.POST , value="/Banks/addBranche/{id}")
	public void addNewBranche(@ModelAttribute Branches branche,HttpServletResponse response) throws IOException {
		
		System.out.println("posted to /Branches/Bankid/addBranche ");
		brancheService.addBranche(branche);
		response.sendRedirect("/Banks/all");
	}
	
	
	
	//get All Branches ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Banks/Branches/all")
	public ModelAndView ShowAllBranches() {
		ModelAndView mav = new ModelAndView("Branches/AllBranche");
	    List<Branches> allbranche=brancheService.getAllBranche();
		mav.addObject("allbranches",allbranche);
		return mav; 
	}
	
	//get  Branche ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Banks/Branches/view/{id}")
	public ModelAndView ShowABranche(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("Branches/viewBranche");
	    Branches branche=brancheService.getBranche(id);
		mav.addObject("branche",branche);
		return mav; 
	}
	
	
	
	
	// update branche--------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Banks/Branches/edit/{id}")
	public ModelAndView ShowUpdateBranche(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("Branches/updateBranche");
		Branches branche=brancheService.getBranche(id);
		Banks bank=branche.getBank();
		mav.addObject("branche",branche);
		mav.addObject("bank", bank);
		return mav; 
	} 
	
	
	@RequestMapping(method = RequestMethod.POST , value="/Banks/Branches/update/{id}")
	public void UpdateBranche(@Valid Branches branche,HttpServletResponse response) throws IOException {
		System.out.println("posted to /Banks/Branches/update/id ");
		brancheService.UpdateBracnhe(branche);
		response.sendRedirect("/Banks/Branches/all");
	}
	

	
	//----------------------------------------------------------------
	
	//delete Branche ----------------------------------------------------------  
	@RequestMapping(method = RequestMethod.GET, value="/Banks/Branches/delete/{id}")
	public void deleteBranche(@PathVariable int id,HttpServletResponse response) throws IOException
	{
		brancheService.DeleteBranche(id);
		response.sendRedirect("/Banks/Branches/all");
	}
}
