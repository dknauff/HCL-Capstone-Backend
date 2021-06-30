package com.hcl.service;

import com.hcl.model.Product;
import com.hcl.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

	@Override
	public Product addProduct(Product product) {
		return productRepo.save(product);
	}

	@Override
	public List<Product> findAllProducts() {
		return productRepo.findAll();
	}

	@Override
	public Product findProductById(Long id) {
		return productRepo.findProductByProductId(id).orElse(null);
	}

	@Override
	public Product updateProduct(Product product, Long id) {
		product.setProductId(id);
		return productRepo.save(product);
	}

	@Override
	public void deleteProduct(Long id) {
		productRepo.deleteProductByProductId(id);
		
	}
}
