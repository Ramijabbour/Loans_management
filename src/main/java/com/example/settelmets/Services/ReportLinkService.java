package com.example.settelmets.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.settelmets.Models.ReportsLinkModel;
import com.example.settelmets.Repositories.ReportsLinkRepository;

@Service 
public class ReportLinkService {
	
	
	@Autowired
	private ReportsLinkRepository reportsLinkRepository ; 
	
	
	public ReportsLinkModel getRportLinkModel(int reportId , int type ) {
		for(ReportsLinkModel model : this.reportsLinkRepository.findAll()) {
			if(model.getId() == reportId && model.getType() == type ) {
				return model ; 
			}
		}
		return null; 
	}
	
	public void addReportLinkModel(ReportsLinkModel reportLinkModel , int type  ) {
		if(this.getRportLinkModel(reportLinkModel.getId(), type ) == null ) {
			reportLinkModel.setType(type);
			this.reportsLinkRepository.save(reportLinkModel); 
		}
	}
	
	public void deleteReportLink(ReportsLinkModel reportsLinkModel ) {
		this.reportsLinkRepository.delete(reportsLinkModel);
	}
	
	
}
