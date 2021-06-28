package com.hcl.repo;

import com.hcl.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {

    public Optional<Product> findProductByProductId(Long id);

    public void deleteProductByProductId(Long id);

}
