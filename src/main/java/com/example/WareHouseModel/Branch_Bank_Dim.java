package com.example.WareHouseModel;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.example.Banks.Banks;
import com.example.aspect.EncryptDecrypt.StringEncryptDecryptConverter;

@Entity
public class Branch_Bank_Dim {

	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int id ;
	
	public String branchName ="";
	
	public String brancheCode ="";
	
    public String bankName ="" ;

    public String FinancialAllocations ;

    
    
    
	public Branch_Bank_Dim() {}

	public Branch_Bank_Dim(int id, String branchName, String brancheCode, String bankName,
			String financialAllocations) {
		super();
		this.id = id;
		this.branchName = branchName;
		this.brancheCode = brancheCode;
		this.bankName = bankName;
		FinancialAllocations = financialAllocations;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBrancheCode() {
		return brancheCode;
	}

	public void setBrancheCode(String brancheCode) {
		this.brancheCode = brancheCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getFinancialAllocations() {
		return FinancialAllocations;
	}

	public void setFinancialAllocations(String financialAllocations) {
		FinancialAllocations = financialAllocations;
	}


}
