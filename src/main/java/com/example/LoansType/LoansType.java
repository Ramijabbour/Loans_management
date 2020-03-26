package com.example.LoansType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LoansType {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int LoanTypeID ;
	private String TypeName="";
	
	
	public LoansType(String loanTypeName ) {
		this.TypeName = loanTypeName ; 
	}
	
	public int getLoanTypeID() {
		return LoanTypeID;
	}
	public void setLoanTypeID(int loanTypeID) {
		LoanTypeID = loanTypeID;
	}
	public String getTypeName() {
		return TypeName;
	}
	public void setTypeName(String typeName) {
		TypeName = typeName;
	}
	
	
	
}
