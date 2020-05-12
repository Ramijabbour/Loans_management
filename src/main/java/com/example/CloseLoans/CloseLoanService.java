package com.example.CloseLoans;

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
import com.example.Loans.Loans;

@Service
public class CloseLoanService {

	@Autowired
	CloseLoansRepository closeLoanRepository ; 
	
	public void addLoan(CloseLoans closeLoan)
	{
		closeLoanRepository.save(closeLoan);
	}
	
	public List<CloseLoans> GetAllCloseLoan(int PageNumber)
	{
		Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize(), Sort.by("id"));	
		Page<CloseLoans> pagedResult = this.closeLoanRepository.findAll(paging);
		if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<CloseLoans>();
        }
	}
	
	public List<CloseLoans> getBrancheClosedLoans(Branches branche){
		List<CloseLoans> brancheLoans = new ArrayList<CloseLoans>();
		for(CloseLoans loan : this.closeLoanRepository.findAll()) {
			if(loan.getLoan().getBranche().getId() == branche.getId()) {
				brancheLoans.add(loan); 
			}
		}
		return brancheLoans; 
	}
	
	
	public Page<CloseLoans> getAllLoansSequence(int pageNum,Page<CloseLoans> paramModelPage) {
		if(paramModelPage == null) {
		Pageable paging = PageRequest.of(pageNum, SiteConfiguration.getAnalatycsPageSize(), Sort.by("id"));
		Page<CloseLoans> modelPage = this.closeLoanRepository.findAll(paging);
		return modelPage ;
		
		}else if(paramModelPage.hasNext()) {
			Pageable paging = PageRequest.of(pageNum, SiteConfiguration.getAnalatycsPageSize(), Sort.by("id"));
			Page<CloseLoans> modelPage = this.closeLoanRepository.findAll(paging);
			return modelPage ;
		}else {
			return null ; 
		}
	}
	
}
