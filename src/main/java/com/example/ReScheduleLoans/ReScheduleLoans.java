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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ReScheduleLoanID ;
	
	@ManyToOne
	private Loans loan=null;

	public int getReScheduleLoanID() {
		return ReScheduleLoanID;
	}

	public void setReScheduleLoanID(int reScheduleLoanID) {
		ReScheduleLoanID = reScheduleLoanID;
	}

	public Loans getLoan() {
		return loan;
	}

	public void setLoan(Loans loan) {
		this.loan = loan;
	}

	
	
	
	
}
