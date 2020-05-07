package com.example.OpenLoans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.example.Loans.Loans;

public class RegularOpenLoanHTML implements ViewOpenLoanDocTemplate {

	public RegularOpenLoanHTML() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ModelAndView ViewOpenLoanDOC(Loans Loan) {


        String viewName = "DOC/regularopenloan";

        Map<String, Object> model = new HashMap<String, Object>();

        
        model.put("LoanID", Loan.getLoanNumber());
        model.put("Bank",Loan.getBranche().getBank().getBankName() + " - " + Loan.getBranche().getBranchName());
        model.put("client", Loan.getClient().getClientName());
        model.put("sndat", Loan.getNumberOfVoucher());
        model.put("cost", Loan.getTotalAmmount());
        model.put("Date", Loan.getLoanDate());



        return new ModelAndView(viewName , model);

	}
}
