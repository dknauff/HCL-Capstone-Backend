package com.hcl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcl.model.Category;
import com.hcl.service.CategoryService;

@CrossOrigin(value = "https://capstone-front-end-react.herokuapp.com/", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/category")
public class CategoryController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CategoryService categoryService;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping()
	public ResponseEntity<Category> addCategory(@RequestBody Category category) {
		Category newCategory = categoryService.addCategory(category);
		logger.info("A new category has been created.");
		return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Category> updateCategoryById(@RequestBody Category category, @PathVariable Long id) {
		Category updatedCategory = categoryService.updateCategory(category, id);
    logger.info("A category with id: {} has been updated.", id);
		return new ResponseEntity<>(updatedCategory, HttpStatus.OK);

	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Category> deleteCategoryById(@PathVariable("id") Long id) {
		categoryService.deleteCategory(id);
    logger.warn("A category with id: {} has been deleted.", id);
		return new ResponseEntity<>(HttpStatus.OK);

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
	
	@GetMapping("/instock")
	public ResponseEntity<List<Category>> getAllCategoriesInstock(){
    logger.info("The category is in-stock");
		return new ResponseEntity<>(categoryService.findAllInstock(true), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/instock/{id}")
	public ResponseEntity<String> updateInstock(@RequestBody boolean instock, @PathVariable Long id){
		boolean updated = categoryService.updateInstock(instock, id);
    logger.info("The category that is in-instock with id:{} has been updated.", id);
		return updated ? new ResponseEntity<>("Updated successfully", HttpStatus.OK)
				: new ResponseEntity<>("Unsuccessful update", HttpStatus.OK);
	}
}
