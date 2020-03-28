package com.example.settelmets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SettlementController {
	
	@Autowired
	SettlementService settlementService ; 
	
	@RequestMapping(method = RequestMethod.GET ,value = "/settlement/invoke" )
	public void invokeSettleMethod() {
		this.settlementService.settleChecks();
	}
	
}
