package com.example.aspect;

public class ServiceException extends Exceptions{

	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super(); 
		this.ErrorMsg = "حصل خطأ ما أثناء تنفيذ الخدمة الرجاء المحاولة مرة أخرى";
	}

	public String getErrorMsg() {
		return ErrorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		ErrorMsg = errorMsg;
	}

}


