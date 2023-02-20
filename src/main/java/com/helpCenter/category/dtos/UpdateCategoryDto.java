package com.helpCenter.category.dtos;

import com.helpCenter.category.entity.Category;

import jakarta.validation.constraints.Pattern;

public class UpdateCategoryDto {

	@Pattern(message = "Minimum 2 characters requried", regexp = "^(?=.{2,20}$)(?![_.])(?!.*[_.]{2})[A-Za-z._]+(?<![_.])$")
	private String name;
	@Pattern(regexp = "^(?=.*[0-9])" + "(?=.*[a-z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{2,20}$")
	private String code;
	private Category parent;
	private int Flag;

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

	@Override
	public String toString() {
		return "categoryDto [name=" + name + ", code=" + code + ", parent=" + parent + ", Flag=" + Flag + "]";
	}

}
