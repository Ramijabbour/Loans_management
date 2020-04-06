package com.example.settelmets;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.MQ.SettledChaque;
import com.example.settelmets.SettlementService;
import com.itextpdf.kernel.geom.Path;
import com.itextpdf.kernel.pdf.canvas.parser.clipper.Paths;
import com.itextpdf.styledxmlparser.css.media.MediaType;

@RestController
public class ReportsController {

	@Autowired 
	private ReportsService reportsService ;

	@Autowired
	private SettlementService settlementService ; 
	
	@Autowired 
	private ReportLinkService reportLinkService ; 
	
	@RequestMapping(method = RequestMethod.GET,value = "/settlement/checks/settled/reports/export/{id}")
	public ModelAndView getExportIndex(@PathVariable int id ) {
		SettledChaque settledCheck = this.settlementService.findCheckByID(id);
		ModelAndView mav = new ModelAndView("DOC/exportHandler");
		mav.addObject("check",settledCheck);
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/settled/reports/export")
	@ResponseBody
	public void  getDocxReport(@Param(value ="id") int id){
		SettledChaque settledChaque = this.settlementService.findCheckByID(id) ; 
		ReportsLinkModel reportsLinkModel = this.reportLinkService.getRportLinkModel(settledChaque.getId(),1) ; 	
		String path ; 
		if(reportsLinkModel == null ) {
			path = this.reportsService.exportDocx(settledChaque);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsLinkModel(settledChaque.getId(),path,1);
			this.reportLinkService.addReportLinkModel(reportsLinkModel,1);
		}else {
			path = reportsLinkModel.getPath() ; 
		}
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/settled/reports/export/pdf/{id}")
	public void getPdfReport(@PathVariable int id){
		SettledChaque settledChaque = this.settlementService.findCheckByID(id) ; 
		ReportsLinkModel reportsLinkModel = this.reportLinkService.getRportLinkModel(settledChaque.getId(),2) ; 	
		String path ; 
		if(reportsLinkModel == null) {
			path = this.reportsService.excportPDF(settledChaque);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsLinkModel(settledChaque.getId(),path,2);
			this.reportLinkService.addReportLinkModel(reportsLinkModel,2);
		}else {
			path = reportsLinkModel.getPath() ; 
		}
		
	
	
	}

	
	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/settled/reports/export/xml/{id}")
	public ModelAndView getXmlReport(@PathVariable int id) {
		SettledChaque settledChaque = this.settlementService.findCheckByID(id) ; 
		return this.reportsService.exportXml(settledChaque);
	}


	
}
