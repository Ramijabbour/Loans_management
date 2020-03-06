package com.example.OpenLoans;
import com.example.Loans.Loans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;



@Entity
public class OpenLoans {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int OpenLoanID ;
	
	@ManyToOne
	private Loans loan=null;

	public int getOpenLoanID() {
		return OpenLoanID;
	}

	public void setOpenLoanID(int openLoanID) {
		OpenLoanID = openLoanID;
	}

	public Loans getLoan() {
		return loan;
	}

	public void setLoan(Loans loan) {
		this.loan = loan;
	}
	
	
	
}
