package com.example.settelmets.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.DocumentTemplate.DOCXDOC;
import com.DocumentTemplate.PDFDOC;
import com.DocumentTemplate.XMLDOC;
import com.example.CloseLoans.CloseLoanHTML;
import com.example.CloseLoans.CloseLoansReportDocs;
import com.example.CloseLoans.CloseLoansReportPDF;
import com.example.Loans.LoanInfoDoc;
import com.example.Loans.LoanInfoHTML;
import com.example.Loans.LoanInfoPDF;
import com.example.Loans.Loans;
import com.example.OpenLoans.OpenLoanHTML;
import com.example.OpenLoans.OpenLoansReportDocs;
import com.example.OpenLoans.OpenLoansReportPDF;
import com.example.OpenLoans.RegularOpenLoanHTML;
import com.example.OpenLoans.RegularOpenLoansReportDocs;
import com.example.OpenLoans.RegularOpenLoansReportPDF;
import com.example.Vouchers.VoucherService;
import com.example.Vouchers.Vouchers;
import com.example.settelmets.Models.SettledChaque;

@Service
public class ReportsService {
	
	
	@Autowired
	VoucherService voucherservice ;

	
	public String exportDocx(SettledChaque settledChaque) {
		DOCXDOC docsGenerator = new DOCXDOC() ; 
		String downloadPath = docsGenerator.CreateRTGSDoc(settledChaque);
		return downloadPath ; 
	}
	
	public ModelAndView exportXml(SettledChaque settledChaque) {
		XMLDOC xmlGenerator = new XMLDOC(); 
		return xmlGenerator.ViewRTGSDOC(settledChaque);
		
	}
	
	public String excportPDF(SettledChaque settledChaque) {
		PDFDOC pdfGenerator = new PDFDOC(); 
		String downloadPath =  pdfGenerator.CreateRTGSDoc(settledChaque);
		return downloadPath ; 
	}
	

	public String exportOpenLoanDocx(Loans Loan) {
		OpenLoansReportDocs docsGenerator = new OpenLoansReportDocs() ; 
		String downloadPath = docsGenerator.CreateOpenLoanDoc(Loan);
		return downloadPath ; 
	}
	
	public ModelAndView exportOpenLoanXml(Loans Loan) {
		OpenLoanHTML xmlGenerator = new OpenLoanHTML(); 
		return xmlGenerator.ViewOpenLoanDOC(Loan);
		
	}
	
	public String excportOpenLoanPDF(Loans Loan) {
		OpenLoansReportPDF pdfGenerator = new OpenLoansReportPDF(); 
		String downloadPath =  pdfGenerator.CreateOpenLoanDoc(Loan);
		return downloadPath ; 
	}
	
	

	public String exportOpenLoanRegDocx(Loans Loan) {
		RegularOpenLoansReportDocs docsGenerator = new RegularOpenLoansReportDocs() ; 
		String downloadPath = docsGenerator.CreateOpenLoanDoc(Loan);
		return downloadPath ; 
	}
	
	public ModelAndView exportOpenLoanRegXml(Loans Loan) {
		RegularOpenLoanHTML xmlGenerator = new RegularOpenLoanHTML(); 
		return xmlGenerator.ViewOpenLoanDOC(Loan);
		
	}
	
	public String excportOpenLoanRegPDF(Loans Loan) {
		RegularOpenLoansReportPDF pdfGenerator = new RegularOpenLoansReportPDF(); 
		String downloadPath =  pdfGenerator.CreateOpenLoanDoc(Loan);
		return downloadPath ; 
	}
	
	
	

	public String exportLoanInfoDocx(Loans Loan ) {
		List<Vouchers> allvouchers = voucherservice.getVoucherForThisLoan(Loan.getId());	
		LoanInfoDoc docsGenerator = new LoanInfoDoc() ;
		String downloadPath = docsGenerator.CreateInfoLoanDoc(Loan, allvouchers);
		return downloadPath ; 
	}
	
	public ModelAndView exportLoanInfoXml(Loans Loan) {
		List<Vouchers> allvouchers = voucherservice.getVoucherForThisLoan(Loan.getId());	
		LoanInfoHTML xmlGenerator = new LoanInfoHTML(); 
		return xmlGenerator.ViewLoanInfoDOC(Loan, allvouchers);
		
	}
	
	public String exportLoanInfoPDF(Loans Loan) {
		List<Vouchers> allvouchers = voucherservice.getVoucherForThisLoan(Loan.getId());	
		LoanInfoPDF pdfGenerator = new  LoanInfoPDF();
		String downloadPath =  pdfGenerator.CreateInfoLoanDoc(Loan, allvouchers);
		return downloadPath ; 
	}

	public String exportCloseLoanDocx(Vouchers Voucher) {
		List<Vouchers> allvouchers = voucherservice.getVoucherForThisLoan(Voucher.getLoan().getId());	
		CloseLoansReportDocs docsGenerator = new CloseLoansReportDocs() ; 
		String downloadPath = docsGenerator.CreateCloseLoanDoc(Voucher, allvouchers);
		return downloadPath ; 
	}
	
	public ModelAndView exportCloseLoanXml(Vouchers Voucher) {
		List<Vouchers> allvouchers = voucherservice.getVoucherForThisLoan(Voucher.getLoan().getId());	
		CloseLoanHTML xmlGenerator = new CloseLoanHTML(); 
		return xmlGenerator.ViewCloseLoanDOC(Voucher, allvouchers);
		
	}
	
	public String excportCloseLoanPDF(Vouchers Voucher) {
		List<Vouchers> allvouchers = voucherservice.getVoucherForThisLoan(Voucher.getLoan().getId());	
		CloseLoansReportPDF pdfGenerator = new CloseLoansReportPDF(); 
		String downloadPath =  pdfGenerator.CreateCloseLoanDoc(Voucher, allvouchers);
		return downloadPath ; 
	}

	
}
