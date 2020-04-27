package com.example.settelmets.Models;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.aspect.EncryptDecrypt.IntEncryptDecryptConverter;
import com.example.aspect.EncryptDecrypt.StringEncryptDecryptConverter;

@Entity
public class ReportsLinkModel {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Convert(converter = IntEncryptDecryptConverter.class)
	private int EntryId ; 
	
	@Convert(converter = IntEncryptDecryptConverter.class)
	private int ReportId ;
	
	@Convert(converter = StringEncryptDecryptConverter.class)
	private String Path ; 
	
	@Convert(converter = IntEncryptDecryptConverter.class)
	private int Type ; 
	//1 means Docx 
	//2 means PDF
	
	public ReportsLinkModel(int reportId , String path ,int type ) {
		this.Path = path ; 
		this.ReportId = reportId ; 
		this.Type = type ; 
	}
	
	public ReportsLinkModel() {}

	public int getReportId() {
		return ReportId;
	}

	public void setReportId(int reportId) {
		ReportId = reportId;
	}

	public String getPath() {
		return Path;
	}

	public void setPath(String path) {
		Path = path;
	}

	public int getEntryId() {
		return EntryId;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}
	
	
	
}
