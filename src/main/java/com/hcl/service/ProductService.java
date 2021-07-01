package com.hcl.service;

import java.util.List;

import com.hcl.model.Product;

public interface ProductService {

    Product addProduct(Product product);

    List<Product> findAllProducts();
    
    Product findProductById(Long id);

    Product updateProduct(Product product, Long id);

    void deleteProduct(Long id);
  
    boolean setStock(Long id, boolean instock);
    
    List<Product> findAllInstockProducts(boolean instock);
    
    List<Product> findAllByCategoryInstock(boolean instock, Long categoryId);
    
    List<Product> searchByProductName(String query);
}
