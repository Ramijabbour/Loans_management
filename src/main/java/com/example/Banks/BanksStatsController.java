package com.example.Banks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class BanksStatsController {

	
	@Autowired
	private BankStatsService bankStatsService ; 
	
	@Autowired
	private BankService banksService ; 
	
	@RequestMapping(method = RequestMethod.GET , value = "/banks/view/stats/{id}")
	public ModelAndView getBankNewStats(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("Banks/newstats");
		Banks bank = this.banksService.getBankById(id);
		mav.addObject("bankstats",this.bankStatsService.getBankStats(bank)) ;
		return mav ; 
	}
	
	
	// add redirect routes 
	
}
