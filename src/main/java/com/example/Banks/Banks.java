package com.example.Banks;

import ValidContent_Visitor.Valid_Visitable;
import ValidContent_Visitor.Visitor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Banks extends Valid_Visitable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int BankID ;

    public String BranchName="";
    
    public String BankName ="" ;
    
	public String BankCode="";

	public String FinancialAllocations  ;

	
	public Banks() {}
	
	public Banks(String bankName,String branchName, String bankCode, String financialAllocations) {
		super();
		this.BankName = bankName ; 
		this.BranchName = branchName;
		this.BankCode = bankCode;
		this.FinancialAllocations = financialAllocations;
	}
	
	
	public Banks(String branchName, String bankCode, String financialAllocations) {
		
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

	public String getFinancialAllocations() {
		return FinancialAllocations;
	}

	public void setFinancialAllocations(String financialAllocations) {
		FinancialAllocations = financialAllocations;
	}

	public String getBankName() {
		return this.BankName; 
	}
	public void setBankName(String bankName) {
		this.BankName = bankName ; 
	}
	public boolean accept(Visitor visitor) { return visitor.visit(this); }}