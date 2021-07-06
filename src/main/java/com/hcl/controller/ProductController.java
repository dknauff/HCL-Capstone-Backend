package com.hcl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hcl.model.Product;
import com.hcl.service.ProductService;

@CrossOrigin(value = "https://capstone-front-end-react.herokuapp.com/", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/product")
@PreAuthorize("hasRole('ROLE_USER')")
public class ProductController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductService productService;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping()
	public ResponseEntity<?> addProduct(@RequestBody Product product) {
		Product newProduct = productService.addProduct(product);
		logger.info("A product has been added.");
		System.out.println(product);
		return newProduct == null ? new ResponseEntity<String>("Failed to create product", HttpStatus.BAD_REQUEST)
				: new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getAllProductsById(@PathVariable("id") Long id) {
		Product product = productService.findProductById(id);
		logger.info("A product has been retrieved with id: {}", id);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Long id) {
		Product updatedProduct = productService.updateProduct(product, id);
		logger.info("A product with id: {} has been updated.", id);
		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
		productService.deleteProduct(id);
		logger.warn("A product with id: {} has been deleted", id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.findAllProducts();
		logger.info("A list of products has been retrieved.");
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/instock")
	public ResponseEntity<List<Product>> getAllInstockProducts() {
		logger.info("All products in-stock have been listed.");
		return new ResponseEntity<>(productService.findAllInstockProducts(true), HttpStatus.OK);
	}

	@GetMapping("/instock/{id}")
	public ResponseEntity<List<Product>> getAllInstockByCategory(@PathVariable Long id) {
		logger.info("A list of all products of category with id:{} have been retrieved.", id);
		return new ResponseEntity<>(productService.findAllByCategoryInstock(true, id), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/instock/{id}")
	public ResponseEntity<String> updateStock(@RequestBody boolean instock, @PathVariable Long id) {
		boolean updated = productService.setStock(id, instock);
		logger.info("A product with id:{} had its in-stock updated.", id);
		return updated ? new ResponseEntity<String>("Updated successfully", HttpStatus.OK)
				: new ResponseEntity<String>("Unsuccessful update", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/products/{query}")
	public ResponseEntity<List<Product>> searchByProductName(@PathVariable String query) {
		logger.info("Return all products that have the query:{}", query);
		return new ResponseEntity<>(productService.searchByProductName(query), HttpStatus.OK);
	}
}
