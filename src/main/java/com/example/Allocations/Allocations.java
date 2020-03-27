package com.example.Allocations;



import ValidContent_Visitor.Visitor;
import com.example.Banks.Banks;

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
	
	public String AllocationDate ;
	
	public String AllocationAmmount;
	
	@ManyToOne
	private Banks banks =null ;

	public Allocations() {}
	
	public Allocations(String allocationDate , String ammount , Banks bank) {
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

	public String getAllocationAmmount() {
		return AllocationAmmount;
	}

	public void setAllocationAmmount(String allocationAmmount) {
		AllocationAmmount = allocationAmmount;
	}

	public Banks getBanks() {
		return banks;
	}

	public void setBanks(Banks banks) {
		this.banks = banks;
	}
	public boolean accept(Visitor visitor) { return visitor.visit(this); }}

