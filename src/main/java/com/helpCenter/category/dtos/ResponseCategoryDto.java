package com.helpCenter.category.dtos;

import org.springframework.stereotype.Component;

import com.helpCenter.category.entity.Category;
import com.helpCenter.requestHandler.entities.entity.RequestHandler;

import lombok.Data;

@Data
@Component
public class ResponseCategoryDto {

	private String name;
	private String code;
	private Category parent;
	private RequestHandler requestHandler;
	public ResponseCategoryDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseCategoryDto(String name, String code, Category parent,RequestHandler requestHandler) {
		super();
		this.name = name;
		this.code = code;
		this.parent = parent;
		this.requestHandler=requestHandler;
	}

	public ResponseCategoryDto(String name, String code) {
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

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}
	

	public RequestHandler getRequestHandler() {
		return requestHandler;
	}

	public void setRequestHandler(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	@Override
	public String toString() {
		return "GetCategoryDto [name=" + name + ", code=" + code + "]";
	}

	// Conversion Method -( Category To categoryDto)
	public RequestCategoryDto categoryTODto(Category category) {
		RequestCategoryDto dto = new RequestCategoryDto();
		dto.setCode(category.getCode());
		dto.setName(category.getName());
		dto.setParent(category.getParent());
		dto.setRequestHandler(category.getRequestHandler());
		
		return dto;
	}

}
