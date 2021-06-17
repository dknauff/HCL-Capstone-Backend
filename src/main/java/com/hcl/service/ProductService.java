package com.hcl.service;

import com.hcl.model.Product;
import com.hcl.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepo repo;

    public Product addProduct(Product product) {
        return repo.save(product);
    }

    public List<Product> findAllProducts() {
        return repo.findAll();
    }

    public Product findAllProductById(Long id) {
        return repo.findProductByProductId(id).orElseThrow(() -> new UsernameNotFoundException("User by id " + id + "was not found"));
    }

    public Product updateProduct(Product product, Long id) {
        product.setProductId(id);
        return repo.save(product);
    }

    public void deleteProduct(Long id) {
        repo.deleteProductByProductId(id);
    }
}
