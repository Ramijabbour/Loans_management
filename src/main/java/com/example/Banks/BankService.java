package com.example.Banks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.models.Banks;

import dataBase.BanksRepository;

@Service
public class BankService {

	@Autowired
	private BanksRepository bankRepository;
	
	
	public List<Banks> GetAllBanks() {
		
		List<Banks> banks=bankRepository.findAll();
		return banks;
	}
	
	
	public void addBank(Banks bank)
	{
		Banks b=new Banks(bank.getBranchName(),bank.getBankCode(),bank.getFinancialAllocations());
		bankRepository.save(b);
	}


	
}