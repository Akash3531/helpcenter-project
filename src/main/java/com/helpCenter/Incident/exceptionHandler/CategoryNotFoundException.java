package com.helpCenter.Incident.exceptionHandler;

public class CategoryNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String code;

	public CategoryNotFoundException(String code) {
		super(String.format("Category Not Found With Code :%s", code));
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
