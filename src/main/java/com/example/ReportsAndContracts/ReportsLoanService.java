package com.example.ReportsAndContracts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.DocumentTemplate.CloseLoanHTML;
import com.DocumentTemplate.CloseLoansReportDocs;
import com.DocumentTemplate.CloseLoansReportPDF;
import com.DocumentTemplate.LoanInfoDoc;
import com.DocumentTemplate.LoanInfoHTML;
import com.DocumentTemplate.LoanInfoPDF;
import com.DocumentTemplate.OpenLoanHTML;
import com.DocumentTemplate.OpenLoansReportDocs;
import com.DocumentTemplate.OpenLoansReportPDF;
import com.DocumentTemplate.RegularOpenLoanHTML;
import com.DocumentTemplate.RegularOpenLoansReportDocs;
import com.DocumentTemplate.RegularOpenLoansReportPDF;
import com.example.ServicesPool;
import com.example.Loans.Loans;

import com.example.Vouchers.Vouchers;

@Service
public class ReportsLoanService {
	
	
	@Autowired
	private ServicesPool servicePool ; 

	

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
		List<Vouchers> allvouchers = servicePool.getVoucherService().getVoucherForThisLoan(Loan.getId());	
		LoanInfoDoc docsGenerator = new LoanInfoDoc() ;
		String downloadPath = docsGenerator.CreateInfoLoanDoc(Loan, allvouchers);
		return downloadPath ; 
	}
	
	public ModelAndView exportLoanInfoXml(Loans Loan) {
		List<Vouchers> allvouchers = servicePool.getVoucherService().getVoucherForThisLoan(Loan.getId());	
		LoanInfoHTML xmlGenerator = new LoanInfoHTML(); 
		return xmlGenerator.ViewLoanInfoDOC(Loan, allvouchers);
		
	}
	
	public String exportLoanInfoPDF(Loans Loan) {
		List<Vouchers> allvouchers = servicePool.getVoucherService().getVoucherForThisLoan(Loan.getId());	
		LoanInfoPDF pdfGenerator = new  LoanInfoPDF();
		String downloadPath =  pdfGenerator.CreateInfoLoanDoc(Loan, allvouchers);
		return downloadPath ; 
	}

	public String exportCloseLoanDocx(Vouchers Voucher) {
		List<Vouchers> allvouchers = servicePool.getVoucherService().getVoucherForThisLoan(Voucher.getLoan().getId());	
		CloseLoansReportDocs docsGenerator = new CloseLoansReportDocs() ; 
		String downloadPath = docsGenerator.CreateCloseLoanDoc(Voucher, allvouchers);
		return downloadPath ; 
	}
	
	public ModelAndView exportCloseLoanXml(Vouchers Voucher) {
		List<Vouchers> allvouchers = servicePool.getVoucherService().getVoucherForThisLoan(Voucher.getLoan().getId());	
		CloseLoanHTML xmlGenerator = new CloseLoanHTML(); 
		return xmlGenerator.ViewCloseLoanDOC(Voucher, allvouchers);
		
	}
	
	public String excportCloseLoanPDF(Vouchers Voucher) {
		List<Vouchers> allvouchers = servicePool.getVoucherService().getVoucherForThisLoan(Voucher.getLoan().getId());	
		CloseLoansReportPDF pdfGenerator = new CloseLoansReportPDF(); 
		String downloadPath =  pdfGenerator.CreateCloseLoanDoc(Voucher, allvouchers);
		return downloadPath ; 
	}

	
}