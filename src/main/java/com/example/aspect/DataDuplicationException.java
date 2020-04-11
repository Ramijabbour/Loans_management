package com.example.aspect;

public class DataDuplicationException extends Exceptions {

	private static final long serialVersionUID = 1L;
	
	public DataDuplicationException() {
		super(); 
		this.ErrorMsg = "the Entered Info is duplicated with other entry ";
	}
		
	public String getErrorMsg() {
		return ErrorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		ErrorMsg = errorMsg;
	}

	
}
