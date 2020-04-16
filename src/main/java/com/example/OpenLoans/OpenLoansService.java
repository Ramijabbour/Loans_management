
package com.example.OpenLoans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Banks.Banks;

@Service
public class OpenLoansService {

	@Autowired 
	private OpenLoansRepository openLoanRepository;
	
	public List<OpenLoans> GetAllOpenLoan()
	{
		return openLoanRepository.findAll();
	}
	
	
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
	
	
	public List<OpenLoans> getBankOpenLoans(Banks bank){
		List<OpenLoans> bankLoans = new ArrayList<OpenLoans>();
		for(OpenLoans loan : this.openLoanRepository.findAll()) {
			if(loan.getLoan().getBank().getBankID() == bank.getBankID()) {
				bankLoans.add(loan); 
			}
		}
		return bankLoans; 
	}
	
}
