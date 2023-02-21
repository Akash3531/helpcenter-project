package com.helpCenter.category.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.helpCenter.category.dtos.RequestCategoryDto;
import com.helpCenter.category.dtos.ResponseCategoryChildDto;
import com.helpCenter.category.dtos.ResponseCategoryDto;
import com.helpCenter.category.dtos.UpdateCategoryDto;
import com.helpCenter.category.entity.Category;
import com.helpCenter.category.exceptionHandler.ChildNotFoundException;
import com.helpCenter.category.exceptionHandler.CodeException;
import com.helpCenter.category.exceptionHandler.ResourceAlreadyExist;
import com.helpCenter.category.exceptionHandler.ResourceNotFoundException;
import com.helpCenter.category.repository.CategoryRepo;
import com.helpCenter.category.services.CategoryService;
import com.helpCenter.requestHandlers.entity.HandlerDetails;
import com.helpCenter.requestHandlers.entity.RequestHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepo categoryRepository;

	@Autowired
	ResponseCategoryDto responseCategoryDto;
	@Autowired
	RequestCategoryDto requestCategoryDto;

// CREATE CATEGORY
	@Override
	public void createCategory(RequestCategoryDto categorydto) {
		Category category = new Category(categorydto);
		String categoyName = category.getName();
		Category findCategory = categoryRepository.findByName(categoyName);
		if (findCategory != null) {
			throw new ResourceAlreadyExist(categoyName);
		}
		Category categoryByCode = categoryRepository.findByCode(category.getCode().toUpperCase());
		if (category.getCode() == null || categoryByCode != null) {
			throw new CodeException(category.getCode());
		} else {
			category.setCode(category.getCode().toUpperCase());
		}
		if (category.getParent() != null) {
			String name = category.getParent().getName();
			Category parent = categoryRepository.findByName(name);
			if (parent == null) {
				throw new ResourceNotFoundException(name);
			} else {
				category.setParent(parent);
			}
		}
		if (category.getRequestHandler() != null) {
			category.getRequestHandler().setCategory(category);
		}
		categoryRepository.save(category);

	}

// UPDATE CATEGORY FIELDS
	@Override
	public void updateFields(String code, UpdateCategoryDto Category) {
		Category category = new Category(Category);
		Category updateCategory = categoryRepository.findByCode(code.toUpperCase());
		System.out.println(updateCategory);
		if (updateCategory == null || updateCategory.getFlag() == true) {
			throw new ResourceNotFoundException(code);
		}
		String CategoryName = category.getName();
		if (CategoryName != null) {
			Category categoryByName = categoryRepository.findByName(category.getName());
			if (categoryByName != null) {
				throw new ResourceAlreadyExist(CategoryName);
			} else {
				updateCategory.setName(CategoryName);
			}

		}
		if (category.getParent() != null) {
			String parentName = category.getParent().getName();
			Category parent = categoryRepository.findByName(parentName);
			if (parent == null) {
				throw new ResourceNotFoundException(parentName);
			} else {
				updateCategory.setParent(parent);
			}
		}
		if (category.getCode() != null) {
			Category categoryByCode = categoryRepository.findByCode(Category.getCode().toUpperCase());
			if (categoryByCode != null) {
				throw new CodeException(code);
			}
			updateCategory.setCode(category.getCode().toUpperCase());
		}
		categoryRepository.save(updateCategory);
	}

// GET CATEGORY BY CODE
	public ResponseCategoryDto getCategory(String code) {
		String categoryCode = code.toUpperCase();
		Category category = categoryRepository.findByCode(categoryCode);
		if (category == null) {
			throw new ResourceNotFoundException(code);
		}
		return new ResponseCategoryDto(category.getName(), category.getCode(), category.getParent(),category.getRequestHandler());
	}

// GET ALL CATEGORIES
	@Override
	public List<RequestCategoryDto> getCategory() {
		List<Category> categories = categoryRepository.findAll();
		List<RequestCategoryDto> categoriesList = categories.stream()
				.map(category -> responseCategoryDto.categoryTODto(category)).collect(Collectors.toList());
		return categoriesList;
	}

// FETCH CHILDREN OF A CATEGORY
	@Override
	public List<ResponseCategoryChildDto> childrenOfCategory(String code) {

		List<ResponseCategoryChildDto> childsDto = new ArrayList<>();
		Category findByName = categoryRepository.findByCode(code.toUpperCase());
		if (findByName == null) {
			throw new ResourceNotFoundException(code);
		}
		List<Category> categoryChildList = categoryRepository.getChild(findByName.getId());
		if (categoryChildList.isEmpty()) {
			throw new ChildNotFoundException(code);
		}
		for (Category category : categoryChildList) {
			childsDto.add(new ResponseCategoryChildDto(category.getName(), category.getCode()));
		}
		return childsDto;
	}
// DELETE CATEGORY BY CODE
	public void deleteCategory(String code) {
		Category category = categoryRepository.findByCode(code);
		if (category == null) {
			throw new ResourceNotFoundException(code);
		} else {
			category.setFlag(true);
			categoryRepository.save(category);
			List<Category> childList = categoryRepository.getChild(category.getId());
			for (Category child : childList) {
				if (child != null) {
					deleteCategory(child.getCode());
				}
			}
		}
	}

}