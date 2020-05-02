
package com.example.OpenLoans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.SiteConfiguration;
import com.example.BankBranches.Branches;

@Service
public class OpenLoansService {

	@Autowired 
	private OpenLoansRepository openLoanRepository;
	
	public List<OpenLoans> GetAllOpenLoan(int PageNumber)
	{
		Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize(), Sort.by("id"));
		Page<OpenLoans> pagedResult = this.openLoanRepository.findAll(paging);
		if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<OpenLoans>();
        }
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
			if(o.getLoan().getId()==id)
				return o;
		}
		return null;
	}
	
	
	public List<OpenLoans> getBrancheOpenLoans(Branches branche){
		List<OpenLoans> brancheLoans = new ArrayList<OpenLoans>();
		for(OpenLoans loan : this.openLoanRepository.findAll()) {
			if(loan.getLoan().getBranche().getId() == branche.getId()) {
				brancheLoans.add(loan); 
			}
		}
		return brancheLoans; 
	}
	
}
