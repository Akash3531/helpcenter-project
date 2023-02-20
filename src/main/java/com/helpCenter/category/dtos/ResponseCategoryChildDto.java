package com.helpCenter.category.dtos;

public class ResponseCategoryChildDto {
	private String name;
	private String code;

	public ResponseCategoryChildDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseCategoryChildDto(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
