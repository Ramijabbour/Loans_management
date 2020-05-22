package com.example.Allocations;


import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
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

import com.example.BankBranches.BrancheService;
import com.example.Banks.BankService;
import com.example.Banks.Banks;
import com.example.SiteConfig.MasterService;
import com.example.SiteConfig.SiteConfiguration;

@Controller
public class AllocationsController {
	
	@Autowired
	AllocationsService allocationService ;
	
	@Autowired
	BankService bankservice ;
	
	@Autowired
	BrancheService brancheservice ;

	
	public AllocationsController() {
		Method[] methods =  this.getClass().getDeclaredMethods();
		List<String> methodsNames = new ArrayList<String>(); 
		for(Method method : methods) {
			if(!methodsNames.contains(method.getName()))
				{
					methodsNames.add(method.getName());
				}
		}
		methodsNames.add(this.getClass().getSimpleName());
		MasterService.addPermissionsToPermissionService(methodsNames);
	}
	
	//add new Allocation -------------------------------------------------------
		@RequestMapping(method = RequestMethod.GET , value="/Allocation/addAllocation")
		public ModelAndView addAllocation() throws ParseException {
			ModelAndView mav = new ModelAndView("Banks/AddAllocations");
			  List<Banks> allbank=bankservice.GetBankForAllocation();
			  System.out.println("-----=-=-=-==--=-=-=-==-"+allbank.size());
			  mav.addObject("allocation",new Allocations());
			  mav.addObject("allbank",allbank);
			return mav; 
		}
		
		
		
	/*	@RequestMapping(method = RequestMethod.POST , value="/Allocation/addAllocation")
		public void addNewAllocation(@ModelAttribute Allocations Allocation,HttpServletResponse response) throws IOException {
			System.out.println("posted to /Allocation/addAllocation ");
			Banks bank=bankservice.getBankByID(Allocation.getBank().getBankID());
			bank.setFinancialAllocations(Allocation.getAllocationAmmount());
			System.out.println("Done");
			allocationService.addAllocation(Allocation);
			response.sendRedirect("/Banks/all");
		}*/
		
		
		@RequestMapping(method = RequestMethod.POST , value="/Allocation/addAllocation")
		public ModelAndView addAllocation(@ModelAttribute Allocations Allocation,HttpServletResponse response) throws IOException {
			System.out.println("posted to /Allocation/addAllocation ");
			//Banks bank=bankservice.getBankByID(Allocation.getBank().getBankID());
			//bank.setFinancialAllocations(Allocation.getAllocationAmmount());
			//System.out.println("Done");
			//allocationService.addAllocation(Allocation);
			ModelAndView mav = new ModelAndView("Banks/ConfirmeAllocation");
			mav.addObject("allocation", Allocation);
			Banks bank=Allocation.getBank();
			  mav.addObject("bank",bank);
			return mav;
			//response.sendRedirect("/Allocation/ConfirmeAllocation");
		}
		
		

		@RequestMapping(method = RequestMethod.POST , value="/Allocation/ConfirmeAllocation")
		public ModelAndView addAllocation(@ModelAttribute Allocations Allocation) throws IOException {
			System.out.println("posted to /Allocation/addAllocation ");
			Banks bank=bankservice.getBankByID(Allocation.getBank().getBankID());
			int newallocation = Integer.parseInt(Allocation.getAllocationAmmount())+Integer.parseInt(bank.getFinancialAllocations());
			bank.setFinancialAllocations(Integer.toString(newallocation));
			System.out.println("Done");
			allocationService.addAllocation(Allocation);
			return MasterService.sendSuccessMsg("تمت إضافة المخصصات بنجاح"); 		
			}
		
		
	
		
		// -----------------------------------------------------------------------   

		//Get All Allocation -----------------------------------------------------
		@RequestMapping(method = RequestMethod.GET , value="/Allocation/all")
		public ModelAndView viewAllAllocations(@Param(value ="index") int index) {
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
		public ModelAndView UpdateAlloaction(@PathVariable int id) {
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

