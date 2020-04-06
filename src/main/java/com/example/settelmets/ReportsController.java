package com.example.settelmets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.MQ.SettledChaque;
import com.example.settelmets.SettlementService;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
public class ReportsController {

	@Autowired 
	private ReportsService reportsService ;

	@Autowired
	private SettlementService settlementService ;

	@Autowired 
	private ReportLinkService reportLinkService ; 
	
	
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/settled/reports/export/docx")
	@ResponseBody
	public StreamingResponseBody getDocxReport(@Param(value ="id") int id , HttpServletResponse response ){
		SettledChaque settledChaque = this.settlementService.findCheckByID(id) ; 
		ReportsLinkModel reportsLinkModel = this.reportLinkService.getRportLinkModel(settledChaque.getId(),1) ; 	
		String path ; 
		try {
		if(reportsLinkModel == null ) {
			path = this.reportsService.exportDocx(settledChaque);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsLinkModel(settledChaque.getId(),path,1);
			this.reportLinkService.addReportLinkModel(reportsLinkModel,1);
			return getSteamingDocxFile(response,path);
		}else {
			path = reportsLinkModel.getPath() ; 
			return getSteamingDocxFile(response,path);
		}
		}catch(Exception e ) {
			e.printStackTrace(); 
			return null;	
		}	
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/settled/reports/export/pdf")
	@ResponseBody
	public StreamingResponseBody getPdfReport(@Param(value ="id") int id,HttpServletResponse response){
		SettledChaque settledChaque = this.settlementService.findCheckByID(id) ; 
		ReportsLinkModel reportsLinkModel = this.reportLinkService.getRportLinkModel(settledChaque.getId(),2) ; 	
		String path ; 
		try {
		if(reportsLinkModel == null) {
			path = this.reportsService.excportPDF(settledChaque);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsLinkModel(settledChaque.getId(),path,2);
			this.reportLinkService.addReportLinkModel(reportsLinkModel,2);
			return this.getSteamingPdfFile(response, path);
		}else {
			path = reportsLinkModel.getPath() ; 
			return this.getSteamingPdfFile(response, path);
		}
		}catch(Exception e ) {
			e.printStackTrace();  
			return null ; 
		}
	}

	
	public StreamingResponseBody getSteamingDocxFile(HttpServletResponse response,String path) throws IOException {
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"report.docx");
		InputStream inputStream = new FileInputStream(new File(path));
		return outputStream -> {
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
				System.out.println("Writing some bytes of file...");
				outputStream.write(data, 0, nRead);
			}
		};
	}
<<<<<<< HEAD
	@RequestMapping(value = "/downloadpdfFile", method = RequestMethod.GET)
	public StreamingResponseBody getSteamingFile2(HttpServletResponse response) throws IOException {
=======

	public StreamingResponseBody getSteamingPdfFile(HttpServletResponse response,String path) throws IOException {
>>>>>>> 4dad71d3a2b9462be30e72cb0e24dc39c79f78e7
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"report.pdf");
		InputStream inputStream = new FileInputStream(new File(path));
		return outputStream -> {
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
				System.out.println("Writing some bytes of file...");
				outputStream.write(data, 0, nRead);
			}
		};
	}
<<<<<<< HEAD
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


=======
>>>>>>> 4dad71d3a2b9462be30e72cb0e24dc39c79f78e7
	
}
