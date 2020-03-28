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
		this.settlementService.addCheck(check);
		ModelAndView mav = new ModelAndView("settlement/success");
		return mav ; 
	}
	
	
}
