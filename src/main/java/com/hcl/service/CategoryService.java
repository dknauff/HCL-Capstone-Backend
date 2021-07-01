package com.hcl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.customexception.CategoryNotFoundException;
import com.hcl.model.Category;
import com.hcl.model.Product;
import com.hcl.repo.CategoryRepo;
import com.hcl.repo.ProductRepo;

@Service
@Transactional
public class CategoryService {
	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private ProductService productService;
	
	public Category addCategory(Category category) {
		if (category == null)
			return null;
		if (category.getCategoryName().isEmpty())
			return null;

		Category check = categoryRepo.findByCategoryName(category.getCategoryName()).orElse(null);
		if (check != null) {
			if(!check.isInstock()) {
				check.setInstock(true);
				categoryRepo.save(check);
			}
		}else {
			return categoryRepo.save(category);
		}
		return null;
	}

	public List<Category> findAllCategory() {
		return categoryRepo.findAll();

	}

	public Category findAllCategoryById(Long id) {
		if (id == null)
			return null;
		return categoryRepo.findCategoryByCategoryId(id)
				.orElseThrow(() -> new CategoryNotFoundException("Category by id " + id + " was not found"));
	}

	public Category updateCategory(Category category, Long id) {
		if (category == null)
			return null;
		if (category.getCategoryName().isEmpty())
			return null;
		if (id == null)
			return null;
		category.setCategoryId(id);
		return categoryRepo.save(category);
	}

	public void deleteCategory(Long id) {
		if (id == null)
			return;
		categoryRepo.deleteCategoryByCategoryId(id);
	}
	
	public boolean updateInstock(boolean instock, Long id) {
		if(id == null)
			return false;
		Category category = categoryRepo.findById(id).orElse(null);
		if(category == null)
			return false;
		if(category.isInstock() != instock) {
			category.setInstock(instock);
			categoryRepo.save(category);
			if(!category.isInstock()) {
				List<Product> products = productRepo.findAllByInstockAndCategory(true, category);
				products.forEach(x -> {
					productService.setStock(x.getProductId(), false);
				});
				productRepo.saveAll(products);
			}
		}
		return true;
	}
	
	public List<Category> findAllInstock(boolean instock){
		return categoryRepo.findAllByInstock(instock);
	}

}