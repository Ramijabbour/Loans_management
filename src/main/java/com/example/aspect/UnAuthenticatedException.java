package com.example.aspect;

public class UnAuthenticatedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String ErrorMsg ;
	private int ExceptionCode ; 
	
	public UnAuthenticatedException() {
		super(); 
		this.ErrorMsg = "يرجى تسجيل الدخول للمتابعة";
		this.ExceptionCode = -403 ; 
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
