package com.example.settelmets.Services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.example.MQ.OrderMessageSender;
import com.example.SiteConfig.MasterService;
import com.example.SiteConfig.SiteConfiguration;
import com.example.settelmets.Models.Chaque;
import com.example.settelmets.Models.SettledChaque;
import com.example.settelmets.RTGSLink.ChecksSendingModel;
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
	
	@Autowired
	private OrderMessageSender ordermsgSender ; 
	
	
	private long[][] toSettleChecks ; 
	private int ParticipantsCount ; 
	private List<String> ParticipantsIds ; 
	private List<String> BanksNames ; 
	private List<String> BranchesNames;
	public static Date TTS;

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
	public void settleChecks() throws ParseException {
		
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
				
				//link the checks To the operation report 
				linkChecksToReport(onHoldChecks,settlementReportModel);	
				
				//send the results to RTGS SYS 
				sendSettledChecks(resultList);
				this.settledChecksRepository.saveAll(resultList);
					
				//send the checks To RTGS SYS 
				sendChecks(onHoldChecks);
				
				this.onHoldChecksRepository.saveAll(onHoldChecks);

			}
		}
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
		Date now = new Date();
		TTS=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa").parse(dtf.format(now));
	}		
	
	//@Scheduled(fixedRate = 8000000)
	@Transactional
	public void sendOnHoldChecks() {
		List<Chaque> onHoldChecksList = this.onHoldChecksRepository.findByActiveTrue();
		sendChecks(onHoldChecksList);
	}
	
	private void sendChecks(List<Chaque> checksList) {
		ChecksSendingModel checkSendingModel   = new ChecksSendingModel("",checksList.get(0).getSettlementReportModel());
		for(int index = 0 ; index < checksList.size() ; index ++ ) {
			Chaque check = checksList.get(index);
			if(check.getSettlementReportModel().getTimestamp().equalsIgnoreCase(checkSendingModel.getSettlementReportModel().getTimestamp())) {
				if(index == checksList.size()-1) {
					checkSendingModel.addCheckSequenceNumber(check.getSequenceNum(), true);
				}else {
					checkSendingModel.addCheckSequenceNumber(check.getSequenceNum(), false);
				}
			}
		}
		
		 try{
			 System.out.println("TS  : "+checkSendingModel.getSettlementReportModel().getTimestamp());
			 System.out.println("Seq : "+checkSendingModel.getChecksSequence());
			 this.ordermsgSender.sendOrderCheck(checkSendingModel);
			 System.out.println("S1");
			 for(String sequenceNum : checkSendingModel.getChecksSequence().split(",")) {
				Chaque check = this.onHoldChecksRepository.findBysequenceNum(Integer.valueOf(sequenceNum));
				 check.setSent(true);
				 this.onHoldChecksRepository.save(check);
			 }
			 System.out.println("S2");
			  	/*
			 	for(Chaque check : checksList) {
			  		this.ordermsgSender.sendOrderCheck(check);
			  		check.setSent(true);
			  	} */
			  }catch (Exception e ){
				  System.out.println("Checks Sending operation faild: cannot reach messgae queue retrying after 10 min");
			  }
		 System.out.println("S3");
		 return ;
		 //this.onHoldChecksRepository.saveAll(checksList); 
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

	
	private void sendSettledChecks(List<SettledChaque> checksList) {
		try {
			for(SettledChaque settledCheck : checksList ) {
				this.ordermsgSender.sendOrderCheck(settledCheck);
				settledCheck.setSent(true);
			}
		}catch(Exception e ) {
			System.out.println("settled Checks Sending operation faild re-trying after 10 min");
		}		
	}
	
	private void linkChecksToReport(List<Chaque> checksList, SettlementReportModel settlementReportModel) {
		for(Chaque check : checksList) {
			check.setActive(true);
			check.setSettlementReportModel(settlementReportModel);	
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
	
	public List<SettlementReportModel> getAllReports(){
		return this.settlementReportRepo.findAll();
	}

	public List<Chaque> getChecksByReport(int id){
		List<Chaque> checksList = this.onHoldChecksRepository.findBysettlementReportModel(this.settlementReportRepo.findById(id));
		return checksList ; 
	}
	
	public List<SettledChaque> getSettledChecksByReport(int id){
		List<SettledChaque> checksList = this.settledChecksRepository.findBysettlementReportModel(this.settlementReportRepo.findById(id));
		return checksList ; 
	}
	
}
