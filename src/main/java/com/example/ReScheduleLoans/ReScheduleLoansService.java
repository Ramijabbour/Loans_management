package com.example.ReScheduleLoans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.SiteConfiguration;

@Service
public class ReScheduleLoansService {

	
	@Autowired 
	ReScheduleLoansRepository reScheduleLoansReopsitory;
	
	
	public void addLoan(ReScheduleLoans ReScheduleLoan)
	{
		reScheduleLoansReopsitory.save(ReScheduleLoan);
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
	
	
}
