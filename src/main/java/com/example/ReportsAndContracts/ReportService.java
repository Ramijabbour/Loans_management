package com.example.ReportsAndContracts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service 
public class ReportService {
	
	
	@Autowired
	private ReportsAndContractsRepository reportsLinkRepository ; 
	
	
	public ReportsAndContracts getRportLinkModel(int reportId , int type ) {
		for(ReportsAndContracts model : this.reportsLinkRepository.findAll()) {
			if(model.getId() == reportId && model.getType() == type ) {
				return model ; 
			}
		}
		return null; 
	}
	
	public void addReportLinkModel(ReportsAndContracts reportLinkModel , int type  ) {
		if(this.getRportLinkModel(reportLinkModel.getId(), type ) == null ) {
			reportLinkModel.setType(type);
			this.reportsLinkRepository.save(reportLinkModel); 
		}
	}
	
	public void deleteReportLink(ReportsAndContracts reportsLinkModel ) {
		this.reportsLinkRepository.delete(reportsLinkModel);
	}
	
	
}