package com.example.Banks;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.security.user.User;


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
		bank.setFinancialAllocations("0");
		bankRepository.save(bank);
	}


	public void deleteBank(int id) {
		bankRepository.deleteById(id);
	}

	public Banks getBankById(int id)
	{
		List<Banks> allBanks = this.bankRepository.findAll() ; 
		if(allBanks.isEmpty()) {
			System.out.println("empty BanksList ");
			return null ;  
		}
		for(Banks bank : allBanks) {
			if(bank.getBankID() == id ){
				return bank  ; 
			}
		}
		System.out.println("requested bank not found ");
		return null ; 
	 
	}

	public void updateBank(Banks bank) {
		try {
			if(bankRepository.findById(bank.getBankID()) != null) {
					bankRepository.save(bank); 
				}
		}catch(Exception e ) {
			System.out.println("NullPointerException Handled at bank Service / Update Bank -- call for null Bank ");
		}
	}


	public Banks getBankByID(int bankId) {
		List<Banks> banksList = this.bankRepository.findAll() ; 
		for(Banks bank : banksList) {
			if(bank.getBankID() == bankId) {
				return bank ; 
			}
		}
		return null ; 
	}
	
}
