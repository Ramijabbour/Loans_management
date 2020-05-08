package com.example.CloseLoans;

import org.springframework.web.servlet.ModelAndView;

import com.example.Loans.Loans;
import  com.example.Vouchers.Vouchers;

import java.util.List;

public interface ViewCloseLoanDocTemplate {
	 public ModelAndView ViewCloseLoanDOC(Vouchers voucher , List<Vouchers> allvouchers);

}
