package com.example.ReportsAndContracts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.ServicesPool;
import com.example.Loans.Loans;
import com.example.Vouchers.Vouchers;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
public class ReportsLoanController {

	@Autowired
	private ServicesPool servicePool ; 
	

////////////////////////////////////////////////////////////
	

	//openLoanHandler
		@RequestMapping(method = RequestMethod.GET,value = "/OpenLoan/reports/export/{id}")
		public ModelAndView getOpenExportIndex(@PathVariable int id ) {
			Loans Loan = servicePool.getLoansService().getOneByID(id);
			ModelAndView mav = new ModelAndView("DOC/exportOpenLoanHandler");
			mav.addObject("loan",Loan);
			return mav ;
		}
	
	
	
	
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/OpenLoan/reports/export/html")
	public ModelAndView getOpenXmlReport(@Param(value ="id") int id) {
		Loans Loan = servicePool.getLoansService().getOneByID(id) ;
		return servicePool.getReportsLoansService().exportOpenLoanXml(Loan);
	}

	@RequestMapping(method = RequestMethod.GET , value = "/OpenLoan/reports/export/docx")
	@ResponseBody
	public StreamingResponseBody getOpenDocxReport(@Param(value ="id") int id , HttpServletResponse response ){
		Loans Loan = servicePool.getLoansService().getOneByID(id) ; 
		ReportsAndContracts reportsLinkModel = servicePool.getReportsService().getRportLinkModel(Loan.getId(),1) ;
		String path ;
		try {
			path = servicePool.getReportsLoansService().exportOpenLoanDocx(Loan);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsAndContracts(Loan.getId(),1);
			servicePool.getReportsService().addReportLinkModel(reportsLinkModel,1);
			return getSteamingDocxFile(response,path);
		
		}
		catch(FileNotFoundException fe) {
			servicePool.getReportsService().deleteReportLink(reportsLinkModel);
			path = servicePool.getReportsLoansService().exportOpenLoanDocx(Loan);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsAndContracts(Loan.getId(),1);
			servicePool.getReportsService().addReportLinkModel(reportsLinkModel,1);
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
		Loans Loan = servicePool.getLoansService().getOneByID(id);
		ReportsAndContracts reportsLinkModel = servicePool.getReportsService().getRportLinkModel(Loan.getId(),2) ;
		String path ;
		try {
			path = servicePool.getReportsLoansService().excportOpenLoanPDF(Loan);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsAndContracts(Loan.getId(),2);
			servicePool.getReportsService().addReportLinkModel(reportsLinkModel,2);
			return this.getSteamingPdfFile(response, path);
		
		}
		catch(FileNotFoundException fe) {
			servicePool.getReportsService().deleteReportLink(reportsLinkModel);
			path = servicePool.getReportsLoansService().excportOpenLoanPDF(Loan);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsAndContracts(Loan.getId(),2);
			servicePool.getReportsService().addReportLinkModel(reportsLinkModel,2);
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
		@RequestMapping(method = RequestMethod.GET,value = "/OpenLoan/regular/reports/export/{id}")
		public ModelAndView getOpenRegExportIndex(@PathVariable int id ) {
			Loans Loan = servicePool.getLoansService().getOneByID(id);
			ModelAndView mav = new ModelAndView("DOC/exportOpenLoanRegHandler");
			mav.addObject("loan",Loan);
			return mav ;
		}
	
	
	
	
	
	
	@RequestMapping(method = RequestMethod.GET , value = "/OpenLoan/regular/reports/export/html")
	public ModelAndView getOpenRegXmlReport(@Param(value ="id") int id) {
		Loans Loan = servicePool.getLoansService().getOneByID(id) ;
		return servicePool.getReportsLoansService().exportOpenLoanRegXml(Loan);
	}

	@RequestMapping(method = RequestMethod.GET , value = "/OpenLoan/regular/reports/export/docx")
	@ResponseBody
	public StreamingResponseBody getOpenRegDocxReport(@Param(value ="id") int id , HttpServletResponse response ){
		Loans Loan = servicePool.getLoansService().getOneByID(id) ; 
		ReportsAndContracts reportsLinkModel = servicePool.getReportsService().getRportLinkModel(Loan.getId(),1) ;
		String path ;
		try {
			path = servicePool.getReportsLoansService().exportOpenLoanRegDocx(Loan);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsAndContracts(Loan.getId(),1);
			servicePool.getReportsService().addReportLinkModel(reportsLinkModel,1);
			return getSteamingDocxFile(response,path);
	
		}
		catch(FileNotFoundException fe) {
			servicePool.getReportsService().deleteReportLink(reportsLinkModel);
			path = servicePool.getReportsLoansService().exportOpenLoanRegDocx(Loan);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsAndContracts(Loan.getId(),1);
			servicePool.getReportsService().addReportLinkModel(reportsLinkModel,1);
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
		Loans Loan = servicePool.getLoansService().getOneByID(id);
		ReportsAndContracts reportsLinkModel = servicePool.getReportsService().getRportLinkModel(Loan.getId(),2) ;
		String path ;
		try {
			path = servicePool.getReportsLoansService().excportOpenLoanRegPDF(Loan);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsAndContracts(Loan.getId(),2);
			servicePool.getReportsService().addReportLinkModel(reportsLinkModel,2);
			return this.getSteamingPdfFile(response, path);
		
		}
		catch(FileNotFoundException fe) {
			servicePool.getReportsService().deleteReportLink(reportsLinkModel);
			path = servicePool.getReportsLoansService().excportOpenLoanRegPDF(Loan);
			System.out.println("file path : "+path );
			reportsLinkModel = new ReportsAndContracts(Loan.getId(),2);
			servicePool.getReportsService().addReportLinkModel(reportsLinkModel,2);
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
	@RequestMapping(method = RequestMethod.GET, value = "/LoanInfo/reports/export/{id}")
	public ModelAndView getInfoExportIndex(@PathVariable int id) {
		Loans Loan = servicePool.getLoansService().getOneByID(id);
		ModelAndView mav = new ModelAndView("DOC/exportLoanInfoHandler");
		mav.addObject("loan", Loan);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/LoanInfo/reports/export/html/{id}")
	public ModelAndView getInfoXmlReport(@PathVariable int id) {
		Loans Loan = servicePool.getLoansService().getOneByID(id);
		return servicePool.getReportsLoansService().exportLoanInfoXml(Loan);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/LoanInfo/reports/export/docx")
	@ResponseBody
	public StreamingResponseBody getInfoDocxReport(@Param(value = "id") int id, HttpServletResponse response) {
		Loans Loan = servicePool.getLoansService().getOneByID(id);
		ReportsAndContracts reportsLinkModel = servicePool.getReportsService().getRportLinkModel(Loan.getId(), 1);
		String path;
		try {
				path = servicePool.getReportsLoansService().exportLoanInfoDocx(Loan);
				System.out.println("file path : " + path);
				reportsLinkModel = new ReportsAndContracts(Loan.getId(),  1);
				servicePool.getReportsService().addReportLinkModel(reportsLinkModel, 1);
				return getSteamingDocxFile(response, path);
			
		} catch (FileNotFoundException fe) {
			servicePool.getReportsService().deleteReportLink(reportsLinkModel);
			path = servicePool.getReportsLoansService().exportLoanInfoDocx(Loan);
			System.out.println("file path : " + path);
			reportsLinkModel = new ReportsAndContracts(Loan.getId(),  1);
			servicePool.getReportsService().addReportLinkModel(reportsLinkModel, 1);
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
	Loans Loan = servicePool.getLoansService().getOneByID(id);
	ReportsAndContracts reportsLinkModel = servicePool.getReportsService().getRportLinkModel(Loan.getId(), 2);
		String path;
		try {
				path = servicePool.getReportsLoansService().exportLoanInfoPDF(Loan);
				System.out.println("file path : " + path);
				reportsLinkModel = new ReportsAndContracts(Loan.getId(),  2);
				servicePool.getReportsService().addReportLinkModel(reportsLinkModel, 2);
				return this.getSteamingPdfFile(response, path);
			
		} catch (FileNotFoundException fe) {
			servicePool.getReportsService().deleteReportLink(reportsLinkModel);
			path = servicePool.getReportsLoansService().exportLoanInfoPDF(Loan);
			System.out.println("file path : " + path);
			reportsLinkModel = new ReportsAndContracts(Loan.getId(),  2);
			servicePool.getReportsService().addReportLinkModel(reportsLinkModel, 2);
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
	@RequestMapping(method = RequestMethod.GET, value = "/CloseVoucher/reports/export/{id}")
	public ModelAndView getCloseExportIndex(@PathVariable int id) {
		Vouchers Voucher = servicePool.getVoucherService().GetVoucherById(id);
		ModelAndView mav = new ModelAndView("DOC/exportCloseVoucherHandler");
		mav.addObject("voucher", Voucher);
		
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/CloseVoucher/reports/export/html")
	public ModelAndView getCloseXmlReport(@Param(value = "id") int id) {
		Vouchers Voucher = servicePool.getVoucherService().GetVoucherById(id);
		return servicePool.getReportsLoansService().exportCloseLoanXml(Voucher);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/CloseVoucher/reports/export/docx")
	@ResponseBody
	public StreamingResponseBody getCloseDocxReport(@Param(value = "id") int id, HttpServletResponse response) {
		Vouchers Voucher = servicePool.getVoucherService().GetVoucherById(id);
		ReportsAndContracts reportsLinkModel = servicePool.getReportsService().getRportLinkModel(Voucher.getLoan().getId(), 1);
		String path;
		try {
				path = servicePool.getReportsLoansService().exportCloseLoanDocx(Voucher);
				System.out.println("file path : " + path);
				reportsLinkModel = new ReportsAndContracts(Voucher.getLoan().getId(),  1);
				servicePool.getReportsService().addReportLinkModel(reportsLinkModel, 1);
				return getSteamingDocxFile(response, path);
			
		} catch (FileNotFoundException fe) {
			servicePool.getReportsService().deleteReportLink(reportsLinkModel);
			path = servicePool.getReportsLoansService().exportCloseLoanDocx(Voucher);
			System.out.println("file path : " + path);
			reportsLinkModel = new ReportsAndContracts(Voucher.getLoan().getId(),  1);
			servicePool.getReportsService().addReportLinkModel(reportsLinkModel, 1);
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
		Vouchers Voucher = servicePool.getVoucherService().GetVoucherById(id);
		ReportsAndContracts reportsLinkModel = servicePool.getReportsService().getRportLinkModel(Voucher.getLoan().getId(), 2);
		String path;
		try {
				path = servicePool.getReportsLoansService().excportCloseLoanPDF(Voucher);
				System.out.println("file path : " + path);
				reportsLinkModel = new ReportsAndContracts(Voucher.getLoan().getId(), 2);
				servicePool.getReportsService().addReportLinkModel(reportsLinkModel, 2);
				return this.getSteamingPdfFile(response, path);
			
		} catch (FileNotFoundException fe) {
			servicePool.getReportsService().deleteReportLink(reportsLinkModel);
			path = servicePool.getReportsLoansService().excportCloseLoanPDF(Voucher);
			System.out.println("file path : " + path);
			reportsLinkModel = new ReportsAndContracts(Voucher.getLoan().getId(),  2);
			servicePool.getReportsService().addReportLinkModel(reportsLinkModel, 2);
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