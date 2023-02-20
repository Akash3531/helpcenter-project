package com.helpCenter.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helpCenter.user.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
