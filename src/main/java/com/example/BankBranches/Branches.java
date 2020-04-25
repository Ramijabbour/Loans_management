package com.example.BankBranches;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.example.Banks.Banks;
import com.example.aspect.EncryptDecrypt.StringEncryptDecryptConverter;

@Entity
public class Branches {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int BrancheID ;
	
	@Convert(converter = StringEncryptDecryptConverter.class)
	public String BranchName="";
	
	@Convert(converter = StringEncryptDecryptConverter.class)
	public String BrancheCode="";
	

    @ManyToOne
    private Banks bank = null;

	
	public Branches() {
	}



	public Branches(String branchName, String bankCode) {
		super();
		BranchName = branchName;
		BrancheCode = bankCode;
	}



	public int getBrancheID() {
		return BrancheID;
	}



	public void setBrancheID(int brancheID) {
		BrancheID = brancheID;
	}



	public String getBranchName() {
		return BranchName;
	}



	public void setBranchName(String branchName) {
		BranchName = branchName;
	}



	public String getBrancheCode() {
		return BrancheCode;
	}

	public void setBrancheCode(String brancheCode) {
		BrancheCode = brancheCode;
	}

	public Banks getBank() {
		return bank;
	}
	public void setBank(Banks bank) {
		this.bank = bank;
	}

	
}
