package com.example.WareHouse;

import javax.persistence.Convert;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.example.Banks.Banks;
import com.example.aspect.EncryptDecrypt.StringEncryptDecryptConverter;

public class Branch_Bank_Dim {

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id ;
	
	@Convert(converter = StringEncryptDecryptConverter.class)
	public String branchName ="";
	
	@Convert(converter = StringEncryptDecryptConverter.class)
	public String brancheCode ="";
	





	public int getId() {
		return id;
	}



	public void setId(int brancheID) {
		id = brancheID;
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

	public Banks getBank() {
		return bank;
	}
	public void setBank(Banks bank) {
		this.bank = bank;
	}
}
