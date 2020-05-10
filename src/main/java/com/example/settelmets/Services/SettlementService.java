package com.example.settelmets.Services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.MasterService;
import com.example.SiteConfiguration;
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
	
	@Autowired 
	private BankService banksService ; 
	@Autowired
	private BrancheService branchesService ; 
	
	private long[][] toSettleChecks ; 
	private int ParticipantsCount ; 
	private List<String> ParticipantsIds ; 
	private List<String> BanksNames ; 
	private List<String> BranchesNames;

	private List<Chaque> initSettlementOperation() {
		//essential variables 
		ParticipantsIds = new ArrayList<String>() ;
		BanksNames = new ArrayList<String>(); 
		BranchesNames = new ArrayList<String>();
		List<Chaque> onHoldChecks = this.onHoldChecksRepository.findByActiveFalse() ;
		ParticipantsIds = this.getParticipantsInfo(onHoldChecks,this.BanksNames,this.BranchesNames);
		ParticipantsCount = ParticipantsIds.size() ; 
		
		//initialize toSettleChecks array with zero values 
		toSettleChecks = new long[ParticipantsCount][ParticipantsCount]; 
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
		return onHoldChecks ; 
	}
	
	private List<String> getParticipantsInfo(List<Chaque> checks,List<String> banksNames ,List<String> branchesNames){
		List<String> banks = new ArrayList<String>();
		for(Chaque check : checks ){
			if(!banks.contains(check.getFirstBranchCode())) {
				banks.add(check.getFirstBranchCode());
				banksNames.add(check.getFirstBankName());
				branchesNames.add(check.getFirstBranchName());
			}
			if(!banks.contains(check.getSecondBranchCode())) {
				banks.add(check.getSecondBranchCode());
				banksNames.add(check.getSecondBankName());
				branchesNames.add(check.getSecondBranchName());
				
			}
		}
		return banks; 
	}
	
	//change schedule invoke time and isolate it in another thread 
	//@Scheduled(fixedRate = 7000000)
	@Transactional
	public void settleChecks() {
		System.out.println("settlement invoked at : "+MasterService.getCurrDateTime());
		List<Chaque> onHoldChecks = initSettlementOperation(); 
		SettelmentHandler.setNumberOfParticipants(this.ParticipantsCount);
		if(ParticipantsIds.size() != 0 ) {
			List<SettledChaque> resultList = SettelmentHandler.invokeSettlementSequence(toSettleChecks,ParticipantsIds,BanksNames,BranchesNames);
			if(resultList == null ) {
				super.notificationsService.addNotification("Settlement Operation Failed duo to data check failuer reviewing the checks is needed! ", "/settlement/checks/reports", "SUPER");
			}else {
			
			this.settledChecksRepository.saveAll(resultList);
			for(Chaque check : onHoldChecks) {
				check.setActive(true);
			}
			this.onHoldChecksRepository.saveAll(onHoldChecks); 
		}
		super.notificationsService.addNotification("Gross Settlement Operation passed data check Stage continue to review results reports", "/settlement/checks/reports", "SUPER");
		// add result validation 
		}
	}
	
	@Transactional
	public int addCheck(CheckDisposableModel check ) {
		int result = testCheckInfoValidation(check) ;
		if(result == 0 ) {
			Chaque finalCheck = new Chaque(check.getCheckId(),check.getFirstBankName(), check.getSecondBankName(),check.getFirstBranchName(),
					check.getFirstBranchCode(),check.getSecondBranchName(),check.getSecondBranchCode(),check.getAmount(),super.get_current_User().getUsername(),
					super.get_current_User().getId(),false);
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
	 *  amount less than zero    | -1 			|Logical Error
	 *
	 *  check between branches   | -21			|Logical Error
	 *  of the same bank 	    
	 * 
	 *  sender branch code is 	 | -22			|Logical Error
	 *  equal to receiver 
	 *  branch code
	 *  
	 *  first bank not found 	 | -311 		|First Bank Name Error 
	 *  
	 *  first branch not found   | -312			|First Branch Code Error 
	 *  
	 *  second bank not found    | -321  		|Second Bank Name Error
	 *  
	 *  second branch not found  | -322			|Second Branch Code Error
	 *  
	 *  check id duplication     | -4  			|
	 * */

	public int testCheckInfoValidation(CheckDisposableModel check ) {
		//check ID data duplication 
		List<Chaque> allChecks = this.onHoldChecksRepository.findAll() ; 
		if(check.getAmount() <= 0 ) {
			return -1 ;
		}
		
		if(check.getFirstBankName().equalsIgnoreCase(check.getSecondBankName())) {
			return -21 ; 
		}
		
		if(check.getFirstBranchCode().equalsIgnoreCase(check.getSecondBranchCode())) {
			return -22 ; 
		}
		
		//check the first bank data 
		Banks bank = this.banksService.getBankByName(check.getFirstBankName());
		if(bank == null ){
			return -311 ; 
		}
		//check branch 
		List<Branches> firstBankBranches = this.branchesService.getBankBranches(bank);
		boolean found = false; 
		for(Branches branch : firstBankBranches ) {
			if(branch.getBrancheCode().equalsIgnoreCase(check.getFirstBranchCode())) {
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
		boolean found2 = false ; 
		List<Branches> secondBankBranches = this.branchesService.getBankBranches(bank2);
		for(Branches branch : secondBankBranches) {
			if(branch.getBrancheCode().equalsIgnoreCase(check.getSecondBranchCode())) {
				found2 = true ; 
			}
		}
		if(!found2) {
			return -322 ; 
		}
		
		for(Chaque tempCheck : allChecks ) {
			if(check.getCheckId() == tempCheck.getCheckId() ) {
				return -4;  
			}
		}	
		return 0 ; 
	}
		
	
	public List<Chaque> getOnHoldChecks(int PageNumber){	
		Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize(), Sort.by("id"));		
		Page<Chaque> pagedResult = this.onHoldChecksRepository.findAll(paging);
		List<Chaque> allList = new ArrayList<Chaque>() ; 
		if (pagedResult.hasContent()) {
			for(Chaque check : pagedResult.getContent() ) {
				if(!check.isActive())
				allList.add(check);
			}
            return allList; 
        } else {
            return new ArrayList<Chaque>();
        }
	}
	public List<SettledChaque> getSettledChecksbyFirstBankName(int PageNumber){
		Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize(), Sort.by("id"));
		Slice<SettledChaque> pagedResult = this.settledChecksRepository.findByFirstBankName("ok",paging);
		//List<EmployeeEntity> employeeList = slicedResult.getContent();
		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<SettledChaque>();
		}
	}
	
	public List<SettledChaque> getSettledChecks(int PageNumber){
		Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize(), Sort.by("id"));
		Page<SettledChaque> pagedResult = this.settledChecksRepository.findAll(paging);
		if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<SettledChaque>();
        }
	}
	
	public List<Chaque> getAllChecks(int PageNumber){
		Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize(), Sort.by("id"));		
		Page<Chaque> pagedResult = this.onHoldChecksRepository.findAll(paging);
		if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Chaque>();
        }
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
