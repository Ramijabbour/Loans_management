package com.example.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.example.security.user.User;



@Entity
public class Vouchers {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int VoucherID ;
	
	private String VoucherDate ;
	
	private int VoucherAmmount;
	
	private int Total ; 
	
	private int NetAmmount;
	
	private int FundingRatio;
	
	@ManyToOne
	private Clients client =null ;
	
	@ManyToOne
	private User user =null ;

	
	
	public int getVoucherID() {
		return VoucherID;
	}

	public void setVoucherID(int voucherID) {
		VoucherID = voucherID;
	}

	public String getVoucherDate() {
		return VoucherDate;
	}

	public void setVoucherDate(String voucherDate) {
		VoucherDate = voucherDate;
	}

	public int getVoucherAmmount() {
		return VoucherAmmount;
	}

	public void setVoucherAmmount(int voucherAmmount) {
		VoucherAmmount = voucherAmmount;
	}

	public int getTotal() {
		return Total;
	}

	public void setTotal(int total) {
		Total = total;
	}

	public int getNetAmmount() {
		return NetAmmount;
	}

	public void setNetAmmount(int netAmmount) {
		NetAmmount = netAmmount;
	}

	public int getFundingRatio() {
		return FundingRatio;
	}

	public void setFundingRatio(int fundingRatio) {
		FundingRatio = fundingRatio;
	}

	public Clients getClient() {
		return client;
	}

	public void setClient(Clients client) {
		this.client = client;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	
}
