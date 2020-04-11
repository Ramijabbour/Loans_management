package com.example.aspect;

//throwable class 
public class Exceptions extends RuntimeException {
	
	private static final long serialVersionUID = 1L ;  
	protected String ErrorMsg ;
	
	public Exceptions() {}
	
	public Exceptions(String errorMsg){
		this.ErrorMsg = errorMsg ; 
	}
	
	public String getException() {
		return this.ErrorMsg;
	}
}
