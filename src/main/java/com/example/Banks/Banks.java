package com.example.Banks;

import ValidContent_Visitor.Valid_Visitable;
import ValidContent_Visitor.Visitor;

import javax.persistence.*;

import com.example.aspect.EncryptDecrypt.StringEncryptDecryptConverter;

@Entity
@Table(name = "Banks",indexes = {@Index(name = "index_bankName",  columnList="bankName", unique = false)})
public class Banks extends Valid_Visitable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int BankID ;

	@Convert(converter = StringEncryptDecryptConverter.class)
    public String bankName ="" ;
	@Convert(converter = StringEncryptDecryptConverter.class)
    public String FinancialAllocations ;

	
	public Banks() {}
	

	public Banks(String bankName ,String alloacation) {
		super();
		this.bankName = bankName;
		FinancialAllocations=alloacation;
	}

	public int getBankID() {
		return BankID;
	}

	public void setBankID(int bankID) {
		BankID = bankID;
	}

	
	public String getBankName() {
		return this.bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName ;
	}
	
	
	public String getFinancialAllocations() {
		return FinancialAllocations;
	}


	public void setFinancialAllocations(String financialAllocations) {
		FinancialAllocations = financialAllocations;
	}


	public boolean accept(Visitor visitor) { return visitor.visit(this); }}

