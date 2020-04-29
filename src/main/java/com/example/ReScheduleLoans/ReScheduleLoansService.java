package com.example.ReScheduleLoans;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReScheduleLoansService {

	
	@Autowired 
	ReScheduleLoansRepository reScheduleLoansReopsitory;
	
	
	public void addLoan(ReScheduleLoans ReScheduleLoan)
	{
		reScheduleLoansReopsitory.save(ReScheduleLoan);
	}
	
	public List<ReScheduleLoans> getAllReScheduleLoans()
	{
		return reScheduleLoansReopsitory.findAll();
	}
	
	
}
