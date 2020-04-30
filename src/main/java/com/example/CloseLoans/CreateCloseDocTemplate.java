package com.example.CloseLoans;

import java.util.List;

import com.example.Loans.Loans;
import com.example.Vouchers.Vouchers;

public interface CreateCloseDocTemplate {

    public String CreateCloseLoanDoc(Loans Loan , Vouchers voucher , List<Vouchers> allvouchers);

}
