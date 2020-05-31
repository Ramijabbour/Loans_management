package com.DocumentTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.example.Loans.Loans;
import com.example.Vouchers.Vouchers;

public class CloseLoanHTML implements ViewCloseLoanDocTemplate {

	public CloseLoanHTML() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ModelAndView ViewCloseLoanDOC(Vouchers voucher , List<Vouchers> allvouchers) {


		Loans Loan = voucher.getLoan();
        String viewName = "DOC/closeloan";

        Map<String, Object> model = new HashMap<String, Object>();

        
        int x = 0 ;
		
		for(Vouchers v : allvouchers)
		{
			if(!v.getStatus().equalsIgnoreCase("paid"))
			{
				 x += Integer.parseInt(v.getVoucherAmmount());
			}
		}

        model.put("financeType", Loan.getFinanceType().getTypeName());
        model.put("Bank",Loan.getBranche().getBank().getBankName());
        model.put("reason", Loan.getPurpose());
        model.put("a", Loan.getClient().getClientName());
        model.put("b", Loan.getName());
        model.put("c", Loan.getTotalAmmount());
        model.put("d", voucher.getVoucherAmmount());
        model.put("e", x);	       
        model.put("Date", Loan.getLoanDate());


        return new ModelAndView(viewName , model);

	}
}
