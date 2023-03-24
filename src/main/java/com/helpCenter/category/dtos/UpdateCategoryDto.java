package com.helpCenter.category.dtos;

import com.helpCenter.category.entity.Category;
import com.helpCenter.requestHandlers.entity.RequestHandler;

import jakarta.validation.constraints.Pattern;

public class UpdateCategoryDto {

	@Pattern(message = "Minimum 2 characters requried", regexp = "^(?=.{2,20}$)(?![_.])(?!.*[_.]{2})[A-Za-z._]+(?<![_.])$")
	private String name;
	@Pattern(regexp = "^(?=.*[0-9])" + "(?=.*[a-z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{2,20}$")
	private String code;
	private Category parent;
	private int etaInMinutes;
	private int Flag;
	private RequestHandler requestHandler;
	public UpdateCategoryDto(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}

	public int getFlag() {
		return Flag;
	}

	public void setFlag(int flag) {
		Flag = flag;
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
