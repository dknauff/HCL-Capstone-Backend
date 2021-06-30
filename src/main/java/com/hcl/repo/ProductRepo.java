package com.hcl.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.model.Category;
import com.hcl.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

    public Optional<Product> findProductByProductId(Long id);

    public void deleteProductByProductId(Long id);
    
    public List<Product> findAllByInstock(boolean instock);

    public List<Product> findAllByInstockAndCategory(boolean instock, Category category);
}
