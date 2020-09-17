package com.example.Banks.Stats;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.ServicesPool;
import com.example.Banks.Banks;
import com.example.Banks.Stats.Handlers.MultiBanksAnalysisController;
import com.example.Banks.Stats.Handlers.MultiBanksSingleYearAnalysisController;
import com.example.Banks.Stats.Handlers.SingleBankAnalysisController;
import com.example.Banks.Stats.Models.BanksSelectionModel;
import com.example.Banks.Stats.Models.SingleSpanModel;
import com.example.Banks.Stats.Models.TimeSpanModel;
import com.example.SiteConfig.MasterService;

@RestController
public class BanksStatsController {

	@Autowired
	private BankStatsService bankStatsService ; 
	
	@Autowired
	private ServicesPool servicePool ; 
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/Banks/view/stats/{bankId}")
	public ModelAndView getBanksStats(@PathVariable int bankId) {
		Banks bank = servicePool.getBankService().getBankById(bankId);
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
		Banks bank = servicePool.getBankService().getBankById(id);
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
	
	@RequestMapping(method = RequestMethod.GET , value = "/charts/nav")
	public ModelAndView getDashNavigation() {
		ModelAndView mav = new ModelAndView("Charts/DashNavigation");
		mav.addObject("dm", this.bankStatsService.getDashDataObject());
		return mav ; 
	}

	@RequestMapping(method = RequestMethod.GET , value = "/charts/bankSelectionNav/{redirect}")
	public ModelAndView dashResponse(@PathVariable int redirect) {
		if(redirect == 1 ) {
			MultiBanksSingleYearAnalysisController.flushLists();
			return getBankSelectionView();
			//send to single year bank selection 
		}else if(redirect ==2 ) {
			MultiBanksAnalysisController.flushLists();
			return getMultiYearBankSelectionView();
		}else {
			return MasterService.sendGeneralError("unKnown Operation ");
		}
	}
	
	//MultiBanks MultiYears DashBoards 
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/charts/multiYearBankSelection")
	public ModelAndView getMultiYearBankSelectionView() {
		ModelAndView mav = new ModelAndView("Charts/multiBankSelection");
		List<Banks> banksList = servicePool.getBankService().GetAllBanks() ; 
		List<Banks> banksListToView = new ArrayList<Banks>(); 
		for(Banks bank : banksList) {
			if(!MultiBanksAnalysisController.containsBank(bank)) {
				banksListToView.add(bank);
			}
		}
		BanksSelectionModel BSM = new BanksSelectionModel();
		BSM.setBanksList(banksListToView); 
		mav.addObject("bsmObject",BSM);
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/charts/addMultibank/{bankId}")
	public ModelAndView addBankToMultiList(@PathVariable int bankId) {
		Banks bank = servicePool.getBankService().getBankById(bankId);
		if(bank != null )
			MultiBanksAnalysisController.addBankToBanksList(bank);
		return getMultiYearBankSelectionView();
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/charts/allMultiBanks")
	public ModelAndView allMultiBanks() {
		MultiBanksAnalysisController.flushLists();
		MultiBanksAnalysisController.setBanksList(servicePool.getBankService().GetAllBanks());
		return getDashBoardsTimeSpan();
	}
	
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
		System.out.println("get dash boards with attrib "+stDate+"-"+fnDate);
		if(stDate <= 0 || fnDate <=0 ) {
			return MasterService.sendGeneralError("date should be positive value") ;
		}
		if(stDate > fnDate) {
			return MasterService.sendGeneralError("start date should be less than end date") ; 
		}
		
		MultiBanksAnalysisController.setYearSpanStart(stDate);
		MultiBanksAnalysisController.setYearSpanEnd(fnDate);
		ModelAndView mav = new ModelAndView("Charts/multiCharts");
		return mav ; 
	}	
	
	
	// MultiBanks Single Year 
	

	
	
	@RequestMapping(method = RequestMethod.GET , value = "/charts/singlebankSelection")
	public ModelAndView getBankSelectionView() {
		ModelAndView mav = new ModelAndView("Charts/bankSelection");
		List<Banks> banksList = servicePool.getBankService().GetAllBanks() ; 
		List<Banks> banksListToView = new ArrayList<Banks>(); 
		for(Banks bank : banksList) {
			if(!MultiBanksSingleYearAnalysisController.containsBank(bank)) {
				banksListToView.add(bank);
			}
		}
		BanksSelectionModel BSM = new BanksSelectionModel();
		BSM.setBanksList(banksListToView); 
		mav.addObject("bsmObject",BSM);
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/charts/addbank/{bankId}")
	public ModelAndView addBankToList(@PathVariable int bankId) {
		Banks bank = servicePool.getBankService().getBankById(bankId);
		if(bank != null )
			MultiBanksSingleYearAnalysisController.addBanksToBanksList(bank);
		return getBankSelectionView();
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/charts/allSingleBanks")
	public ModelAndView allSingleBanks() {
		MultiBanksSingleYearAnalysisController.flushLists();
		MultiBanksSingleYearAnalysisController.setBanksList(servicePool.getBankService().GetAllBanks());
		return getSingleTimeSpan();
	}
	
	
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
		ModelAndView mav = new ModelAndView("Charts/singleYearCharts");
		return mav ; 
	}	
	//

	
}
