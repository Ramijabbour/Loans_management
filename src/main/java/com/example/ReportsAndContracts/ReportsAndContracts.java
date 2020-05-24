package com.example.ReportsAndContracts;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.aspect.EncryptDecrypt.IntEncryptDecryptConverter;



@Entity
public class ReportsAndContracts {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Convert(converter = IntEncryptDecryptConverter.class)
	private int id ; 
	
	@Convert(converter = IntEncryptDecryptConverter.class)
	private int ReportId ;
	

	@Convert(converter = IntEncryptDecryptConverter.class)
	private int Type ; 
	//1 means Docx 
	//2 means PDF

	
	public ReportsAndContracts() {
		// TODO Auto-generated constructor stub
	}
	
	public ReportsAndContracts(int reportId , int type ) {
		this.ReportId = reportId ; 
		this.Type = type ; 
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setReportId(int reportId) {
		ReportId = reportId;
	}
	
	public void setType(int type) {
		Type = type;
	}
	
	public int getId() {
		return id;
	}
	
	public int getReportId() {
		return ReportId;
	}
	
	public int getType() {
		return Type;
	}
	
	
}
