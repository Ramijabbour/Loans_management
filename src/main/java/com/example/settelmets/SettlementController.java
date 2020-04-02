package com.example.settelmets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


import com.example.MQ.Chaque;

@RestController
public class SettlementController {
	
	@Autowired
	SettlementService settlementService ; 
	
	//add protection or lock the method
	@RequestMapping(method = RequestMethod.GET ,value = "/settlement/invoke" )
	public void invokeSettleMethod() {
		this.settlementService.settleChecks();
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/add/get")
	public ModelAndView addCheckView() {
		ModelAndView mav = new ModelAndView("settlement/add");
		mav.addObject("check",new Chaque());
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/settlement/checks/add")
	public ModelAndView addCheck(@ModelAttribute Chaque check ) {
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
	
	private String translateErrorCode(int errCode) {
		if(errCode == -1  ) {
			return "The transfer ammount is less than zero";
		}
		if(errCode ==  -2 ) {
			return "Sender and reciever are the Same bank";
		}
		if(errCode ==  -311) {
			return "First Bnak not found " ; 
		}
		if(errCode ==  -312) {
			return "First Banke Name Error ";
		}
		if(errCode ==  -321) {
			return "Second Bank not found ";
		}
		if(errCode ==  -322) {
			return "Second Bank Name Error ";
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
	
	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/reports")
	public ModelAndView getonSettlementReports() {
		ModelAndView mav = new ModelAndView("settlement/reports");
		mav.addObject("checksList",this.settlementService.getSettledChecksReports());
		return mav ; 
	}

}
