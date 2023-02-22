package com.helpCenter.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.helpCenter.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByuserName(String userName);

	@Query(value = "select * from user WHERE active=false", nativeQuery = true)
	List<User> getAllUser();
	
	@Query(value = "select name from user",nativeQuery =true)
	List<String> findAllActiveUsersName();
}
