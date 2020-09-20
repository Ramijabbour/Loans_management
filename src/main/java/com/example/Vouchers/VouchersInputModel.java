package com.example.Vouchers;

import java.util.List;

import com.example.Loans.Loans;

import java.util.ArrayList;

public class VouchersInputModel {
	
	
	List<Vouchers> vouchersList = new ArrayList<Vouchers>(); 
	
	private Loans loan ; 
	
	public VouchersInputModel(){}
	
	
	public VouchersInputModel(List<Vouchers> vouchersList, Loans loan) {
		this.vouchersList = vouchersList;
		this.loan = loan;
	}




	public void AddVoucher(Vouchers voucher ) {
		this.vouchersList.add(voucher);
	}


	public List<Vouchers> getVouchersList() {
		return vouchersList;
	}


	public void setVouchersList(List<Vouchers> vouchersList) {
		this.vouchersList = vouchersList;
	}

	public Loans getLoan() {
		return loan;
	}

	public void setLoan(Loans loan) {
		this.loan = loan;
	}


}
