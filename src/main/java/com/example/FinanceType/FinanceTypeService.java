package com.example.FinanceType;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinanceTypeService {

	@Autowired 
	FinanceTypeRepository financeTypeRepository ;
	
	public List<FinanceType> getAllFinanceType()
	{
		return financeTypeRepository.findAll();
	}
}
