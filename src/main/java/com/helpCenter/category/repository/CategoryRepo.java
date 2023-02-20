package com.helpCenter.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.helpCenter.category.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

	public Category findByName(String name);

	@Query(value = "Select * from category where code=?", nativeQuery = true)
	public Category findByCode(String code);

	@Query(value = "select * from category where parent_id=?", nativeQuery = true)
	public List<Category> getChild(int id);

}
