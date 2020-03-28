package com.example.settelmets;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	
	public void addCheck(Chaque check ) {
		//check data validation 
		this.onHoldChecksRepository.save(check);		
	}
}
