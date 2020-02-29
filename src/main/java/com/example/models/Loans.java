package com.example.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;



@Entity
public class Loans {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int LoanID ;
	
	private String FirstSide ;
	
	private String SecondSide ;
	
	private String WorkDate ;
	
	private String LoanDate ;
	
	private String FinanceType ;
	
	private int InterestRate ;
	
	private int DelayInterestRate ;
	
	private int FundintRate ;
	
	private String LoanType ;
	
	private int ClearanceNumber;
	
	private int TotalAmmount ;
	
	private String TotalAmmountAsString ;
	
	private int NetAmmount ;
	
	private String NetAmmountAsString ;
	
	private String NumberOfVoucherAsString ;
	
	private int NumberOfVoucher ;
	
	private String purpose ;
	
	@ManyToOne
	private Clients client =null ;
	
	@ManyToOne
	private Banks bank =null ;
	
	@ManyToOne
	private User user =null ;

	public int getLoanID() {
		return LoanID;
	}

	public void setLoanID(int loanID) {
		LoanID = loanID;
	}

	public String getFirstSide() {
		return FirstSide;
	}

	public void setFirstSide(String firstSide) {
		FirstSide = firstSide;
	}

	public String getSecondSide() {
		return SecondSide;
	}

	public void setSecondSide(String secondSide) {
		SecondSide = secondSide;
	}

	public String getWorkDate() {
		return WorkDate;
	}

	public void setWorkDate(String workDate) {
		WorkDate = workDate;
	}

	public String getLoanDate() {
		return LoanDate;
	}

	public void setLoanDate(String loanDate) {
		LoanDate = loanDate;
	}

	public String getFinanceType() {
		return FinanceType;
	}

	public void setFinanceType(String financeType) {
		FinanceType = financeType;
	}

	public int getInterestRate() {
		return InterestRate;
	}

	public void setInterestRate(int interestRate) {
		InterestRate = interestRate;
	}

	public int getDelayInterestRate() {
		return DelayInterestRate;
	}

	public void setDelayInterestRate(int delayInterestRate) {
		DelayInterestRate = delayInterestRate;
	}

	public int getFundintRate() {
		return FundintRate;
	}

	public void setFundintRate(int fundintRate) {
		FundintRate = fundintRate;
	}

	public String getLoanType() {
		return LoanType;
	}

	public void setLoanType(String loanType) {
		LoanType = loanType;
	}

	public int getClearanceNumber() {
		return ClearanceNumber;
	}

	public void setClearanceNumber(int clearanceNumber) {
		ClearanceNumber = clearanceNumber;
	}

	public int getTotalAmmount() {
		return TotalAmmount;
	}

	public void setTotalAmmount(int totalAmmount) {
		TotalAmmount = totalAmmount;
	}

	public String getTotalAmmountAsString() {
		return TotalAmmountAsString;
	}

	public void setTotalAmmountAsString(String totalAmmountAsString) {
		TotalAmmountAsString = totalAmmountAsString;
	}

	public int getNetAmmount() {
		return NetAmmount;
	}

	public void setNetAmmount(int netAmmount) {
		NetAmmount = netAmmount;
	}

	public String getNetAmmountAsString() {
		return NetAmmountAsString;
	}

	public void setNetAmmountAsString(String netAmmountAsString) {
		NetAmmountAsString = netAmmountAsString;
	}

	public String getNumberOfVoucherAsString() {
		return NumberOfVoucherAsString;
	}

	public void setNumberOfVoucherAsString(String numberOfVoucherAsString) {
		NumberOfVoucherAsString = numberOfVoucherAsString;
	}

	public int getNumberOfVoucher() {
		return NumberOfVoucher;
	}

	public void setNumberOfVoucher(int numberOfVoucher) {
		NumberOfVoucher = numberOfVoucher;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Clients getClient() {
		return client;
	}

	public void setClient(Clients client) {
		this.client = client;
	}

	public Banks getBank() {
		return bank;
	}

	public void setBank(Banks bank) {
		this.bank = bank;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}