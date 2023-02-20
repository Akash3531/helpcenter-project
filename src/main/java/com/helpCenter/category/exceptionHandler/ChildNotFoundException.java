package com.helpCenter.category.exceptionHandler;

public class ChildNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String name;

	public ChildNotFoundException(String name) {
		super(String.format("Children not found for Category name :%s", name));
		this.name = name;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
