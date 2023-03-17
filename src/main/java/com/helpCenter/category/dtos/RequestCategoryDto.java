package com.helpCenter.category.dtos;

import org.springframework.stereotype.Component;

import com.helpCenter.category.entity.Category;
import com.helpCenter.requestHandlers.entity.RequestHandler;

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
	private boolean active;
	private int etaInMinutes;
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
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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
	public int getEtaInMinutes() {
		return etaInMinutes;
	}

	public void setEtaInMinutes(int etaInMinutes) {
		this.etaInMinutes = etaInMinutes;
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
		return "categoryDto [name=" + name + ", code=" + code + ", parent=" + parent + ", Active=" + active + "]";
	}

}
