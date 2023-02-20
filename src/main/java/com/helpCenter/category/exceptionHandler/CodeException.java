package com.helpCenter.category.exceptionHandler;

public class CodeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String code;

	public CodeException(String code) {
		super(String.format("Code may be null or code is already exist: %s", code));
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
