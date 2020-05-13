package com.example.Banks.Stats.ChartsHandler;

import java.util.ArrayList;
import java.util.List; 

public class AnalysisModel {

	private String name ; // year
	private List<Integer> data = new ArrayList<Integer>() ; // one entry for this year allocation  
	
	public AnalysisModel() {}
	
	public AnalysisModel(String name , List<Integer> data ) {
		this.name = name ; 
		this.data = data ; 
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Integer> getData() {
		return data;
	}
	public void setData(List<Integer> data) {
		this.data = data;
	} 
	
	public void addDataEntry(int data ) {
		this.data.add(data);
	}
	
	
}
