package com.hcl.controller;

import com.hcl.model.Product;
import com.hcl.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductService service;

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product newProduct = service.addProduct(product);
        logger.info("A product has been added.");
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = service.findAllProducts();
        logger.info("A list of products has been retrieved.");
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getAllProductsById(@PathVariable("id") Long id) {
        Product product = service.findAllProductById(id);
        logger.info("A product has been retrieved with id: {}", id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Long id) {
        Product updatedProduct = service.updateProduct(product, id);
        logger.info("A product with id: {} has been updated.", id);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
        service.deleteProduct(id);
        logger.warn("A product with id: {} has been deleted", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
