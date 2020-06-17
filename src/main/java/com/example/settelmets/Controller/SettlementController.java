package com.example.settelmets.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
	public void invokeSettleMethod() {
		this.settlementService.settleChecks();
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
		ModelAndView mav = new ModelAndView("settlement/onHold");
		List<Chaque> allchecks = this.settlementService.getTrueChecks(index);
		mav.addObject("checksList",allchecks);
		if(allchecks.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav ; 
	}
	
	
	
}
