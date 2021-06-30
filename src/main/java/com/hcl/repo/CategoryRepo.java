package com.hcl.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {
	public Optional<Category> findCategoryByCategoryId(Long id);

	public Optional<Category> findByCategoryName(String categoryName);
	
	public List<Category> findAllByInstock(boolean instock);
	
	public void deleteCategoryByCategoryId(Long id);

}
