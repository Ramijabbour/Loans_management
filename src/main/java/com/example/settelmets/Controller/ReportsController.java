package com.example.settelmets.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import com.example.Loans.LoanService;
import com.example.Loans.Loans;
import com.example.Vouchers.VoucherService;
import com.example.Vouchers.Vouchers;
import com.example.settelmets.Models.ReportsLinkModel;
import com.example.settelmets.Models.SettledChaque;
import com.example.settelmets.Services.ReportLinkService;
import com.example.settelmets.Services.ReportsService;
import com.example.settelmets.Services.SettlementService;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
public class ReportsController {

	@Autowired
	private ReportsService reportsService ;

	@Autowired
	private SettlementService settlementService ;

	@Autowired
	private ReportLinkService reportLinkService ;
	
	@Autowired
	private LoanService loanService;

	@Autowired
	private VoucherService voucherservice;


	@RequestMapping(method = RequestMethod.GET,value = "/settlement/checks/settled/reports/export")
	public ModelAndView getExportIndex(@Param(value ="id") int id ) {
		SettledChaque settledCheck = this.settlementService.findCheckByID(id);
		ModelAndView mav = new ModelAndView("DOC/exportHandler");
		mav.addObject("check",settledCheck);
		return mav ;
	}
	
	@RequestMapping(method = RequestMethod.GET , value = "/settlement/checks/settled/reports/export/html")
	public ModelAndView getXmlReport(@Param(value ="id") int id) {
		SettledChaque settledChaque = this.settlementService.findCheckByID(id) ;
		return this.reportsService.exportXml(settledChaque);
	}

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
		}
		catch(FileNotFoundException fe) {
			this.reportLinkService.deleteReportLink(reportsLinkModel);
			path = this.reportsService.exportDocx(settledChaque);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsLinkModel(settledChaque.getId(),path,1);
			this.reportLinkService.addReportLinkModel(reportsLinkModel,1);
			try {
				return getSteamingDocxFile(response,path);
			} catch (IOException e) {
				e.printStackTrace();
				return null ; 
			}
		}
		catch(Exception e ) {
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
		}
		catch(FileNotFoundException fe) {
			this.reportLinkService.deleteReportLink(reportsLinkModel);
			path = this.reportsService.excportPDF(settledChaque);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsLinkModel(settledChaque.getId(),path,2);
			this.reportLinkService.addReportLinkModel(reportsLinkModel,2);
			try {
				return this.getSteamingPdfFile(response, path);
			} catch (IOException e) {
				e.printStackTrace();
				return null ; 
			}
		}
		
		catch(Exception e ) {
			e.printStackTrace();  
			return null ; 
		}
	}

