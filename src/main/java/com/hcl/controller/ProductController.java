package com.hcl.controller;

import com.hcl.model.Product;
import com.hcl.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService service;

	@PostMapping()
	public ResponseEntity<?> addProduct(@RequestBody Product product) {
		Product newProduct = service.addProduct(product);
		return newProduct == null ? new ResponseEntity<String>("Failed to create product", HttpStatus.BAD_REQUEST)
				: new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getAllProductsById(@PathVariable("id") Long id) {
		Product product = service.findAllProductById(id);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Long id) {
		Product updatedProduct = service.updateProduct(product, id);
		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
		service.deleteProduct(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = service.findAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/instock")
	public ResponseEntity<List<Product>> getAllInstockProducts() {
		return new ResponseEntity<>(service.findAllInstockProducts(true), HttpStatus.OK);
	}

	@GetMapping("/instock/{id}")
	public ResponseEntity<List<Product>> getAllInstockByCategory(@PathVariable Long id){
		return new ResponseEntity<>(service.findAllByCategoryInstock(true, id), HttpStatus.OK);
	}
	@PutMapping("/instock/{id}")
	public ResponseEntity<String> updateStock(@RequestBody boolean instock, @PathVariable Long id) {
		boolean updated = service.setStock(id, instock);
		return updated ? new ResponseEntity<String>("Updated successfully", HttpStatus.OK)
				: new ResponseEntity<String>("Unsuccessful update", HttpStatus.BAD_REQUEST);
	}

}
