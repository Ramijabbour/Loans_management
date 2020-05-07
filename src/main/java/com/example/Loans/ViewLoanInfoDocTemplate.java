package com.example.Loans;

import org.springframework.web.servlet.ModelAndView;

import com.example.Loans.Loans;
import  com.example.Vouchers.Vouchers;

import java.util.List;

public interface ViewLoanInfoDocTemplate {
	 public ModelAndView ViewLoanInfoDOC(Loans Loan , List<Vouchers> allvouchers);

}
