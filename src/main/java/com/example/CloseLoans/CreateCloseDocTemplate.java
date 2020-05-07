package com.example.CloseLoans;

import java.util.List;

import com.example.Loans.Loans;
import com.example.Vouchers.Vouchers;

public interface CreateCloseDocTemplate {

    public String CreateCloseLoanDoc( Vouchers voucher , List<Vouchers> allvouchers);

}
