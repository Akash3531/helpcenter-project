package com.helpCenter.category.dtos;

import org.springframework.stereotype.Component;

import com.helpCenter.category.entity.Category;
import com.helpCenter.requestHandler.entities.RequestHandler;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Component
public class RequestCategoryDto {

	@NotEmpty
	@Pattern(regexp = "^(?=.{2,20}$)(?![_.])(?!.*[_.]{2})[A-Za-z._]+(?<![_.])$")
	private String name;
	@NotEmpty
	@Pattern(regexp = "^(?=.*[0-9])" + "(?=.*[a-z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{2,20}$")
	private String code;
	private Category parent;
	private boolean Flag;
	private RequestHandler requestHandler;
	public RequestCategoryDto(RequestCategoryDto categoryDto) {
		super();
		this.name = categoryDto.getName();
	}
	
	public RequestCategoryDto(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}
	public boolean getFlag() {
		return Flag;
	}

	public void setFlag(boolean b) {
		Flag = b;
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

	public RequestCategoryDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequestHandler getRequestHandler() {
		return requestHandler;
	}

	public void setRequestHandler(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}
	@Override
	public String toString() {
		return "categoryDto [name=" + name + ", code=" + code + ", parent=" + parent + ", Flag=" + Flag + "]";
	}

}