////////////////////////////////////////////////////////////
	

	//openLoanHandler
		@RequestMapping(method = RequestMethod.GET,value = "/OpenLoan/reports/export")
		public ModelAndView getOpenExportIndex(@Param(value ="id") int id ) {
			Loans Loan = this.loanService.getOneByID(id);
			ModelAndView mav = new ModelAndView("DOC/exportOpenLoanHandler");
			mav.addObject("loan",Loan);
			return mav ;
		}
	
	
	
	
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/OpenLoan/reports/export/html")
	public ModelAndView getOpenXmlReport(@Param(value ="id") int id) {
		Loans Loan = this.loanService.getOneByID(id) ;
		return this.reportsService.exportOpenLoanXml(Loan);
	}

	@RequestMapping(method = RequestMethod.GET , value = "/OpenLoan/reports/export/docx")
	@ResponseBody
	public StreamingResponseBody getOpenDocxReport(@Param(value ="id") int id , HttpServletResponse response ){
		Loans Loan = this.loanService.getOneByID(id) ; 
		ReportsLinkModel reportsLinkModel = this.reportLinkService.getRportLinkModel(Loan.getId(),1) ;
		String path ;
		try {
		if(reportsLinkModel == null ) {
			path = this.reportsService.exportOpenLoanDocx(Loan);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsLinkModel(Loan.getId(),path,1);
			this.reportLinkService.addReportLinkModel(reportsLinkModel,1);
			return getSteamingDocxFile(response,path);
		}else {
			path = reportsLinkModel.getPath() ; 
			return getSteamingDocxFile(response,path);
		}
		}
		catch(FileNotFoundException fe) {
			this.reportLinkService.deleteReportLink(reportsLinkModel);
			path = this.reportsService.exportOpenLoanDocx(Loan);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsLinkModel(Loan.getId(),path,1);
			this.reportLinkService.addReportLinkModel(reportsLinkModel,1);
			try {
				return getSteamingDocxFile(response,path);
			} catch (IOException e) {
				e.printStackTrace();
				return null ; 
			}
		}
		catch(Exception e ) {
			e.printStackTrace(); 
			return null;	
		}	
	}

	@RequestMapping(method = RequestMethod.GET , value = "/OpenLoan/reports/export/pdf")
	@ResponseBody
	public StreamingResponseBody getOpenPdfReport(@Param(value ="id") int id,HttpServletResponse response){
		Loans Loan = this.loanService.getOneByID(id);
		ReportsLinkModel reportsLinkModel = this.reportLinkService.getRportLinkModel(Loan.getId(),2) ;
		String path ;
		try {
		if(reportsLinkModel == null) {
			path = this.reportsService.excportOpenLoanPDF(Loan);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsLinkModel(Loan.getId(),path,2);
			this.reportLinkService.addReportLinkModel(reportsLinkModel,2);
			return this.getSteamingPdfFile(response, path);
		}else {
			path = reportsLinkModel.getPath() ; 
			return this.getSteamingPdfFile(response, path);
		}
		}
		catch(FileNotFoundException fe) {
			this.reportLinkService.deleteReportLink(reportsLinkModel);
			path = this.reportsService.excportOpenLoanPDF(Loan);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsLinkModel(Loan.getId(),path,2);
			this.reportLinkService.addReportLinkModel(reportsLinkModel,2);
			try {
				return this.getSteamingPdfFile(response, path);
			} catch (IOException e) {
				e.printStackTrace();
				return null ; 
			}
		}
		
		catch(Exception e ) {
			e.printStackTrace();  
			return null ; 
		}
	}


	

	
//////////////////////////////////////////////////////////////////////////////

	
	

	//openLoanHandler // regular
		@RequestMapping(method = RequestMethod.GET,value = "/OpenLoan/regular/reports/export")
		public ModelAndView getOpenRegExportIndex(@Param(value ="id") int id ) {
			Loans Loan = this.loanService.getOneByID(id);
			ModelAndView mav = new ModelAndView("DOC/exportOpenLoanRegHandler");
			mav.addObject("loan",Loan);
			return mav ;
		}
	
	
	
	
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/OpenLoan/regular/reports/export/html")
	public ModelAndView getOpenRegXmlReport(@Param(value ="id") int id) {
		Loans Loan = this.loanService.getOneByID(id) ;
		return this.reportsService.exportOpenLoanRegXml(Loan);
	}

	@RequestMapping(method = RequestMethod.GET , value = "/OpenLoan/regular/reports/export/docx")
	@ResponseBody
	public StreamingResponseBody getOpenRegDocxReport(@Param(value ="id") int id , HttpServletResponse response ){
		Loans Loan = this.loanService.getOneByID(id) ; 
		ReportsLinkModel reportsLinkModel = this.reportLinkService.getRportLinkModel(Loan.getId(),1) ;
		String path ;
		try {
		if(reportsLinkModel == null ) {
			path = this.reportsService.exportOpenLoanRegDocx(Loan);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsLinkModel(Loan.getId(),path,1);
			this.reportLinkService.addReportLinkModel(reportsLinkModel,1);
			return getSteamingDocxFile(response,path);
		}else {
			path = reportsLinkModel.getPath() ; 
			return getSteamingDocxFile(response,path);
		}
		}
		catch(FileNotFoundException fe) {
			this.reportLinkService.deleteReportLink(reportsLinkModel);
			path = this.reportsService.exportOpenLoanRegDocx(Loan);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsLinkModel(Loan.getId(),path,1);
			this.reportLinkService.addReportLinkModel(reportsLinkModel,1);
			try {
				return getSteamingDocxFile(response,path);
			} catch (IOException e) {
				e.printStackTrace();
				return null ; 
			}
		}
		catch(Exception e ) {
			e.printStackTrace(); 
			return null;	
		}	
	}

	@RequestMapping(method = RequestMethod.GET , value = "/OpenLoan/regular/reports/export/pdf")
	@ResponseBody
	public StreamingResponseBody getOpenRegPdfReport(@Param(value ="id") int id,HttpServletResponse response){
		Loans Loan = this.loanService.getOneByID(id);
		ReportsLinkModel reportsLinkModel = this.reportLinkService.getRportLinkModel(Loan.getId(),2) ;
		String path ;
		try {
		if(reportsLinkModel == null) {
			path = this.reportsService.excportOpenLoanRegPDF(Loan);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsLinkModel(Loan.getId(),path,2);
			this.reportLinkService.addReportLinkModel(reportsLinkModel,2);
			return this.getSteamingPdfFile(response, path);
		}else {
			path = reportsLinkModel.getPath() ; 
			return this.getSteamingPdfFile(response, path);
		}
		}
		catch(FileNotFoundException fe) {
			this.reportLinkService.deleteReportLink(reportsLinkModel);
			path = this.reportsService.excportOpenLoanRegPDF(Loan);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsLinkModel(Loan.getId(),path,2);
			this.reportLinkService.addReportLinkModel(reportsLinkModel,2);
			try {
				return this.getSteamingPdfFile(response, path);
			} catch (IOException e) {
				e.printStackTrace();
				return null ; 
			}
		}
		
		catch(Exception e ) {
			e.printStackTrace();  
			return null ; 
		}
	}


	
	

		
	
