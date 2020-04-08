package com.example.CloseLoans;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
}
