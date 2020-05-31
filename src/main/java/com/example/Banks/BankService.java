package com.example.Banks;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Allocations.AllocationsService;
import com.example.OpenLoans.OpenLoans;
import com.example.SiteConfig.SiteConfiguration;

@Service
public class BankService {

	@Autowired
	private BanksRepository bankRepository;
	
	@Autowired
	private AllocationsService allocationService;
	
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
	
	public Banks getBankByName(String bankName ) {
		for(Banks bank : this.bankRepository.findAll()) {
			if(bank.getBankName().equalsIgnoreCase(bankName)) {
				return bank ; 
			}
		}
		return null ; 
	}
	
	
	
	public List<Banks> GetBankForAllocation() throws ParseException
	{
		List<Banks> allBanks=bankRepository.findAll();
		List<Banks> availableBanks = new ArrayList<Banks>();
		
		for(Banks bank : allBanks) {
			if(allocationService.CheckDateMoreThanYear(bank))
			{	System.out.println("step 1 ");
				availableBanks.add(bank);
			}	}
		return availableBanks;		
		
	}

	//------search
	public List<Banks> SearchbyBankName(int PageNumber,String bankName){
		Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize(), Sort.by("id"));
		Slice<Banks> pagedResult = this.bankRepository.findByBankName(bankName,paging);
		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Banks>();
		}
	}

	public int getBanksCount() {
		return this.bankRepository.getBanksCount();
	}
	
}
