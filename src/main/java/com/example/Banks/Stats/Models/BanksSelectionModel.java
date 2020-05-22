package com.example.Banks.Stats.Models;

import java.util.List ;

import com.example.Banks.Banks;

import java.util.ArrayList; 

public class BanksSelectionModel {
	
	List<BaseSelectionModel> banksList  = new ArrayList<BaseSelectionModel>(); 
	
	public BanksSelectionModel() {
	}
	
	public BanksSelectionModel(List<BaseSelectionModel> banksList) {
		super();
		this.banksList = banksList;
	}

	public List<BaseSelectionModel> getBanksList() {
		return banksList;
	}

	public void setBanksList(List<Banks> banksList) {
		for(Banks bank : banksList) {
			this.banksList.add(new BaseSelectionModel(bank,false));
		}
		
	}
	
	public void addBankBaseModel(BaseSelectionModel bsm) {
		this.banksList.add(bsm);
	}
	
}
