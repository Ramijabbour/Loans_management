package com.example.Banks.Stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.MasterService;
import com.example.Banks.BankService;
import com.example.Banks.Banks;
import com.example.Banks.Stats.ChartsHandler.MultiBanksAnalysisController;
import com.example.Banks.Stats.ChartsHandler.SingleBankAnalysisController;
import com.example.Banks.Stats.ChartsHandler.TimeSpanModel;

@RestController
public class BanksStatsController {

	@Autowired
	private BankStatsService bankStatsService ; 
	
	@Autowired
	private BankService bankservice;
	
	@RequestMapping(method = RequestMethod.GET , value = "/Banks/view/stats/{bankId}")
	public ModelAndView getBanksStats(@PathVariable int bankId) {
		Banks bank = this.bankservice.getBankById(bankId);
		if(bank == null ) {
			return MasterService.sendGeneralError("Bank Not Found") ; 
		}
		ModelAndView mav = new ModelAndView("Banks/newstats");
		mav.addObject("bankstats",this.bankStatsService.getBankStats(bank)) ;
		return mav ; 
	}


	@RequestMapping(method = RequestMethod.GET , value = "/Banks/view/stats/charts/{bankId}")
	public ModelAndView getBankStatsTimeSpan(@PathVariable int bankId ) {
		TimeSpanModel timeSpanModel = new TimeSpanModel() ; 
		ModelAndView mav = new ModelAndView("Charts/settimespan");
		mav.addObject("bankId",bankId);
		mav.addObject("tsm",timeSpanModel );
		return mav ; 
	}

	@RequestMapping(method = RequestMethod.GET , value = "/Banks/view/stats/set/{bankId}")
	public ModelAndView getBankStatsTimeResponse(@PathVariable int bankId,@ModelAttribute TimeSpanModel timeSpanModel) {
		return  getBankNewStats(bankId,timeSpanModel.getStdate(),timeSpanModel.getFndate()); 
	}

	
	public ModelAndView getBankNewStats(int id, int stdate, int fndate ) {
		Banks bank = this.bankservice.getBankById(id);
		if(stdate <= 0 || fndate <=0 ) {
			return MasterService.sendGeneralError("date should be positive value") ;
		}
		if(stdate > fndate) {
			return MasterService.sendGeneralError("start date should be less than end date") ; 
		}
		if(bank == null ) {
			return MasterService.sendGeneralError("Bank Not Found") ; 
		}
		//find better way than static variables 
		SingleBankAnalysisController.setTimeSpanStart(stdate);
		SingleBankAnalysisController.setTimeSpanEnd(fndate); 
		SingleBankAnalysisController.setBank(bank);
		//-----------------------------------------
		ModelAndView mav = new ModelAndView("Charts/charts");
		return mav ; 
	}
	
	

	
	//MultiBanks DashBoards 
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/dashBoards/setTimeSpan")
	public ModelAndView getDashBoardsTimeSpan() {
		TimeSpanModel timeSpanModel = new TimeSpanModel() ; 
		ModelAndView mav = new ModelAndView("Charts/multiSpan");
		mav.addObject("tsm",timeSpanModel );
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/dashBoards/setTimeSpan/response")
	public ModelAndView DashBoardsTimeSpanResponse(@ModelAttribute TimeSpanModel timeSpanModel) {
		return  getDashBoards(timeSpanModel.getStdate(),timeSpanModel.getFndate()); 
	}
	

	public ModelAndView getDashBoards(int stDate , int fnDate) {	
		if(stDate <= 0 || fnDate <=0 ) {
			return MasterService.sendGeneralError("date should be positive value") ;
		}
		if(stDate > fnDate) {
			return MasterService.sendGeneralError("start date should be less than end date") ; 
		}
		MultiBanksAnalysisController.flushLists();
		MultiBanksAnalysisController.setYearSpanStart(stDate);
		MultiBanksAnalysisController.setYearSpanEnd(fnDate);
		MultiBanksAnalysisController.setBanksList(this.bankservice.GetAllBanks());
		ModelAndView mav = new ModelAndView("Charts/multiCharts");
		return mav ; 
	}	
	
	
}
