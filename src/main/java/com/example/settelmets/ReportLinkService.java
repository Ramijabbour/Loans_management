package com.example.settelmets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service 
public class ReportLinkService {
	
	
	@Autowired
	private ReportsLinkRepository reportsLinkRepository ; 
	
	
	public ReportsLinkModel getRportLinkModel(int reportId , int type ) {
		for(ReportsLinkModel model : this.reportsLinkRepository.findAll()) {
			if(model.getReportId() == reportId && model.getType() == type ) {
				return model ; 
			}
		}
		return null; 
	}
	
	public void addReportLinkModel(ReportsLinkModel reportLinkModel , int type  ) {
		if(this.getRportLinkModel(reportLinkModel.getReportId(), type ) == null ) {
			reportLinkModel.setType(type);
			this.reportsLinkRepository.save(reportLinkModel); 
		}
	}
	
	
}
