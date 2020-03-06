package com.example.FinanceType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FinanceType {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int FinanceTypeID ;
	private String TypeName="";
	private int FundintRate ;
	private int lenght; 
	
	
	public FinanceType(String typeName,int fundingRatio,int len) {
		this.TypeName = typeName ; 
		this.FundintRate = fundingRatio ; 
		this.lenght = len  ;
	}
		
	public int getFundintRate() {
		return FundintRate;
	}

	public void setFundintRate(int fundintRate) {
		FundintRate = fundintRate;
	}
	
	public int getFinanceTypeID() {
		return FinanceTypeID;
	}
	public void setFinanceTypeID(int financeTypeID) {
		FinanceTypeID = financeTypeID;
	}
	public String getTypeName() {
		return TypeName;
	}
	public void setTypeName(String typeName) {
		TypeName = typeName;
	}

	
	public void setLenght(int len) {
		this.lenght = len ; 
	}
	public int getLenght() {
		return this.lenght ; 
	}
}
