package com.example.settelmets.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.settelmets.Models.CheckDisposableModel;
import com.example.settelmets.Services.SettlementService;

@RestController
public class SettlementController {
	
	@Autowired
	SettlementService settlementService ;  
	
	//add protection or lock the method
	@RequestMapping(method = RequestMethod.GET ,value = "/settlement/invoke" )
	public void invokeSettleMethod() {
		this.settlementService.settleChecks();
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/add")
	public ModelAndView addCheckView() {
		ModelAndView mav = new ModelAndView("settlement/add");
		mav.addObject("check",new CheckDisposableModel());
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/settlement/checks/add")
	public ModelAndView addCheck(@ModelAttribute CheckDisposableModel check ) {
		int operationCode = this.settlementService.addCheck(check);
		if(operationCode != 0 ) {
			String msg = this.translateErrorCode(operationCode) ;
			ModelAndView mav = new ModelAndView("settlement/fail");
			mav.addObject("msg", msg);
			return mav ; 
		}else {
			ModelAndView mav = new ModelAndView("settlement/success");
			return mav ; 
		}
	}	
		
	/*
	 * 		ERROR 				 | Error code  |
	 * ----------------------------------------- 
	 *  amount less than zero    | -1 			|Logical Error
	 *
	 *  check between branches   | -21			|Logical Error
	 *  of the same bank 	    
	 * 
	 *  sender branch code is 	 | -22			|Logical Error
	 *  equal to receiver 
	 *  branch code
	 *  
	 *  first bank not found 	 | -311 		|First Bank Name Error 
	 *  
	 *  first branch not found   | -312			|First Branch Code Error 
	 *  
	 *  second bank not found    | -321  		|Second Bank Name Error
	 *  
	 *  second branch not found  | -322			|Second Branch Code Error
	 *  
	 *  check id duplication     | -4  			|
	 * */
	private String translateErrorCode(int errCode) {
		if(errCode == -1  ) {
			return "The transfer ammount is less than zero";
		}
		if(errCode ==  -21 ) {
			return "the check is between branches from the same bank";
		}
		if(errCode ==  -22 ) {
			return "sender branch code is equal to receiver branch code";
		}
		
		if(errCode ==  -311) {
			return "First Bnak not found " ; 
		}
		if(errCode ==  -312) {
			return "First Branch not found";
		}
		if(errCode ==  -321) {
			return "Second Bank not found ";
		}
		if(errCode ==  -322) {
			return "Second Branch not found ";
		}
		if(errCode ==  -4) {
			return "The CheckId is already used";
		}
		return "unknown error ";
	}
	

	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/all")
	public ModelAndView getAllChecks() {
		ModelAndView mav = new ModelAndView("settlement/allChecks");
		mav.addObject("checksList",this.settlementService.getAllChecks());
		return mav ; 

	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/settled")
	public ModelAndView getSettledChecks() {
		ModelAndView mav = new ModelAndView("settlement/settled");
		mav.addObject("checksList",this.settlementService.getSettledChecks());
		return mav ; 
	}	
		
	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/onhold")
	public ModelAndView getonHoldChecks() {
		ModelAndView mav = new ModelAndView("settlement/onHold");
		mav.addObject("checksList",this.settlementService.getOnHoldChecks());
		return mav ; 
	}
	
	
	
}
