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

import com.hcl.model.Category;
import com.hcl.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@PostMapping()
	public ResponseEntity<Category> addCategory(@RequestBody Category category) {
		Category newCategory = categoryService.addCategory(category);
		return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Category> updateCategoryById(@RequestBody Category category, @PathVariable Long id) {
		Category updatedCategory = categoryService.updateCategory(category, id);
		return new ResponseEntity<>(updatedCategory, HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Category> deleteCategoryById(@PathVariable("id") Long id) {
		categoryService.deleteCategory(id);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getAllCategory() {
		List<Category> categories = categoryService.findAllCategory();
		return new ResponseEntity<>(categories, HttpStatus.OK);

	}

	@GetMapping("/categories/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id) {
		Category category = categoryService.findAllCategoryById(id);
		return new ResponseEntity<>(category, HttpStatus.OK);

	}
	
	@GetMapping("/instock")
	public ResponseEntity<List<Category>> getAllCategoriesInstock(){
		return new ResponseEntity<>(categoryService.findAllInstock(true), HttpStatus.OK);
	}
	
	@PutMapping("/instock/{id}")
	public ResponseEntity<String> updateInstock(@RequestBody boolean instock, @PathVariable Long id){
		boolean updated = categoryService.updateInstock(instock, id);
		return updated ? new ResponseEntity<>("Updated successfully", HttpStatus.OK)
				: new ResponseEntity<>("Unsuccessful update", HttpStatus.OK);
	}
}
