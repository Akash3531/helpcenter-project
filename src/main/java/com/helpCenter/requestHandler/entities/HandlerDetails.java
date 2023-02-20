package com.helpCenter.requestHandler.entities;

import java.util.List;

public class HandlerDetails {
	
	private int level;
	private List<String> resources;
	
	public HandlerDetails() {
		super();
		
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public List<String> getResources() {
		return resources;
	}

	public void setResources(List<String> resources) {
		this.resources = resources;
	}

	@Override
	public String toString() {
		return "HandlerDetails [level=" + level + ", resources=" + resources + "]";
	}
	

	
}
