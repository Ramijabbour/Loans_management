package com.DocumentTemplate;

import com.example.Vouchers.Vouchers;

public class RestAmount {

	private Vouchers voucher ;
	private String rests ;
	
	
	public RestAmount() {
		// TODO Auto-generated constructor stub
	}
	
	public void setRests(String rests) {
		this.rests = rests;
	}
	
	public String getRests() {
		return rests;
	}
	
	public void setVoucher(Vouchers voucher) {
		this.voucher = voucher;
	}
	
	public Vouchers getVoucher() {
		return voucher;
	}
}
