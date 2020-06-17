package com.example.settelmets.RTGSLink;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.SiteConfig.MasterService;
import com.example.settelmets.Models.Chaque;
import com.example.settelmets.Models.SettledChaque;


public class SettlementReport implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id ; 
	
	private List<Chaque> checksList ; // checks with state true 
	
	private List<SettledChaque> settlementRports ;

	private String operationTimeStamp ; 
	
	
	public SettlementReport(List<Chaque> checksList, List<SettledChaque> settlementRports) {
		super();
		this.checksList = checksList;
		this.settlementRports = settlementRports;
		this.operationTimeStamp = MasterService.getDateTimeAsString() ; 
	}

	public String getOperationTimeStamp() {
		return operationTimeStamp;
	}

	public void setOperationTimeStamp(String operationTimeStamp) {
		this.operationTimeStamp = operationTimeStamp;
	}

	public List<Chaque> getChecksList() {
		return checksList;
	}

	public void setChecksList(List<Chaque> checksList) {
		this.checksList = checksList;
	}

	public List<SettledChaque> getSettlementRports() {
		return settlementRports;
	}

	public void setSettlementRports(List<SettledChaque> settlementRports) {
		this.settlementRports = settlementRports;
	} 
	
}
