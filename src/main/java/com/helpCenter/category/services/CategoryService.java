package com.helpCenter.category.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.helpCenter.category.dtos.RequestCategoryDto;
import com.helpCenter.category.dtos.ResponseCategoryChildDto;
import com.helpCenter.category.dtos.ResponseCategoryDto;
import com.helpCenter.category.dtos.UpdateCategoryDto;

@Service
public interface CategoryService {

	void createCategory(RequestCategoryDto category);

	ResponseCategoryDto getCategory(String code);

	List<RequestCategoryDto> getCategory();

	void deleteCategory(String code);

	void updateFields(String code, UpdateCategoryDto category);

	List<ResponseCategoryChildDto> childrenOfCategory(String code);

}
