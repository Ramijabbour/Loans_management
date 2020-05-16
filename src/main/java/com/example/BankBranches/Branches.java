package com.example.BankBranches;

import javax.persistence.*;

import com.example.Banks.Banks;
import com.example.aspect.EncryptDecrypt.StringEncryptDecryptConverter;

@Entity
@Table(name = "Branches",indexes = {@Index(name = "index_brancheCode",  columnList="brancheCode", unique = false)})
public class Branches {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id ;
	
	@Convert(converter = StringEncryptDecryptConverter.class)
	public String branchName ="";
	
	@Convert(converter = StringEncryptDecryptConverter.class)
	public String brancheCode ="";
	

    @ManyToOne
    private Banks bank = null;

	
	public Branches() {
	}



	public Branches(String branchName, String bankCode) {
		super();
		this.branchName = branchName;
		brancheCode = bankCode;
	}



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
