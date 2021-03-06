
package com.example.OpenLoans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.BankBranches.Branches;
import com.example.SiteConfig.SiteConfiguration;


@Service
public class OpenLoansService {

	@Autowired 
	private OpenLoansRepository openLoanRepository;
	
	public List<OpenLoans> GetAllOpenLoan(int PageNumber)
	{
		Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize(), Sort.by("id").descending());
		Page<OpenLoans> pagedResult = this.openLoanRepository.findAll(paging);
		if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<OpenLoans>();
        }
	}
	
	
	public List <OpenLoans>GetNotConfirmedLoans(int PageNumber)
	{ 	
		List<OpenLoans> allLoans=openLoanRepository.findAll();
		List<OpenLoans> NotConfimedLoans = new ArrayList<OpenLoans>();	
		for(OpenLoans l : allLoans)
		{
			if(l.getLoan().getStatus().equalsIgnoreCase("غير مؤكدة")) {
				NotConfimedLoans.add(l);
			}
		}
		
		int ignore = calculateIgnoreValue(PageNumber);
		
		List<OpenLoans> pagedList = new ArrayList<OpenLoans>();
		for(int i = ignore ; i < ignore+SiteConfiguration.getPageSize() ; i ++ ) {
			try {
				pagedList.add(NotConfimedLoans.get(i));
			}catch(Exception e ) {
				break ; 
			}
		}
		return pagedList ; 
		//return NotConfimedLoans;
	}
	
	
	public List <OpenLoans>GetConfirmedLoans(int PageNumber )
	{
		List<OpenLoans> allLoans=openLoanRepository.findAll();
		List<OpenLoans> ConfimedLoans = new ArrayList<OpenLoans>();
		for(OpenLoans l : allLoans)
		{
			if(l.getLoan().getStatus().equalsIgnoreCase("مؤكدة"))
				ConfimedLoans.add(l);
		}

		int ignore = calculateIgnoreValue(PageNumber);
		List<OpenLoans> pagedList = new ArrayList<OpenLoans>();
		for(int i = ignore ; i < ignore+SiteConfiguration.getPageSize() ; i ++ ) {
			try {
				pagedList.add(ConfimedLoans.get(i));
			}catch(Exception e ) {
				break ; 
			}
		}
		
		return pagedList ; 
		//return ConfimedLoans;
	}
	
	
	private int calculateIgnoreValue(int value ) {
		if(value == 0 ) {
			return 0 ; 
		}
		else {
			return value * SiteConfiguration.getPageSize() ; 
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
	
	
	public Page<OpenLoans> getAllLoansSequence(int pageNum,Page<OpenLoans> paramModelPage) {
		if(paramModelPage == null) {
		Pageable paging = PageRequest.of(pageNum, SiteConfiguration.getAnalatycsPageSize(), Sort.by("id"));
		Page<OpenLoans> modelPage = this.openLoanRepository.findAll(paging);
		return modelPage ;
		
		}else if(paramModelPage.hasNext()) {
			Pageable paging = PageRequest.of(pageNum, SiteConfiguration.getAnalatycsPageSize(), Sort.by("id"));
			Page<OpenLoans> modelPage = this.openLoanRepository.findAll(paging);
			return modelPage ;
		}else {
			return null ; 
		}
	}
	
	
	public int getOpenLoansCount() {
		return this.openLoanRepository.getOpenLoansCount() ; 
	}
	
	
	
	public long getTotalLoansValue() {
		int pageNum = 0 ; 
		long totalVal = 0 ; 
		Page<OpenLoans> loansPage = getAllLoansSequence(pageNum, null);
		while(loansPage != null ) {
			for(OpenLoans loan : loansPage.getContent()) {
			totalVal += Long.valueOf(loan.getLoan().getTotalAmmount());
			}
			pageNum++;
			loansPage = getAllLoansSequence(pageNum, loansPage);
		}
		return totalVal ; 
	}
}
