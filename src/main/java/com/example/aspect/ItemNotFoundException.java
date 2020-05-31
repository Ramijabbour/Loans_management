package com.example.aspect;

public class ItemNotFoundException  extends Exceptions{

	private static final long serialVersionUID = 1L;

	public ItemNotFoundException() {
		super(); 
		this.ErrorMsg = "the requested item not found  ";
	}
		
	public String getErrorMsg() {
		return ErrorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		ErrorMsg = errorMsg;
	}
	
}
