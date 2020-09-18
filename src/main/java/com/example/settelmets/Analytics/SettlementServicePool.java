package com.example.settelmets.Analytics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.settelmets.RTGSLink.RTGSUserService;
import com.example.settelmets.Services.SettlementService;

@Service
public class SettlementServicePool {
	
	@Autowired
	private RTGSUserService rtgsUsersService ; 
	
	@Autowired
	private SettlementService settlementService ;


	
	public RTGSUserService getRtgsUsersService() {
		return rtgsUsersService;
	}

	public SettlementService getSettlementService() {
		return settlementService;
	} 

	
}
