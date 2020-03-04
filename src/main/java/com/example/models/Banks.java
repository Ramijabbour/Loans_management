package com.example.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Banks {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int BankID ;

    private String BranchName="";

	private String BankCode="";

	private int FinancialAllocations = 0 ;
  
	
	
	public Banks(String branchName, String bankCode, int financialAllocations) {
		super();
		BranchName = branchName;
		BankCode = bankCode;
		FinancialAllocations = financialAllocations;
	}

	public int getBankID() {
		return BankID;
	}

	public void setBankID(int bankID) {
		BankID = bankID;
	}

	public String getBranchName() {
		return BranchName;
	}

	public void setBranchName(String branchName) {
		BranchName = branchName;
	}

	public String getBankCode() {
		return BankCode;
	}

	public void setBankCode(String bankCode) {
		BankCode = bankCode;
	}

	public int getFinancialAllocations() {
		return FinancialAllocations;
	}

	public void setFinancialAllocations(int financialAllocations) {
		FinancialAllocations = financialAllocations;
	}
	
}
