package com.helpCenter.category.entity;

import org.hibernate.annotations.Where;
import org.springframework.stereotype.Component;

import com.helpCenter.category.dtos.RequestCategoryDto;
import com.helpCenter.category.dtos.UpdateCategoryDto;
import com.helpCenter.requestHandlers.entity.RequestHandler;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Where(clause = "active=true")
//@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Entity
@Table
@Component
public class Category {

	@Id
	@Column(name = "category_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Integer id;
	@Column(length = 64)
	private String name;
	private boolean active = true;
	private int etaInMinutes;
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Category parent;
	private String code;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "category", fetch = FetchType.EAGER)
	private RequestHandler requestHandler;

	public Category(Integer id, String name, boolean active,int etaInMinutes, Category parent, String code,
			RequestHandler requestHandler) {
		super();
		this.id = id;
		this.name = name;
		this.active = active;
		this.etaInMinutes=etaInMinutes;
		this.parent = parent;
		this.code = code;
		this.requestHandler = requestHandler;
	}

	public Category(String name,String code, Category parent) {
		this.name = name;
		this.code=code;
		this.parent = parent;
	}

	public Category(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Mapper categoryDto to Category
	public Category(RequestCategoryDto category) {
		this.code = category.getCode();
		this.name = category.getName();
		this.parent = category.getParent();
		this.etaInMinutes=category.getEtaInMinutes();
		this.requestHandler = category.getRequestHandler();

	}

	// Mapper updateCategoryDto to Category
	public Category(UpdateCategoryDto category) {
		this.code = category.getCode();
		this.name = category.getName();
		this.parent = category.getParent();
		this.etaInMinutes=category.getEtaInMinutes();
		this.requestHandler=category.getRequestHandler();
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getEtaInMinutes() {
		return etaInMinutes;
	}

	public void setEtaInMinutes(int etaInMinutes) {
		this.etaInMinutes = etaInMinutes;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public RequestHandler getRequestHandler() {
		return requestHandler;
	}

	public void setRequestHandler(RequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	@Override
	public String toString() {
		return " [ name=" + name + ", parent=" + parent + "]";
	}

}