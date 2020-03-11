package com.example.CloseLoans;

import com.example.Loans.Loans;

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

	public CloseLoans(int closeLoanID, Loans loan) {
		CloseLoanID = closeLoanID;
		this.loan = loan;
	}

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
