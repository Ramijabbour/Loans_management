package com.example.aspect;

public class UnAuthenticatedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String ErrorMsg ;
	private int ExceptionCode ; 
	
	public UnAuthenticatedException() {
		super(); 
		this.ErrorMsg = "UnRegistered user access attempt ";
		this.ExceptionCode = -401 ; 
	}
	
	
	public String getErrorMsg() {
		return ErrorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		ErrorMsg = errorMsg;
	}

	public int getExceptionCode() {
		return ExceptionCode;
	}

	public void setExceptionCode(int exceptionCode) {
		ExceptionCode = exceptionCode;
	}

	
	
}
