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
	
	@RequestMapping(method = RequestMethod.GET,value = "/settlement/checks/settled/reports/export/{id}")
	public ModelAndView getExportIndex(@PathVariable int id ) {
		SettledChaque settledCheck = this.settlementService.findCheckByID(id);
		ModelAndView mav = new ModelAndView("DOC/exportHandler");
		mav.addObject("check",settledCheck);
		return mav ; 
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/settled/reports/export")
	@ResponseBody
	public FileSystemResource  getDocxReport(@Param(value ="id") int id ,HttpServletResponse response ) throws IOException{
		SettledChaque settledChaque = this.settlementService.findCheckByID(id) ; 
		String path = this.reportsService.exportDocx(settledChaque);
		System.out.println("file path : "+path );
		FileSystemResource fsr = new FileSystemResource(new File(path));
		response.setContentType("application/force-download");
		return new FileSystemResource(new File(path));
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
