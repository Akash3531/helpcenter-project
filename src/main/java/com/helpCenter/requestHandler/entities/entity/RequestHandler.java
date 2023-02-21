package com.helpCenter.requestHandler.entities.entity;

import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.helpCenter.category.entity.Category;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class RequestHandler {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@JdbcTypeCode(SqlTypes.JSON)
	private List<HandlerDetails> handler;
	
	@OneToOne
	@JsonBackReference
	@JoinColumn(name="category_id")
	private Category category;

	public RequestHandler() {
		super();
	}

	public RequestHandler(int id, List<HandlerDetails> handler, Category category) {
		super();
		this.id = id;
		this.handler = handler;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<HandlerDetails> getHandler() {
		return handler;
	}

	public void setHandler(List<HandlerDetails> handler) {
		this.handler = handler;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


	
}
