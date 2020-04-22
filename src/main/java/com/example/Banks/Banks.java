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

    public String BankName ="" ;
    
    public String FinancialAllocations ;

	
	public Banks() {}
	

	public Banks(String bankName ,String alloacation) {
		super();
		BankName = bankName;
		FinancialAllocations=alloacation;
	}

	public int getBankID() {
		return BankID;
	}

	public void setBankID(int bankID) {
		BankID = bankID;
	}

	
	public String getBankName() {
		return this.BankName; 
	}
	public void setBankName(String bankName) {
		this.BankName = bankName ; 
	}
	
	
	public String getFinancialAllocations() {
		return FinancialAllocations;
	}


	public void setFinancialAllocations(String financialAllocations) {
		FinancialAllocations = financialAllocations;
	}


	public boolean accept(Visitor visitor) { return visitor.visit(this); }}

