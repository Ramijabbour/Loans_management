package com.example.aspect;

public class UnAuthorizedException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private String ErrorMsg ;
	private int ExceptionCode ; 
	
	public UnAuthorizedException() {
		super(); 
		this.ErrorMsg = "UnAuthorized user : cannot Access requested Service ";
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
