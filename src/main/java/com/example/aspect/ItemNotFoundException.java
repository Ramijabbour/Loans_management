package com.example.aspect;

public class ItemNotFoundException  extends Exceptions{

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
