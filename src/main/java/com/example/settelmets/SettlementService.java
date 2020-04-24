package com.example.settelmets;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.MasterService;
import com.example.Banks.BankService;
import com.example.Banks.Banks;
import com.example.MQ.Chaque;
import com.example.MQ.OnHoldCheckRepository;
import com.example.MQ.SettledChaque;
import com.example.MQ.SettledChecksRepository;

@Service
public class SettlementService extends MasterService {

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
	
	//change schedule invoke time and isolate it in another thread 
	//@Scheduled(fixedRate = 7000000)
	public void settleChecks() {
		System.out.println("ettlement invoked at : "+MasterService.getCurrDateTime());
		initSettlementOperation(); 
		SettelmentHandler.setNumberOfParticipants(this.ParticipantsCount);
		if(ParticipantsIds.size() != 0 ) {
			List<SettledChaque> resultList = SettelmentHandler.minCashFlow(toSettleChecks,ParticipantsIds);
			
			this.settledChecksRepository.saveAll(resultList);
			
		}
		super.notificationsService.addNotification("Gross Settlement reports ready", "/settlement/checks/reports", "SUPER");
		// add result validation 
	}
	
	public int addCheck(Chaque check ) {
		//int result = testCheckInfoValidation(check) ;
		int result =0 ;
		if(result == 0 ) {
			this.onHoldChecksRepository.save(check);
			super.notificationsService.addNotification("check added to settlement Service", "/settlement/checks/all", "SUPER");
			return 0 ; 
		}else{
			return result ; 
		}
	}	

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
	/*public int testCheckInfoValidation(Chaque check ) {
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
	*/
	public boolean resultDataCheck(List<Chaque> results,List<Chaque> input) {
		List<Integer> banks = new ArrayList<Integer>();

		banks = this.findNumberOfParticipants(input); 
		
		double[] debts = new double[banks.size()] ; 
		double[] pays = new double[banks.size()] ; 
		
		for(Chaque check : input) {
			debts[banks.indexOf(check.getFirstBankSW())] += check.getAmount() ; 
			pays[banks.indexOf(check.getSecondBankSW())] += check.getAmount();
		}
		
		return false ; 
	}

	public List<Chaque> getOnHoldChecks(){	
		return this.onHoldChecksRepository.findByActiveFalse() ; 
	}
	
	public List<SettledChaque> getSettledChecks(){
		return this.settledChecksRepository.findAll(); 
	}
	
	public List<Chaque> getAllChecks(){
		return this.onHoldChecksRepository.findAll() ; 
	}

	public SettledChaque findCheckByID(int id ) {
		List<SettledChaque> all = this.settledChecksRepository.findAll() ; 
		for(SettledChaque settledCheck : all) {
			if(settledCheck.getId() == id ) {
				return settledCheck; 
			}
		}
		return null ; 
	}
	
}
