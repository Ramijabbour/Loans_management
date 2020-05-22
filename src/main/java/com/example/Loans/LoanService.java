package com.example.Loans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.example.SiteConfiguration;
import com.example.BankBranches.Branches;
import com.example.SiteConfig.SiteConfiguration;




@Service
public class LoanService {

	@Autowired
	private LoansRepository loansRepository;
	
	
	public List<Loans> getAllLoans(int PageNumber)
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
			if(loan.getId()==id)
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
			if(loansRepository.findById(loan.getId()) != null) {
					loansRepository.save(loan); 
				}
		}catch(Exception e ) {
			System.out.println("NullPointerException Handled at loan Service / Update loan -- call for null loan ");
		}
	}
	
	
	public List<Loans> getBrancheLoans(Branches branche){
		List<Loans> brancheLoans = new ArrayList<Loans>();
		for(Loans loan : this.loansRepository.findAll()) {
			if(loan.getBranche().getId() == branche.getId()) {
				brancheLoans.add(loan); 
			}
		}
		return brancheLoans; 
	}
	
	public Page<Loans> getAllLoansSequence(int pageNum,Page<Loans> paramModelPage) {
		if(paramModelPage == null) {
		Pageable paging = PageRequest.of(pageNum, SiteConfiguration.getAnalatycsPageSize(), Sort.by("id"));
		Page<Loans> modelPage = this.loansRepository.findAll(paging);
		return modelPage ;
		}else if(paramModelPage.hasNext()) {
			Pageable paging = PageRequest.of(pageNum, SiteConfiguration.getAnalatycsPageSize(), Sort.by("id"));
			Page<Loans> modelPage = this.loansRepository.findAll(paging);
			return modelPage ;
		}else {
			return null ; 
		}
	}

	//------search
	public List<Loans> SearchByLoanNumber(int PageNumber,String loanNumber){
		Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize(), Sort.by("id"));
		Slice<Loans> pagedResult = this.loansRepository.findByLoanNumber(loanNumber,paging);
		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Loans>();
		}
	}
	
	
	public int getLoansCount() {
		return this.loansRepository.getLoansCount();
	}
	
	public long getTotalLoansValue() {
		int pageNum = 0 ; 
		long totalVal = 0 ; 
		Page<Loans> loansPage = getAllLoansSequence(pageNum, null);
		while(loansPage != null ) {
			for(Loans loan : loansPage.getContent()) {
			totalVal += Long.valueOf(loan.getTotalAmmount());
			}
			pageNum++;
			loansPage = getAllLoansSequence(pageNum, loansPage);
		}
		return totalVal ; 
	}
}
