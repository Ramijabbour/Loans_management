package com.example.models;

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
	
	
	
	
	
	
}
