package com.example.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CloseLoans {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int CloseLoanID ;
	
	@ManyToOne
	private Loans loan=null;

	public int getCloseLoanID() {
		return CloseLoanID;
	}

	public void setCloseLoanID(int closeLoanID) {
		CloseLoanID = closeLoanID;
	}

	public Loans getLoan() {
		return loan;
	}

	public void setLoan(Loans loan) {
		this.loan = loan;
	}
	
	
	
}
