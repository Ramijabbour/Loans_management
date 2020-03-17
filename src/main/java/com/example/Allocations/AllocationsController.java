package com.example.Allocations;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.Banks.BankService;
import com.example.Banks.Banks;

@Controller
public class AllocationsController {

	@Autowired
	AllocationsService allocationService ;
	
	@Autowired
	BankService bankservice ;
	
	//add new Allocation -------------------------------------------------------
		@RequestMapping(method = RequestMethod.GET , value="/Allocation/addAllocation")
		public ModelAndView addAllocationRequest() {
			ModelAndView mav = new ModelAndView("Banks/AddAllocations");
			  List<Banks> allbank=bankservice.GetAllBanks();
			  mav.addObject("allocation",new Allocations());
			 // mav.addObject("allbank", attributeValue)
			return mav; 
		}
		 
		
		@RequestMapping(method = RequestMethod.POST , value="/Allocation/addAllocation")
		public void addNewBank(@ModelAttribute Banks bank,HttpServletResponse response) throws IOException {
			System.out.println("posted to /Banks/addBank ");
			bankservice.addBank(bank);
			response.sendRedirect("/Banks/all");
		}
		// -----------------------------------------------------------------------   
}
