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
import com.example.Banks.Stats.ChartsHandler.MultiBanksSingleYearAnalysisController;
import com.example.Banks.Stats.ChartsHandler.SingleBankAnalysisController;
import com.example.Banks.Stats.ChartsHandler.SingleSpanModel;
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


	
	//single banks DashBoard
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
	
	@RequestMapping(method = RequestMethod.GET , value = "/dashBoards/nav")
	public ModelAndView getDashNavigation() {
		ModelAndView mav = new ModelAndView("Charts/DashNavigation");
		return mav ; 
	}

	
	
	//MultiBanks MultiYears DashBoards 
	
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
	
	
	// MultiBanks Single Year 
	
	@RequestMapping(method = RequestMethod.GET , value = "/dashBoards/singleTimeSpan")
	public ModelAndView getSingleTimeSpan() {
		SingleSpanModel timeSpanModel = new SingleSpanModel() ; 
		ModelAndView mav = new ModelAndView("Charts/singleSpan");
		mav.addObject("tsm",timeSpanModel );
		return mav ; 
	}
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/dashBoards/singleTimeSpan/response")
	public ModelAndView DashBoardsSingleTimeSpanResponse(@ModelAttribute SingleSpanModel timeSpanModel) {
		return  getSigleDashBoards(timeSpanModel.getYear(),timeSpanModel.getQuarter()); 
	}
	
	
	public ModelAndView getSigleDashBoards(int year , int quarter) {	
		if(year <= 0 ) {
			return MasterService.sendGeneralError("date should be positive value") ;
		}
		if(quarter < 1) {
			return MasterService.sendGeneralError("Qurter Not valid") ; 
		}
		if(quarter > 4) {
			return MasterService.sendGeneralError("Qurter Not valid") ; 
		}
		MultiBanksSingleYearAnalysisController.flushLists();
		MultiBanksSingleYearAnalysisController.setYear(year);
		int monthStart = 0 ;
		int monthEnd = 0 ; 
		if(quarter == 1 ) {
			monthStart = 1 ; 
			monthEnd = 3 ; 
		}else if(quarter == 2 ) {
			monthStart = 4 ; 
			monthEnd = 6 ; 
		}else if(quarter == 3 ) {
			monthStart = 7 ; 
			monthEnd = 9 ; 
		}else if(quarter == 4 ) {
			monthStart = 10 ; 
			monthEnd = 12 ; 
		}
		MultiBanksSingleYearAnalysisController.setMonthStart(monthStart);
		MultiBanksSingleYearAnalysisController.setMonthEnd(monthEnd);
		MultiBanksSingleYearAnalysisController.setBanksList(this.bankservice.GetAllBanks());
		ModelAndView mav = new ModelAndView("Charts/singleYearCharts");
		return mav ; 
	}	
	//
	
}
