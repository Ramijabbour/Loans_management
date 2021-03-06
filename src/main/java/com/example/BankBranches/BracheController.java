package com.example.BankBranches;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.ServicesPool;
import com.example.Banks.Banks;
import com.example.SiteConfig.MasterService;
import com.example.SiteConfig.SiteConfiguration;

@RestController
public class BracheController {
	
	@Autowired 
	ServicesPool servicePool ; 
	
	public BracheController() {
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
	
	@RequestMapping(method = RequestMethod.GET , value="/Banks/addBranche/{id}")
	public ModelAndView addBranche(@PathVariable int id ) {
		ModelAndView mav = new ModelAndView("Branches/AddBranche");
		mav.addObject("branche",new Branches());
		Banks bank =servicePool.getBankService().getBankById(id);
		mav.addObject("bank", bank);
		return mav; 
	}
	 
	
	@RequestMapping(method = RequestMethod.POST , value="/Banks/addBranche/{id}")
	public void addBranche(@ModelAttribute Branches branche,HttpServletResponse response) throws IOException {
		
		System.out.println("posted to /Branches/Bankid/addBranche ");
		servicePool.getBranchesService().addBranche(branche);
		response.sendRedirect("/Banks/all");
	}
	
	
	
	//get All Branches ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Banks/Branches/all")
	public ModelAndView viewAllBranches(@Param(value ="index") int index) {
		ModelAndView mav = new ModelAndView("Branches/AllBranche");
	    List<Branches> allbranche=servicePool.getBranchesService().getAllBranche(index);
		mav.addObject("allbranches",allbranche);
		if(allbranche.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav; 
	}
	
	
	//get Bank Branches ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/BankBranches/{id}")
	public ModelAndView viewBankBranches(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("Branches/AllBranche");
		Banks bank = servicePool.getBankService().getBankById(id);
	    List<Branches> allbranche=servicePool.getBranchesService().getBankBranches(bank);
		mav.addObject("allbranches",allbranche);
		return mav; 
	}
	
	//get  Branche ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Banks/Branches/view/{id}")
	public ModelAndView viewSingleBranch(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("Branches/viewBranche");
	    Branches branche=servicePool.getBranchesService().getBranche(id);
		mav.addObject("branche",branche);
		return mav; 
	}
	
	
	
	
	// update branche--------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Banks/Branches/edit/{id}")
	public ModelAndView UpdateBranche(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("Branches/updateBranche");
		Branches branche=servicePool.getBranchesService().getBranche(id);
		Banks bank=branche.getBank();
		mav.addObject("branche",branche);
		mav.addObject("bank", bank);
		return mav; 
	} 
	
	
	@RequestMapping(method = RequestMethod.POST , value="/Banks/Branches/update/{id}")
	public void UpdateBranche(@Valid Branches branche,HttpServletResponse response) throws IOException {
		System.out.println("posted to /Banks/Branches/update/id ");
		servicePool.getBranchesService().UpdateBracnhe(branche);
		response.sendRedirect("/Banks/Branches/all");
	}
	

	
	//----------------------------------------------------------------
	
	//delete Branche ----------------------------------------------------------  
	@RequestMapping(method = RequestMethod.GET, value="/Banks/Branches/delete/{id}")
	public void deleteBranche(@PathVariable int id,HttpServletResponse response) throws IOException
	{
		servicePool.getBranchesService().DeleteBranche(id);
		response.sendRedirect("/Banks/Branches/all");
	}

	//-----seacrh
	@RequestMapping(method = RequestMethod.POST , value = "/Banks/Branches/Search")
	public ModelAndView SearchbyBrancheCode(@Param(value ="index") int index,@RequestParam("search") String brancheCode) {
		ModelAndView mav = new ModelAndView("Branches/SearchBranches");
		List<Branches> allbranches = servicePool.getBranchesService().SearchbybrancheCode(index,brancheCode);
		mav.addObject("allbranches",allbranches);
		mav.addObject("searchvar",brancheCode);
		if(allbranches.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else { 
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav ;
	}
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/Banks/Branches/Search/nxtRes/{index}/{searchvar}")
	public ModelAndView searchBankNext(@PathVariable int index,@PathVariable String searchvar ) {
		ModelAndView mav = new ModelAndView("Branches/SearchBranches");
		List<Branches> allbranches = servicePool.getBranchesService().SearchbybrancheCode(index,searchvar);
		mav.addObject("allbranches",allbranches);
		mav.addObject("searchvar",searchvar);
		if(allbranches.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav ;
	}
	
}
