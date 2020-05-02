package com.example.Vouchers;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.example.Clients.Clients;
import com.example.Loans.Loans;
import com.example.aspect.EncryptDecrypt.StringEncryptDecryptConverter;
import com.example.security.user.User;



@Entity
public class Vouchers {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int VoucherID ;
	
	@Convert(converter = StringEncryptDecryptConverter.class)
	private String VoucherDate ;
	@Convert(converter = StringEncryptDecryptConverter.class)
	private String VoucherAmmount;
	@Convert(converter = StringEncryptDecryptConverter.class)
	private String Total ;
	@Convert(converter = StringEncryptDecryptConverter.class)
	private String NetAmmount;
	@Convert(converter = StringEncryptDecryptConverter.class)
	private String FundingRatio;
	@Convert(converter = StringEncryptDecryptConverter.class)
	private String Status ; 
	
	@ManyToOne
	private Clients client =null ;
	
	@ManyToOne
	private User user =null ;

	@ManyToOne
	private Loans Loan =null ;
	
	
	public Vouchers() {
		Status= "";
		FundingRatio = "";
		NetAmmount = "";
		VoucherDate = "";
		VoucherAmmount = "" ;
		Total = "";
	}
	
	public void initWithZeroValues() {
		Status= "NA";
		FundingRatio = "NA";
		NetAmmount = "NA";
		VoucherDate = "NA";
		VoucherAmmount = "NA" ;
		Total = "NA";
	}
	
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

	
	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
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

	public Loans getLoan() {
		return Loan;
	}

	public void setLoan(Loans loan) {
		Loan = loan;
	}
	
}
