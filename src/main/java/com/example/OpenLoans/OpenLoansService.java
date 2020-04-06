
package com.example.OpenLoans;

import java.util.List;

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
	
	public void DeleteOpenLoan(OpenLoans openloan)
	{
		openLoanRepository.delete(openloan);
	}
	
	public OpenLoans getOpenLoanFromLoan(int id)
	{
		List<OpenLoans> allOpenLoans=openLoanRepository.findAll();
		
		for(OpenLoans o : allOpenLoans)
		{
			if(o.getLoan().getLoanID()==id)
				return o;
		}
		return null;
	}
	
	
}
