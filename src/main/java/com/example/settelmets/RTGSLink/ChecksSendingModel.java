package com.example.settelmets.RTGSLink;

import java.io.Serializable;

public class ChecksSendingModel implements Serializable{ 
	
	private static final long serialVersionUID = 1L;
	
	private String ChecksSequence = " "; 
	
	private SettlementReportModel settlementReportModel ; 
	
	public ChecksSendingModel() {
		
	}
	
	public ChecksSendingModel(String ChecksSequence,SettlementReportModel settlementReportModel){
		this.ChecksSequence = ChecksSequence;
		this.settlementReportModel = settlementReportModel ; 
	}
	
	
	public void addCheckSequenceNumber(int checkSequenceNumber,boolean end ) {
		if(end) {
			ChecksSequence+= String.valueOf(checkSequenceNumber);
		}else {
			ChecksSequence+= String.valueOf(checkSequenceNumber)+",";
		}
	}

	public SettlementReportModel getSettlementReportModel() {
		return settlementReportModel;
	}


	public void setSettlementReportModel(SettlementReportModel settlementReportModel) {
		this.settlementReportModel = settlementReportModel;
	}


	public String getChecksSequence() {
		return ChecksSequence;
	}	
	
}
