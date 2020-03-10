package com.example.Vouchers;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.example.Clients.Clients;
import com.example.security.user.User;



@Entity
public class Vouchers {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int VoucherID ;
	
	private String VoucherDate ;
	
	private String VoucherAmmount;
	
	private String Total ;
	
	private String NetAmmount;
	
	private String FundingRatio;
	
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

	public String getVoucherAmmount() {
		return VoucherAmmount;
	}

	public void setVoucherAmmount(String voucherAmmount) {
		VoucherAmmount = voucherAmmount;
	}

	public String getTotal() {
		return Total;
	}

	public void setTotal(String total) {
		Total = total;
	}

	public String getNetAmmount() {
		return NetAmmount;
	}

	public void setNetAmmount(String netAmmount) {
		NetAmmount = netAmmount;
	}

	public String getFundingRatio() {
		return FundingRatio;
	}

	public void setFundingRatio(String fundingRatio) {
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
