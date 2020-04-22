package com.example.CloseLoans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BankBranches.Branches;
import com.example.Banks.Banks;

@Service
public class CloseLoanService {

	@Autowired
	CloseLoansRepository closeLoanRepository ; 
	
	public void addLoan(CloseLoans closeLoan)
	{
		closeLoanRepository.save(closeLoan);
	}
	
	public List<CloseLoans> GetAllCloseLoan()
	{
		return closeLoanRepository.findAll();
	}
	
	public List<CloseLoans> getBrancheClosedLoans(Branches branche){
		List<CloseLoans> brancheLoans = new ArrayList<CloseLoans>();
		for(CloseLoans loan : this.closeLoanRepository.findAll()) {
			if(loan.getLoan().getBranche().getBrancheID() == branche.getBrancheID()) {
				brancheLoans.add(loan); 
			}
		}
		return brancheLoans; 
	}
	
}
