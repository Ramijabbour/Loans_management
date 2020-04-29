package com.example.ReScheduleLoans;

import com.example.Loans.Loans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;



@Entity
public class ReScheduleLoans {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;
	
	@ManyToOne
	private Loans loan=null;

	
	
	public ReScheduleLoans() {
	}

	public ReScheduleLoans(Loans loan) {
		this.loan = loan;
	}

	public int getId() {
		return id;
	}

	public void setId(int reScheduleLoanID) {
		id = reScheduleLoanID;
	}

	public Loans getLoan() {
		return loan;
	}

	public void setLoan(Loans loan) {
		this.loan = loan;
	}

	
	
	
	
}
