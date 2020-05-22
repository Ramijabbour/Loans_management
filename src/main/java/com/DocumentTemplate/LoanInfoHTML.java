package com.DocumentTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.example.Loans.Loans;
import com.example.Vouchers.Vouchers;
import com.itextpdf.text.pdf.PdfPCell;


public class LoanInfoHTML implements ViewLoanInfoDocTemplate {

	public LoanInfoHTML() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ModelAndView ViewLoanInfoDOC(Loans Loan,List<Vouchers> allvouchers) {


        String viewName = "DOC/loaninfo";

        Map<String, Object> model = new HashMap<String, Object>();

        List<String> Rests = new ArrayList<String>();

        int ammount = Integer.parseInt(Loan.getTotalAmmount());
        for(Vouchers v : allvouchers) {
                        
            ammount = ammount - Integer.parseInt(v.getVoucherAmmount());
            Rests.add(""+ammount);
        }
        
       
        
        model.put("Name", Loan.getName());
        model.put("Bank",Loan.getBranche().getBank().getBankName() + " - " + Loan.getBranche().getBranchName());
        model.put("vouchers", allvouchers);
        model.put("sndat", Loan.getNumberOfVoucher());
        model.put("cost", Loan.getTotalAmmount());
        model.put("totalAmmount", Loan.getTotalAmmount());
        model.put("totalRests", Loan.getTotalAmmount());
        if(Loan.getLoanType().getTypeName().equalsIgnoreCase("مر خص"))
        {
        	model.put("mr",  Loan.getTotalAmmount());
            model.put("ma","");
            	
        }
        else
        {
        	model.put("mr", "");
        	model.put("ma", Loan.getTotalAmmount());
        }
        model.put("Rests",Rests);
        
        
        model.put("Date", Loan.getLoanDate());


        return new ModelAndView(viewName , model);

	}
}
