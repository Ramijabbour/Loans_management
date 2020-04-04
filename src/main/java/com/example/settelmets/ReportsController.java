package com.example.settelmets;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.MQ.SettledChaque;
import com.example.settelmets.SettlementService;

@RestController
public class ReportsController {

	@Autowired 
	private ReportsService reportsService ;

	@Autowired
	private SettlementService settlementService ; 
	
	@RequestMapping(method = RequestMethod.GET,value = "/settlement/checks/settled/reports/export/{id}")
	public ModelAndView getExportIndex(@PathVariable int id ) {
		SettledChaque settledCheck = this.settlementService.findCheckByID(id);
		ModelAndView mav = new ModelAndView("DOC/exportHandler");
		mav.addObject("check",settledCheck);
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/settled/reports/export/docx/{id}")
	public void getDocxReport(@PathVariable int id,HttpServletResponse response ) throws IOException{
		SettledChaque settledChaque = this.settlementService.findCheckByID(id) ; 
		this.reportsService.exportDocx(settledChaque);
		//response.sendRedirect("");//download Link 	
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/settled/reports/export/pdf/{id}")
	public void getPdfReport(@PathVariable int id , HttpServletResponse response )throws IOException {
		SettledChaque settledChaque = this.settlementService.findCheckByID(id) ; 
		this.reportsService.excportPDF(settledChaque);
		//response.sendRedirect("");//download Link
	}

	
	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/settled/reports/export/xml/{id}")
	public ModelAndView getXmlReport(@PathVariable int id) {
		SettledChaque settledChaque = this.settlementService.findCheckByID(id) ; 
		return this.reportsService.exportXml(settledChaque);
	}

	
	
}
