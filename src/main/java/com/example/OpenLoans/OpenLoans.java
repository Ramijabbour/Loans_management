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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	
	@ManyToOne
	private Loans loan=null;

	
	
	public OpenLoans() {
	}

	public OpenLoans(Loans loan) {
		this.loan = loan;
	}

	public int getId() {
		return id;
	}

	public void setId(int openLoanID) {
		id = openLoanID;
	}

	public Loans getLoan() {
		return loan;
	}

	public void setLoan(Loans loan) {
		this.loan = loan;
	}
	
	
	
}
