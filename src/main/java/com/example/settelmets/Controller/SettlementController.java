package com.example.settelmets.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.SiteConfig.SiteConfiguration;
import com.example.settelmets.Models.Chaque;
import com.example.settelmets.Models.SettledChaque;
import com.example.settelmets.Services.SettlementService;

@RestController
public class SettlementController {
	
	@Autowired
	SettlementService settlementService ;  
	
	//add protection or lock the method
	@RequestMapping(method = RequestMethod.GET ,value = "/settlement/invoke" )
<<<<<<< HEAD
	public String invokeSettleMethod() {
=======
	public void invokeSettleMethod() throws ParseException {
>>>>>>> parent of 0b73682... Revert "commit"
		this.settlementService.settleChecks();
		return "ok";
	}
	

	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/all")
	public ModelAndView getAllChecks(@Param(value ="index") int index) {
		ModelAndView mav = new ModelAndView("settlement/allChecks");
		List<Chaque> allchecks = this.settlementService.getAllChecks(index);
		mav.addObject("checksList",allchecks);
		if(allchecks.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav ;
	}
	//----------------------Search by check id
	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/checkid")
	public ModelAndView getAllChecksbyCheckid(@Param(value ="index") int index,@Param(value ="checkId") int checkId) {
		ModelAndView mav = new ModelAndView("settlement/allChecks");
		List<Chaque> allchecks = this.settlementService.getAllChecksbyCheckId(checkId,index);
		mav.addObject("checksList",allchecks);
		if(allchecks.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav ;
	}

	@RequestMapping(method = RequestMethod.GET , value = "/settlement/operations/reports")
	public ModelAndView getSettlementReportModels() {
		ModelAndView mav = new ModelAndView("settlement/allreports");
		mav.addObject("reportsList", this.settlementService.getAllReports());
		return mav; 
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/settled/link/{id}")
	public ModelAndView getSettlementChecksFromReport(@PathVariable int id ) {
		ModelAndView mav = new ModelAndView("settlement/settledChecksList");
		mav.addObject("checksList", this.settlementService.getChecksByReport(id));
		return mav; 
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/settlement/operations/reports/link/{id}")
	public ModelAndView getSettlementReportsFromModel(@PathVariable int id ) {
		ModelAndView mav = new ModelAndView("settlement/SettledReportsList");
		mav.addObject("reportsList", this.settlementService.getSettledChecksByReport(id));
		return mav; 
	}
	
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/reports")
	public ModelAndView getSettledChecks(@Param(value ="index") int index) {
		ModelAndView mav = new ModelAndView("settlement/settled");
		List<SettledChaque> allcheck = this.settlementService.getSettledChecks(index);
		mav.addObject("checksList",allcheck);
		if(allcheck.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav ; 
	}	
		
	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/onhold")
	public ModelAndView getonHoldChecks(@Param(value ="index") int index) {
		ModelAndView mav = new ModelAndView("settlement/onHold");
		List<Chaque> allchecks = this.settlementService.getOnHoldChecks(index);
		mav.addObject("checksList",allchecks);
		if(allchecks.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/settled")
	public ModelAndView getTrueChecks(@Param(value ="index") int index) {
		ModelAndView mav = new ModelAndView("settlement/allsettledChecks");
		List<Chaque> allchecks = this.settlementService.getTrueChecks(index);
		mav.addObject("checksList",allchecks);
		if(allchecks.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav ; 
	}
	@RequestMapping(method = RequestMethod.GET , value = "/TTS")
	public ModelAndView getTTS() throws ParseException {
		ModelAndView mav = new ModelAndView("settlement/TTS");
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
		System.out.println(dtf.format(SettlementService.TTS));
		Date d1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa").parse(dtf.format(SettlementService.TTS));
		Calendar cl = Calendar. getInstance();
		cl.setTime(d1);
		cl. add(Calendar.HOUR, 2);
		Date d2 = cl.getTime();
		System.out.println(d2);
		mav.addObject("endDate",d2);
		return mav;
	}
	
	
	
}
