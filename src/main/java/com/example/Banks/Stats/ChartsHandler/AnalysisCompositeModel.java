package com.example.Banks.Stats.ChartsHandler;

import java.util.List;

public class AnalysisCompositeModel {
	private String title; // allocations for bank x between years a-b
    private List<String> categories; // contains the bank name 
    private List<AnalysisModel> series; // 
    
    
    public AnalysisCompositeModel() {}
    
	public AnalysisCompositeModel(String title, List<String> categories, List<AnalysisModel> series) {
		super();
		this.title = title;
		this.categories = categories;
		this.series = series;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public List<AnalysisModel> getSeries() {
		return series;
	}
	public void setSeries(List<AnalysisModel> series) {
		this.series = series;
	}


    

}
