package com.hcl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcl.model.Category;
import com.hcl.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/add")
	public ResponseEntity<Category> addCategory(@RequestBody Category category) {
		Category newCategory = categoryService.addCategory(category);
		logger.info("A new category has been created.");
		return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
	}

	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getAllCategory() {
		List<Category> categories = categoryService.findAllCategory();
		logger.info("All the categories have been listed.");
		return new ResponseEntity<>(categories, HttpStatus.OK);

	}

	@GetMapping("/categories/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id) {
		Category category = categoryService.findAllCategoryById(id);
		logger.info("A category with id: {} has been retreived.", id);
		return new ResponseEntity<>(category, HttpStatus.OK);

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Category> updateCategoryById(@RequestBody Category category, @PathVariable Long id) {
		Category updatedCategory = categoryService.updateCategory(category, id);
		logger.info("A category with id: {} has been updated.", id);
		return new ResponseEntity<>(updatedCategory, HttpStatus.OK);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Category> deleteCategoryById(@PathVariable("id") Long id) {
		categoryService.deleteCategory(id);
		logger.warn("A category with id: {} has been deleted.", id);
		return new ResponseEntity<>(HttpStatus.OK);

	}
}
