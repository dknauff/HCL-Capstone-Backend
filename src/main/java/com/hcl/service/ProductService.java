package com.hcl.service;

import java.util.List;

import com.hcl.model.Product;

public interface ProductService {

    Product addProduct(Product product);

    List<Product> findAllProducts();
    
    Product findProductById(Long id);

    Product updateProduct(Product product, Long id);

    void deleteProduct(Long id);
	
}
