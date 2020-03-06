package com.example.Banks;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class BankService {

	@Autowired
	private BanksRepository bankRepository;
	
	
	public List<Banks> GetAllBanks() {
		List<Banks> banks=bankRepository.findAll();
		return banks;
	}
	
	public Optional <Banks> GetBank(int id)
	{
		return bankRepository.findById(id);
	}
	
	
	public void addBank(Banks bank)
	{
		Banks b=new Banks(bank.getBankName(),bank.getBranchName(),bank.getBankCode(),bank.getFinancialAllocations());
		bankRepository.save(b);
	}


	public void deleteBank(int id) {
		bankRepository.deleteById(id);
	}


	public void updateBank(Banks bank) {
		bankRepository.save(bank);
	}


	
}
