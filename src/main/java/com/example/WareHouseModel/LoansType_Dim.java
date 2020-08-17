package com.example.WareHouseModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class LoansType_Dim {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int LoanTypeID ;
	private String TypeName="";
	
	
	public LoansType_Dim() {}
	

	
	public LoansType_Dim(int loanTypeID, String typeName) {
		super();
		LoanTypeID = loanTypeID;
		TypeName = typeName;
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
