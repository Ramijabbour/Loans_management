package com.example.aspect;



//throwable class 
public class Exceptions extends RuntimeException {
	
	private static final long serialVersionUID = 1L ; 
	private int ExceptionCode ; 
	private String ErrorMsg ;

	
	public Exceptions(int exceptionNumber,String errorMsg){
		this.ErrorMsg = errorMsg ; 
		this.ExceptionCode = exceptionNumber ; 
	}
	
	public String getException() {
		return "error code : "+this.ExceptionCode + " : "+this.ErrorMsg;
	}
}
