package com.example.LoansType;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoansTypeService {

	@Autowired 
	LoansTypeRepository loansTypeRespository;
	
	public List<LoansType> getAllType()
	{
		return loansTypeRespository.findAll();
	}
	
}
