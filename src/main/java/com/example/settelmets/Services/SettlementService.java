package com.example.settelmets.Services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.SiteConfig.MasterService;
import com.example.SiteConfig.SiteConfiguration;
import com.example.settelmets.Models.Chaque;
import com.example.settelmets.Models.SettledChaque;
import com.example.settelmets.RTGSLink.SettlementReportModel;
import com.example.settelmets.RTGSLink.SettlementReportRepository;
import com.example.settelmets.Repositories.OnHoldCheckRepository;
import com.example.settelmets.Repositories.SettledChecksRepository;

@Service
public class SettlementService extends MasterService {

	@Autowired 
	private OnHoldCheckRepository onHoldChecksRepository ;  	
	@Autowired
	private SettledChecksRepository settledChecksRepository;
	@Autowired 
	private SettlementReportRepository settlementReportRepo ; 
	
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
		
		SettlementReportModel settlementReportModel = new SettlementReportModel();
		settlementReportModel.setTimestamp(MasterService.getDateTimeAsString());
		settlementReportRepo.save(settlementReportModel);
		
		System.out.println("settlement invoked at : "+MasterService.getCurrDateTime());
		
		List<Chaque> onHoldChecks = initSettlementOperation(); 
		SettelmentHandler.setNumberOfParticipants(this.ParticipantsCount);
		
		if(ParticipantsIds.size() != 0 ) {
			List<SettledChaque> resultList = SettelmentHandler.invokeSettlementSequence(toSettleChecks,ParticipantsIds
					,BanksNames,BranchesNames);
			if(resultList != null ) {
				for(SettledChaque settledCheck : resultList) {
					settledCheck.setSettlementReportModel(settlementReportModel);
				}
				this.settledChecksRepository.saveAll(resultList);
				for(Chaque check : onHoldChecks) {
					check.setActive(true);
					check.setSettlementReportModel(settlementReportModel);	
				}
				/*
				  try{
				  	for(Chaque check : onHoldChecks) {
				  		msgSender.send(check);
				  		check.setSent(true);
				  	} 
				  }catch (Exception e ){
				  	check.setSent(false);
				  }
				 */
				this.onHoldChecksRepository.saveAll(onHoldChecks); 
			}
		}
	}		
	
	public List<Chaque> getOnHoldChecks(int PageNumber){	
		Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize(), Sort.by("id"));		
		Slice<Chaque> pagedResult = this.onHoldChecksRepository.findByActiveFalse(paging); 
		if (pagedResult.hasContent()) {
			return pagedResult.getContent() ; 
        } else {
            return new ArrayList<Chaque>();
        }
	}
	
	public List<Chaque> getTrueChecks(int PageNumber){	
		Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize(), Sort.by("id"));		
		Slice<Chaque> pagedResult = this.onHoldChecksRepository.findByActiveTrue(paging);
		if (pagedResult.hasContent()) {
			return pagedResult.getContent() ; 
        } else {
            return new ArrayList<Chaque>();
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

	public List<Chaque> getAllChecksbyCheckId(int PageNumber,int checkId){
		Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize(), Sort.by("id"));
		Slice<Chaque> pagedResult = this.onHoldChecksRepository.findByCheckId(checkId,paging);
		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Chaque>();
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
