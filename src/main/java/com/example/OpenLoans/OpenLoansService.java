
package com.example.OpenLoans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenLoansService {

	@Autowired 
	private OpenLoansRepository openLoanRepository;
	
	
	public void addOpenLoan(OpenLoans openLoan)
	{
		this.openLoanRepository.save(openLoan);
	}
	
}
