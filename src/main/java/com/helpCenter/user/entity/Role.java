package com.helpCenter.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Role {
	@Id
	private int roleId;

	private String role;

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Role(int roleId, String role) {
		super();
		this.roleId = roleId;
		this.role = role;
		// this.user = user;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", role=" + role + "]";
	}

}
