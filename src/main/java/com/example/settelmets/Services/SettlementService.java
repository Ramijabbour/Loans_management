package com.example.settelmets.Services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.MasterService;
import com.example.BankBranches.BrancheService;
import com.example.BankBranches.Branches;
import com.example.Banks.BankService;
import com.example.Banks.Banks;
import com.example.settelmets.Models.Chaque;
import com.example.settelmets.Models.CheckDisposableModel;
import com.example.settelmets.Models.SettledChaque;
import com.example.settelmets.Repositories.OnHoldCheckRepository;
import com.example.settelmets.Repositories.SettledChecksRepository;

@Service
public class SettlementService extends MasterService {

	@Autowired 
	private OnHoldCheckRepository onHoldChecksRepository ;  	
	@Autowired
	private SettledChecksRepository settledChecksRepository;
	
	
	private int[][] toSettleChecks ; 
	private int ParticipantsCount ; 
	private List<String> ParticipantsIds ; 
	

	private void initSettlementOperation() {
		//essential variables 
		ParticipantsIds = new ArrayList<String>() ; 
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
			String currId = check.getFirstBranchCode() ; 
			int indexFrom = ParticipantsIds.indexOf(currId); 
			int indexTo = ParticipantsIds.indexOf(check.getSecondBranchCode());
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
	
	private List<String> findNumberOfParticipants(List<Chaque> checks ){
		List<String> banks = new ArrayList<String>();
		for(Chaque check : checks ){
			if(!banks.contains(check.getFirstBranchCode())) {
				banks.add(check.getFirstBranchCode()); 
			}
			if(!banks.contains(check.getSecondBranchCode())) {
				banks.add(check.getSecondBranchCode()); 
			}
		}
		return banks; 
	}
	
	//change schedule invoke time and isolate it in another thread 
	//@Scheduled(fixedRate = 7000000)
	@Transactional
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
	
	public int addCheck(CheckDisposableModel check ) {
		//int result = testCheckInfoValidation(check) ;
		int result =0 ;
		if(result == 0 ) {
			Chaque finalCheck = new Chaque(check.getCheckId(),check.getFirstBankName(), check.getSecondBankName(),check.getFirstBranchName(),
					check.getFirstBranchCode(),check.getSecondBranchName(),check.getSecondBranchCode(),check.getAmount(),super.get_current_User().getUsername(),
					super.get_current_User().getUserID(),false);
			this.onHoldChecksRepository.save(finalCheck);
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
	 *  first branch not found   | -312			|Name Error 
	 *  second bank not found    | -321  		|ID Error
	 *  second branch not found  | -322			|Name Error
	 *  check id duplication     | -4  			|
	 * */

	/*public int testCheckInfoValidation(CheckDisposableModel check ) {
		//check ID data duplication 
		List<Chaque> allChecks = this.onHoldChecksRepository.findAll() ; 
		if(check.getAmount() <= 0 ) {
			return -1 ;
		}
		if(check.getFirstBankName().equalsIgnoreCase(check.getSecondBankName())) {
			return -2 ; 
		}
		if(check.getFirstBranchName().equalsIgnoreCase(check.getSecondBranchName())) {
			return -2 ; 
		}
		if(check.getFirstBranchCode() == check.getSecondBranchCode()) {
			return -2 ;
		}
		//check the first bank data 
		Banks bank = this.banksService.getBankByName(check.getFirstBankName());
		if(bank == null ){
			return -311 ; 
		}
		//check branch 
		List<Branches> bankBranches = this.branchesService.getBankBranches(bank);
		boolean found = false; 
		for(Branches branch : bankBranches ) {
			if(branch.getBrancheCode().equalsIgnoreCase(String.valueOf(check.getFirstBranchCode()))) {
				found = true ; 
				break ; 
			}
		}if(!found) {
			return -312 ; 
		}
		
		//check the second bank data 
		Banks bank2 = this.banksService.getBankByName(check.getSecondBankName()); 
		if(bank2 == null ) {
			return -321 ; 
		}
		//check branch 
		
		
		for(Chaque tempCheck : allChecks ) {
			if(check.getCheckId() == tempCheck.getCheckId() ) {
				return -4;  
			}
		}	
		return 0 ; 
	}*/
	
	/*
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
*/
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
