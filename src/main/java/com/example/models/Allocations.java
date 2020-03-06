package com.example.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;



@Entity
public class Allocations {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int AllocationID ;
	
	private String AllocationDate ;
	
	private int AllocationAmmount;
	
	@ManyToOne
	private Banks banks =null ;

	
	public Allocations(String allocationDate , int ammount , Banks bank) {
		this.AllocationDate = allocationDate ; 
		this.AllocationAmmount = ammount ; 
		this.banks = bank ; 
	}
	
	public int getAllocationID() {
		return AllocationID;
	}

	public void setAllocationID(int allocationID) {
		AllocationID = allocationID;
	}

	public String getAllocationDate() {
		return AllocationDate;
	}

	public void setAllocationDate(String allocationDate) {
		AllocationDate = allocationDate;
	}

	public int getAllocationAmmount() {
		return AllocationAmmount;
	}

	public void setAllocationAmmount(int allocationAmmount) {
		AllocationAmmount = allocationAmmount;
	}

	public Banks getBanks() {
		return banks;
	}

	public void setBanks(Banks banks) {
		this.banks = banks;
	}
	
	
}
