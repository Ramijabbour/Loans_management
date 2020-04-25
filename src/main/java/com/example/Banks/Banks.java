package com.example.Banks;

import ValidContent_Visitor.Valid_Visitable;
import ValidContent_Visitor.Visitor;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.aspect.EncryptDecrypt.StringEncryptDecryptConverter;

@Entity
public class Banks extends Valid_Visitable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int BankID ;

	@Convert(converter = StringEncryptDecryptConverter.class)
    public String BankName ="" ;
	@Convert(converter = StringEncryptDecryptConverter.class)
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

