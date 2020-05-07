package com.example.Banks.Stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.Banks.BankService;

@RestController
public class BanksStatsController {

	
	@Autowired
	private BankStatsService bankStatsService ; 
	
	@Autowired
	private BankService banksService ; 


	
	// add redirect routes 
	
}
