package com.example.Banks.Stats.ChartsHandler;

import com.example.Banks.Banks;

public class BaseSelectionModel {
	Banks bank ; 
	boolean selected ;
	
	
	public BaseSelectionModel() {}
	
	public BaseSelectionModel(Banks bank, boolean selected) {
		super();
		this.bank = bank;
		this.selected = selected;
	}
	
	public Banks getBank() {
		return bank;
	}
	public void setBank(Banks bank) {
		this.bank = bank;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	} 
	
	
	
}
