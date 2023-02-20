package com.helpCenter.category.exceptionHandler;

public class ResourceAlreadyExist extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String name;

	public ResourceAlreadyExist(String name) {
		super(String.format("Category Already Exist With Name :%s", name));
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
