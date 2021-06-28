package com.hcl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.customexception.CategoryNotFoundException;
import com.hcl.model.Category;
import com.hcl.repo.CategoryRepo;

@Service
@Transactional
public class CategoryService {
	@Autowired
	private CategoryRepo categoryRepo;

	public Category addCategory(Category category) {
		return categoryRepo.save(category);
	}

	public List<Category> findAllCategory() {
		return categoryRepo.findAll();

	}


	public Category findAllCategoryById(Long id) {
		return categoryRepo.findCategoryByCategoryId(id).orElseThrow(() -> new CategoryNotFoundException("Category by id " + id + " was not found"));
	}

	public Category updateCategory(Category category, Long id) {
		category.setCategoryId(id);
		return categoryRepo.save(category);
	}

	public void deleteCategory(Long id) {
		categoryRepo.deleteCategoryByCategoryId(id);

	}

}