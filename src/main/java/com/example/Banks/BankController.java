package com.example.Banks;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.example.SiteConfig.MasterService;
import com.example.SiteConfig.SiteConfiguration;



@RestController
public class BankController {


	@Autowired
	private ServicesPool servicePool;
	
	public BankController() {
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
		
	@RequestMapping("/Banks/{id}")
	public Banks viewBank(@PathVariable int id)
	{
		Optional<Banks> bank=servicePool.getBankService().GetBank(id);
		if(bank.isPresent())
		{
			return bank.get();
		}
		else 
			throw new RuntimeException("Bank"+ id + "is not Exist " );
	}
		
	
	//add new Bank -------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Banks/addBank")
	public ModelAndView addNewBank() {
		ModelAndView mav = new ModelAndView("Banks/AddBank");
		mav.addObject("bank",new Banks());
		return mav; 
	}
	 
	
	@RequestMapping(method = RequestMethod.POST , value="/Banks/addBank")
	public void addNewBank(@ModelAttribute Banks bank,HttpServletResponse response) throws IOException {
		System.out.println("posted to /Banks/addBank ");
		bank.setFinancialAllocations("0");
		servicePool.getBankService().addBank(bank);
		response.sendRedirect("/Banks/all");
	}
	// -----------------------------------------------------------------------   
	   
	
	
	//get All Banks ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Banks/all")
	public ModelAndView ShowAllBank() {
		ModelAndView mav = new ModelAndView("Banks/AllBanks");
		List<Banks> allbank=servicePool.getBankService().GetAllBanks();
		mav.addObject("allbank",allbank);
		return mav; 
	}
	// -------------------------------------------------------------------
	    
	// update bank--------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Banks/edit/{id}")
	public ModelAndView UpdateBank(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("Banks/updateBank");
		Banks bank=servicePool.getBankService().getBankById(id);
		mav.addObject("bank",bank);
		return mav; 
	} 
	
	
	@RequestMapping(method = RequestMethod.POST , value="/Banks/update/{id}")
	public void UpdateBank(@Valid Banks bank,HttpServletResponse response) throws IOException {
		System.out.println("posted to /Banks/update/id ");
		servicePool.getBankService().updateBank(bank);
		response.sendRedirect("/Banks/all");
	}
	

	
	//----------------------------------------------------------------
	
	//delete Bank ----------------------------------------------------------  
	@RequestMapping(method = RequestMethod.GET, value="/Banks/delete/{id}")
	public void deleteBank(@PathVariable int id,HttpServletResponse response) throws IOException
	{
		servicePool.getBankService().deleteBank(id);
		response.sendRedirect("/Banks/all");
	}
	//-----seacrh
	@RequestMapping(method = RequestMethod.POST , value = "/Banks/Search")
	public ModelAndView getAllChecksbyCheckid(@Param(value ="index") int index,@RequestParam("search") String bankName) {
		ModelAndView mav = new ModelAndView("Banks/searchbank");
		List<Banks> allbank = servicePool.getBankService().SearchbyBankName(index,bankName);
		mav.addObject("allbank",allbank);
		mav.addObject("searchvar",bankName);
		if(allbank.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav ;
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/Banks/Search/nxtRes/{index}/{searchvar}")
	public ModelAndView searchBankNext(@PathVariable int index,@PathVariable String searchvar ) {
		ModelAndView mav = new ModelAndView("Banks/searchbank");
		List<Banks> allbank = servicePool.getBankService().SearchbyBankName(index,searchvar);
		mav.addObject("allbank",allbank);
		mav.addObject("searchvar",searchvar);
		if(allbank.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav ;
	}
	
}



