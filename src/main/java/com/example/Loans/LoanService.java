package com.example.Loans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Banks.Banks;

@Service
public class LoanService {

	@Autowired
	private LoansRepository loansRepository;
	
	
	public List<Loans> getAllLoans()
	{
		return loansRepository.findAll();
	}
	
	
	public Loans getOneByID(int id )
	{
		List<Loans> allLoans = loansRepository.findAll();
		if(allLoans.isEmpty())
		{
			System.out.println("Loans List is Empty");
			return null;
		}
		for(Loans loan : allLoans)
		{
			if(loan.getLoanID()==id)
				return loan ;
		}
		System.out.println("loan is not exist");
		return null;
	}
	
	
	public void addLoan(Loans loan)
	{
		loansRepository.save(loan);
	}
	
	public void DeleteLoan(int id)
	{
		loansRepository.deleteById(id);
	}
	public void updateLoan(Loans loan) 
	{
		try {
			if(loansRepository.findById(loan.getLoanID()) != null) {
					loansRepository.save(loan); 
				}
		}catch(Exception e ) {
			System.out.println("NullPointerException Handled at loan Service / Update loan -- call for null loan ");
		}
	}
	
	
	public List<Loans> getBankLoans(Banks bank){
		List<Loans> bankLoans = new ArrayList<Loans>();
		for(Loans loan : this.loansRepository.findAll()) {
			if(loan.getBank().getBankID() == bank.getBankID()) {
				bankLoans.add(loan); 
			}
		}
		return bankLoans; 
	}
	
}
