package com.example.settelmets;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Banks.BankService;
import com.example.Banks.Banks;
import com.example.MQ.Chaque;
import com.example.MQ.OnHoldCheckRepository;
import com.example.MQ.SettledChaque;
import com.example.MQ.SettledChecksRepository;

@Service
public class SettlementService {

	@Autowired 
	private OnHoldCheckRepository onHoldChecksRepository ;  	
	@Autowired
	private SettledChecksRepository settledChecksRepository;
	@Autowired
	private BankService banksService  ; 
	
	private int[][] toSettleChecks ; 
	private int ParticipantsCount ; 
	private List<Integer> ParticipantsIds ; 
	

	private void initSettlementOperation() {
		//essential variables 
		ParticipantsIds = new ArrayList<Integer>() ; 
		List<Chaque> onHoldChecks = this.onHoldChecksRepository.findByActiveFalse() ;
		ParticipantsIds = this.findNumberOfParticipants(onHoldChecks);
		ParticipantsCount = ParticipantsIds.size() ; 
		
		//initialize toSettleChecks array with zero values 
		toSettleChecks = new int[ParticipantsCount][ParticipantsCount]; 
		for(int i = 0 ; i < ParticipantsCount ; i++) {
		for(int j = 0 ; j < ParticipantsCount ; j++) {
				toSettleChecks[i][j] = 0 ; 
			}
		}	
		//Checks to settle to Array 
		for(Chaque check : onHoldChecks) {
			int currId = check.getFirstBankSW() ; 
			int indexFrom = ParticipantsIds.indexOf(currId); 
			int indexTo = ParticipantsIds.indexOf(check.getSecondBankSW());
			this.toSettleChecks[indexFrom][indexTo] += check.getAmount() ; 
		}	
		
		for(int i = 0 ; i < ParticipantsCount ; i++) {
			for(int j = 0 ; j < ParticipantsCount ; j++) {
					System.out.print(toSettleChecks[i][j]+" ");
				}
			System.out.println();
			}	
		
		for(Chaque check : onHoldChecks) {
			check.setActive(true);
		}
		this.onHoldChecksRepository.saveAll(onHoldChecks); 
	}
	
	private List<Integer> findNumberOfParticipants(List<Chaque> checks ){
		List<Integer> banks = new ArrayList<Integer>();
		for(Chaque check : checks ){
			if(!banks.contains(check.getFirstBankSW())) {
				banks.add(check.getFirstBankSW()); 
			}
			if(!banks.contains(check.getSecondBankSW())) {
				banks.add(check.getSecondBankSW()); 
			}
		}
		return banks; 
	}
	
	public void settleChecks() {
		initSettlementOperation(); 
		SettelmentHandler.setNumberOfParticipants(this.ParticipantsCount);
		if(ParticipantsIds.size() != 0 ) {
			List<SettledChaque> resultList = SettelmentHandler.minCashFlow(toSettleChecks,ParticipantsIds);
			this.settledChecksRepository.saveAll(resultList);
		}
		// add result validation 
		// change check state 
		// export results to another check model objects and repo
	}
	
	
	public int addCheck(Chaque check ) {
		int result = testCheckInfoValidation(check) ; 
		if(result == 0 ) {
			this.onHoldChecksRepository.save(check);
			return 0 ; 
		}else{
			return result ; 
		}
	}
	
	
	//error Codes : 
	/*
	 * 		ERROR 				 | Error code  |
	 * ----------------------------------------- 
	 * amount less than zero     | -1 			|
	 * sender is the receiver    | -2  			|
	 *  first bank not found 	 | -311 		|ID Error 
	 *  first bank name is wrong | -312			|Name Error 
	 *  second bank not found    | -321  		|ID Error
	 *  second bank name is wrong| -322			|Name Error
	 *  check id duplication     | -4  			|
	 * */
	public int testCheckInfoValidation(Chaque check ) {
		//check ID data duplication 
		List<Chaque> allChecks = this.onHoldChecksRepository.findAll() ; 
		if(check.getAmount() <= 0 ) {
			return -1 ;
		}
		if(check.getFirstBank().equalsIgnoreCase(check.getSecondBank())) {
			return -2 ; 
		}
		if(check.getFirstBankSW() == check.getSecondBankSW()) {
			return -2 ;
		}
		//check the first bank data 
		Banks bank = this.banksService.getBankByID(check.getFirstBankSW());
		if(bank == null ){
			return -311 ; 
		}
		if(!bank.getBranchName().equalsIgnoreCase(check.getFirstBank())) {
			return -312 ;
		}
		
		//check the second bank data 
		Banks bank2 = this.banksService.getBankByID(check.getSecondBankSW()) ; 
		if(bank2 == null ) {
			return -321 ; 
		}
		if(!bank2.getBranchName().equalsIgnoreCase(check.getSecondBank())) {
			return -322 ; 
		}
		for(Chaque tempCheck : allChecks ) {
			if(check.getCheckId() == tempCheck.getCheckId() ) {
				return -4;  
			}
		}	
		return 0 ; 
	} 
	
	public boolean resultDataCheck(List<Chaque> results) {
		List<Integer> banks = new ArrayList<Integer>();
		List<Double> debts = new ArrayList<Double>();
		List<Double> pays = new ArrayList<Double>();

		banks = this.findNumberOfParticipants(results); 
		
		for(Chaque check : results ) {
			debts.add(banks.indexOf(check.getFirstBankSW()), check.getAmount());
			pays.add(banks.indexOf(check.getSecondBankSW()),check.getAmount());
		}
		
		return false ; 
	}
	
}
