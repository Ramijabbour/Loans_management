package com.example.ReScheduleLoans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.SiteConfig.SiteConfiguration;


@Service
public class ReScheduleLoansService {

	
	@Autowired 
	ReScheduleLoansRepository reScheduleLoansReopsitory;
	
	
	public void addLoan(ReScheduleLoans ReScheduleLoan)
	{
		reScheduleLoansReopsitory.save(ReScheduleLoan);
	}
	
	public void DeleteLoan(ReScheduleLoans ReScheduleLoan)
	{
		reScheduleLoansReopsitory.delete(ReScheduleLoan);
	}
	
	public List<ReScheduleLoans> getAllReScheduleLoans(int PageNumber)
	{
		Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize(), Sort.by("id"));
		Page<ReScheduleLoans> pagedResult = this.reScheduleLoansReopsitory.findAll(paging);
		if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<ReScheduleLoans>();
        }
	}
	
	
	public ReScheduleLoans getReScheduleLoanFromLoan(int id)
	{
		List<ReScheduleLoans> allReScheduleLoans=reScheduleLoansReopsitory.findAll();
		
		for(ReScheduleLoans r : allReScheduleLoans)
		{
			if(r.getLoan().getId()==id)
				return r;
		}
		return null;
	}
	
	public int getResLoansCount() {
		return this.reScheduleLoansReopsitory.getResLoansCount() ; 
	}

	
	public Page<ReScheduleLoans> getAllLoansSequence(int pageNum,Page<ReScheduleLoans> paramModelPage) {
		if(paramModelPage == null) {
		Pageable paging = PageRequest.of(pageNum, SiteConfiguration.getAnalatycsPageSize(), Sort.by("id"));
		Page<ReScheduleLoans> modelPage = this.reScheduleLoansReopsitory.findAll(paging);
		return modelPage ;
		
		}else if(paramModelPage.hasNext()) {
			Pageable paging = PageRequest.of(pageNum, SiteConfiguration.getAnalatycsPageSize(), Sort.by("id"));
			Page<ReScheduleLoans> modelPage = this.reScheduleLoansReopsitory.findAll(paging);
			return modelPage ;
		}else {
			return null ; 
		}
	}
	
	
	
	
	public long getTotalLoansValue() {
		int pageNum = 0 ; 
		long totalVal = 0 ; 
		Page<ReScheduleLoans> loansPage = getAllLoansSequence(pageNum, null);
		while(loansPage != null ) {
			for(ReScheduleLoans loan : loansPage.getContent()) {
			totalVal += Long.valueOf(loan.getLoan().getTotalAmmount());
			}
			pageNum++;
			loansPage = getAllLoansSequence(pageNum, loansPage);
		}
		return totalVal ; 
	}

	
}
