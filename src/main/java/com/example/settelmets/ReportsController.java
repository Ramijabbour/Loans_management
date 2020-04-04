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
	
	@RequestMapping(method = RequestMethod.POST , value = "/settlement/checks/settled/reports/exort/docx")
	public void getDocxReport(@ModelAttribute SettledChaque settledChaque,HttpServletResponse response ) throws IOException{
		this.reportsService.exportDocx(settledChaque);
		response.sendRedirect("");//download Link 	
	}
	
	@RequestMapping(method = RequestMethod.POST , value = "/settlement/checks/settled/reports/export/pdf")
	public void getPdfReport(@ModelAttribute SettledChaque settledChaque , HttpServletResponse response )throws IOException {
		this.reportsService.excportPDF(settledChaque);
		response.sendRedirect("");//download Link
	}

	
	@RequestMapping(method = RequestMethod.GET , value = "/export/xml/{id}")
	public ModelAndView getXmlReport(@PathVariable int id) {
		SettledChaque sc = this.settlementService.findCheckByID(id) ; 
		return this.reportsService.exportXml(sc);
	}

	
	
}
