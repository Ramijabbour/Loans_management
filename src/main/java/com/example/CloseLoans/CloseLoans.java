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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	
	private String Status;
	
	@ManyToOne
	private Loans loan=null;
	
	

	public CloseLoans() {}

	public CloseLoans(Loans loan) {
		this.loan = loan;
	}

	public int getId() {
		return id;
	}

	public void setId(int closeLoanID) {
		id = closeLoanID;
	}

	public Loans getLoan() {
		return loan;
	}

	public void setLoan(Loans loan) {
		this.loan = loan;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
	
	
	
}
