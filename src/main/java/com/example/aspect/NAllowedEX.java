package com.example.aspect;

public class NAllowedEX extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ErrorMsg ;
	private int ExceptionCode ; 
	
	public NAllowedEX() {
		super(); 
		this.ErrorMsg = "العملية غير مسموحة";
		this.ExceptionCode = -405 ; 
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
