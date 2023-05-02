package com.helpCenter.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.helpCenter.user.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

//	@Query(value = "select * from role WHERE role_id=?", nativeQuery = true)
//	Role getNonAdmin(int id);
//
//	@Query(value = "select * from role WHERE role_id=?", nativeQuery = true)
//	Role getAdmin(int id);

	@Query(value = "select * from role WHERE role=?", nativeQuery = true)
	Role getRole(String role);
}
