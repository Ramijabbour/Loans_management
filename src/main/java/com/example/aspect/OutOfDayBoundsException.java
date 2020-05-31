package com.example.aspect;

public class OutOfDayBoundsException extends RuntimeException  {

	private static final long serialVersionUID = 1L;

	private String ErrorMsg ;
	private int ExceptionCode ;
	
	public OutOfDayBoundsException() {
		super();
		ErrorMsg = "لا يمكن الوصول للخدمات خارج أوقات الدوام";
		ExceptionCode = -409;
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
