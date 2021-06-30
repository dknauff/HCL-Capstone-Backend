package com.hcl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.model.Product;
import com.hcl.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping()
	public ResponseEntity<?> addProduct(@RequestBody Product product) {
		Product newProduct = productService.addProduct(product);
		return newProduct == null ? new ResponseEntity<String>("Failed to create product", HttpStatus.BAD_REQUEST)
				: new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getAllProductsById(@PathVariable("id") Long id) {
		Product product = productService.findAllProductById(id);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Long id) {
		Product updatedProduct = productService.updateProduct(product, id);
		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
		productService.deleteProduct(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.findAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/instock")
	public ResponseEntity<List<Product>> getAllInstockProducts() {
		return new ResponseEntity<>(productService.findAllInstockProducts(true), HttpStatus.OK);
	}

	@GetMapping("/instock/{id}")
	public ResponseEntity<List<Product>> getAllInstockByCategory(@PathVariable Long id) {
		return new ResponseEntity<>(productService.findAllByCategoryInstock(true, id), HttpStatus.OK);
	}

	@PutMapping("/instock/{id}")
	public ResponseEntity<String> updateStock(@RequestBody boolean instock, @PathVariable Long id) {
		boolean updated = productService.setStock(id, instock);
		return updated ? new ResponseEntity<String>("Updated successfully", HttpStatus.OK)
				: new ResponseEntity<String>("Unsuccessful update", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/products/{query}")
	public ResponseEntity<List<Product>> searchByProductName(@PathVariable String query){
		return new ResponseEntity<>(productService.searchByProductName(query), HttpStatus.OK);
	}
}
