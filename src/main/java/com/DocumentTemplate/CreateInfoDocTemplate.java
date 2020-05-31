package com.DocumentTemplate;

import java.util.List;

import com.example.Loans.Loans;
import com.example.Vouchers.Vouchers;

public interface CreateInfoDocTemplate {

    public String CreateInfoLoanDoc(Loans Loan , List<Vouchers> allvouchers);

}
