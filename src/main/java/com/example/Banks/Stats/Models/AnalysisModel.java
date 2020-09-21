package com.example.Banks.Stats.Models;

import java.util.ArrayList;
import java.util.List; 

public class AnalysisModel {

	private String name ; // year
	private List<Long> data = new ArrayList<Long>() ; // one entry for this year allocation  
	
	public AnalysisModel() {}
	
	public AnalysisModel(String name , List<Long> data ) {
		this.name = name ; 
		this.data = data ; 
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Long> getData() {
		return data;
	}
	public void setData(List<Long> data) {
		this.data = data;
	} 
	
	public void addDataEntry(Long data ) {
		this.data.add(data);
	}
	
	
}