//////////////////////////////////////////////////////////////////////////////



//LoanInfoHandler
	@RequestMapping(method = RequestMethod.GET, value = "/LoanInfo/reports/export")
	public ModelAndView getInfoExportIndex(@Param(value = "id") int id) {
		Loans Loan = this.loanService.getOneByID(id);
		ModelAndView mav = new ModelAndView("DOC/exportLoanInfoHandler");
		mav.addObject("loan", Loan);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/LoanInfo/reports/export/html")
	public ModelAndView getInfoXmlReport(@Param(value = "id") int id) {
		Loans Loan = this.loanService.getOneByID(id);
		return this.reportsService.exportLoanInfoXml(Loan);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/LoanInfo/reports/export/docx")
	@ResponseBody
	public StreamingResponseBody getInfoDocxReport(@Param(value = "id") int id, HttpServletResponse response) {
		Loans Loan = this.loanService.getOneByID(id);
		ReportsLinkModel reportsLinkModel = this.reportLinkService.getRportLinkModel(Loan.getId(), 1);
		String path;
		try {
			if (reportsLinkModel == null) {
				path = this.reportsService.exportLoanInfoDocx(Loan);
				System.out.println("file path : " + path);
				reportsLinkModel = new ReportsLinkModel(Loan.getId(), path, 1);
				this.reportLinkService.addReportLinkModel(reportsLinkModel, 1);
				return getSteamingDocxFile(response, path);
			} else {
				path = reportsLinkModel.getPath();
				return getSteamingDocxFile(response, path);
			}
		} catch (FileNotFoundException fe) {
			this.reportLinkService.deleteReportLink(reportsLinkModel);
			path = this.reportsService.exportLoanInfoDocx(Loan);
			System.out.println("file path : " + path);
			reportsLinkModel = new ReportsLinkModel(Loan.getId(), path, 1);
			this.reportLinkService.addReportLinkModel(reportsLinkModel, 1);
			try {
				return getSteamingDocxFile(response, path);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/LoanInfo/reports/export/pdf")
	@ResponseBody
	public StreamingResponseBody getInfoPdfReport(@Param(value = "id") int id, HttpServletResponse response) {
		Loans Loan = this.loanService.getOneByID(id);
		ReportsLinkModel reportsLinkModel = this.reportLinkService.getRportLinkModel(Loan.getId(), 2);
		String path;
		try {
			if (reportsLinkModel == null) {
				path = this.reportsService.excportLoanInfoPDF(Loan);
				System.out.println("file path : " + path);
				reportsLinkModel = new ReportsLinkModel(Loan.getId(), path, 2);
				this.reportLinkService.addReportLinkModel(reportsLinkModel, 2);
				return this.getSteamingPdfFile(response, path);
			} else {
				path = reportsLinkModel.getPath();
				return this.getSteamingPdfFile(response, path);
			}
		} catch (FileNotFoundException fe) {
			this.reportLinkService.deleteReportLink(reportsLinkModel);
			path = this.reportsService.excportLoanInfoPDF(Loan);
			System.out.println("file path : " + path);
			reportsLinkModel = new ReportsLinkModel(Loan.getId(), path, 2);
			this.reportLinkService.addReportLinkModel(reportsLinkModel, 2);
			try {
				return this.getSteamingPdfFile(response, path);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	
//////////////////////////////////////////////////////////////////
//CloseVoucherHandler
	@RequestMapping(method = RequestMethod.GET, value = "/CloseVoucher/reports/export")
	public ModelAndView getCloseExportIndex(@Param(value = "id") int id) {
		Vouchers Voucher = this.voucherservice.GetVoucherById(id);
		ModelAndView mav = new ModelAndView("DOC/exportCloseVoucherHandler");
		mav.addObject("voucher", Voucher);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/CloseVoucher/reports/exports/html")
	public ModelAndView getCloseXmlReport(@Param(value = "id") int id) {
		Vouchers Voucher = this.voucherservice.GetVoucherById(id);
		return this.reportsService.exportCloseLoanXml(Voucher);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/CloseVoucher/reports/exports/docx")
	@ResponseBody
	public StreamingResponseBody getCloseDocxReport(@Param(value = "id") int id, HttpServletResponse response) {
		Vouchers Voucher = this.voucherservice.GetVoucherById(id);
		ReportsLinkModel reportsLinkModel = this.reportLinkService.getRportLinkModel(Voucher.getLoan().getId(), 1);
		String path;
		try {
			if (reportsLinkModel == null) {
				path = this.reportsService.exportCloseLoanDocx(Voucher);
				System.out.println("file path : " + path);
				reportsLinkModel = new ReportsLinkModel(Voucher.getLoan().getId(), path, 1);
				this.reportLinkService.addReportLinkModel(reportsLinkModel, 1);
				return getSteamingDocxFile(response, path);
			} else {
				path = reportsLinkModel.getPath();
				return getSteamingDocxFile(response, path);
			}
		} catch (FileNotFoundException fe) {
			this.reportLinkService.deleteReportLink(reportsLinkModel);
			path = this.reportsService.exportCloseLoanDocx(Voucher);
			System.out.println("file path : " + path);
			reportsLinkModel = new ReportsLinkModel(Voucher.getLoan().getId(), path, 1);
			this.reportLinkService.addReportLinkModel(reportsLinkModel, 1);
			try {
				return getSteamingDocxFile(response, path);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/CloseVoucher/reports/export/pdf")
	@ResponseBody
	public StreamingResponseBody getClosePdfReport(@Param(value = "id") int id, HttpServletResponse response) {
		Vouchers Voucher = voucherservice.GetVoucherById(id);
		ReportsLinkModel reportsLinkModel = this.reportLinkService.getRportLinkModel(Voucher.getLoan().getId(), 2);
		String path;
		try {
			if (reportsLinkModel == null) {
				path = this.reportsService.excportCloseLoanPDF(Voucher);
				System.out.println("file path : " + path);
				reportsLinkModel = new ReportsLinkModel(Voucher.getLoan().getId(), path, 2);
				this.reportLinkService.addReportLinkModel(reportsLinkModel, 2);
				return this.getSteamingPdfFile(response, path);
			} else {
				path = reportsLinkModel.getPath();
				return this.getSteamingPdfFile(response, path);
			}
		} catch (FileNotFoundException fe) {
			this.reportLinkService.deleteReportLink(reportsLinkModel);
			path = this.reportsService.excportCloseLoanPDF(Voucher);
			System.out.println("file path : " + path);
			reportsLinkModel = new ReportsLinkModel(Voucher.getLoan().getId(), path, 2);
			this.reportLinkService.addReportLinkModel(reportsLinkModel, 2);
			try {
				return this.getSteamingPdfFile(response, path);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



//////////////////////////////////////////////////////////////////
		
	
	
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
			inputStream.close();
		};
	}

	

	public StreamingResponseBody getSteamingPdfFile(HttpServletResponse response,String path) throws IOException {
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
			inputStream.close();
		};
		
	}

}
