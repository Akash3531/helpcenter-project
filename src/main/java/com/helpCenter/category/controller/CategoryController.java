package com.helpCenter.category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helpCenter.category.dtos.RequestCategoryDto;
import com.helpCenter.category.dtos.ResponseCategoryChildDto;
import com.helpCenter.category.dtos.ResponseCategoryDto;
import com.helpCenter.category.dtos.UpdateCategoryDto;
import com.helpCenter.category.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	// CREATE CATEGORY
	@PostMapping(path="/",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createCategory(@Valid @RequestBody RequestCategoryDto category) {
		 categoryService.createCategory(category);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	//Get Category By name
	@GetMapping("/{code}")
	public ResponseEntity<ResponseCategoryDto> GetByName(@PathVariable String code)
	{
		ResponseCategoryDto category = categoryService.getCategory(code);
		return new ResponseEntity<ResponseCategoryDto>(category, HttpStatus.OK);
	}
	
	// GET ALL CATEGORIES
	@GetMapping("/")
	public ResponseEntity<List<RequestCategoryDto>> getCategories() {
		List<RequestCategoryDto> category = categoryService.getCategory();
		return new ResponseEntity<List<RequestCategoryDto>>(category, HttpStatus.OK);
	}

	// fetching child of a category
	@GetMapping("/{code}/child")
	public ResponseEntity<List<ResponseCategoryChildDto>> getChild(@PathVariable("code") String code) {
		
		List<ResponseCategoryChildDto> childrenOfCategory = categoryService.childrenOfCategory(code);
		return new ResponseEntity<List<ResponseCategoryChildDto>>(childrenOfCategory,HttpStatus.OK);
	}

	// Update Fields
	@PatchMapping("/{code}")
	public ResponseEntity<?> update(@PathVariable String code, @Valid @RequestBody UpdateCategoryDto category) {
		categoryService.updateFields(code, category);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// Delete Category
	@DeleteMapping("/{code}")
	public ResponseEntity<?> deleteCategory(@PathVariable String code) {
		categoryService.deleteCategory(code);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
