package com.example.Allocations;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.SiteConfiguration;
import com.example.BankBranches.BrancheService;
import com.example.Banks.BankService;
import com.example.Banks.Banks;

@Controller
public class AllocationsController {

	@Autowired
	AllocationsService allocationService ;
	
	@Autowired
	BankService bankservice ;
	
	@Autowired
	BrancheService brancheservice ;
	
	//add new Allocation -------------------------------------------------------
		@RequestMapping(method = RequestMethod.GET , value="/Allocation/addAllocation")
		public ModelAndView addAllocationRequest() {
			ModelAndView mav = new ModelAndView("Banks/AddAllocations");
			  List<Banks> allbank=bankservice.GetAllBanks();
			  mav.addObject("allocation",new Allocations());
			  mav.addObject("allbank",allbank);
			return mav; 
		}
		  
		
		@RequestMapping(method = RequestMethod.POST , value="/Allocation/addAllocation")
		public void addNewAllocation(@ModelAttribute Allocations Allocation,HttpServletResponse response) throws IOException {
			System.out.println("posted to /Allocation/addAllocation ");
			Banks bank=bankservice.getBankByID(Allocation.getBank().getBankID());
			bank.setFinancialAllocations(Allocation.getAllocationAmmount());
			System.out.println("Done");
			allocationService.addAllocation(Allocation);
			response.sendRedirect("/Banks/all");
		}
		// -----------------------------------------------------------------------   

		//Get All Allocation -----------------------------------------------------
		@RequestMapping(method = RequestMethod.GET , value="/Allocation/all")
		public ModelAndView AllAllocation(@Param(value ="index") int index) {
			ModelAndView mav = new ModelAndView("Banks/AllAllocations");
			  List<Allocations> allAllocations=allocationService.getAllAllocations(index);
			  mav.addObject("AllAllocations",allAllocations);
			  if(allAllocations.size() > 0 ) {
					SiteConfiguration.addSequesnceVaraibles(mav, index);
				}else {
					SiteConfiguration.addSequesnceVaraibles(mav, -1);
				}
			return mav; 
		}

		// -----------------------------------------------------------------------
		
		//Delete Allocation ------------------------------------------------------
		
		@RequestMapping(method = RequestMethod.GET, value="/Allocation/delete/{id}")
		public void deleteAllocation(@PathVariable int id,HttpServletResponse response) throws IOException
		{
			allocationService.DeleteAllocation(id);
			response.sendRedirect("/Allocation/all");
		}



		// ------------------------------------------------------------------
		// update Allocation--------------------------------------------------------
		@RequestMapping(method = RequestMethod.GET , value="/Allocation/edit/{id}")
		public ModelAndView ShowUpdateAllocation(@PathVariable int id) {
			ModelAndView mav = new ModelAndView("Banks/UpdateAllocations");
			Allocations Allocation = allocationService.getAllocationById(id);
			List<Banks> allbanks =bankservice.GetAllBanks();
			mav.addObject("allbanks",allbanks);
			mav.addObject("allocation",Allocation);
			return mav; 
		} 
		
		
		@RequestMapping(method = RequestMethod.POST , value="/Allocation/update/{id}")
		public void UpdateAlloaction(@Valid Allocations allocation,HttpServletResponse response) throws IOException {
			System.out.println("posted to /Allocations/update/id ");
			Banks bank=bankservice.getBankById(allocation.getBank().getBankID());
			bank.setFinancialAllocations(allocation.getAllocationAmmount());
			allocationService.updateAllocation(allocation);
			response.sendRedirect("/Allocation/all");
		}
		


}

