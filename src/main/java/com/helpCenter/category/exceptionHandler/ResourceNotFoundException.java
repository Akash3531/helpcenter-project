package com.helpCenter.category.exceptionHandler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	String name;

	public ResourceNotFoundException(String name) {
		super(String.format("Category not found wiht name :%s", name));
		this.name = name;

	}

}
