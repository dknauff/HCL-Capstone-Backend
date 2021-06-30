package com.hcl.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hcl.model.Category;
import com.hcl.model.Product;
import com.hcl.model.User;
import com.hcl.repo.CategoryRepo;
import com.hcl.repo.ProductRepo;
import com.hcl.repo.UserRepo;

@Service
@Transactional
public class ProductService {

    @Autowired
	private ProductRepo productRepo;
    
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private CartService cartService;
    
    public Product addProduct(Product product) {
    	if(product.getCategory() == null || product.getCategory().getCategoryId() == null)
    		return null;
    	Category cat = categoryRepo.findById(product.getCategory().getCategoryId()).orElse(null);
    	if(cat == null || !cat.isInstock())
    		return null;
    	product.setCategory(cat);
        return productRepo.save(product);
    }

    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    public Product findAllProductById(Long id) {
    	if(id == null)
    		return null;
        return productRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User by id " + id + "was not found"));
    }

    public Product updateProduct(Product product, Long id) {
    	if(id == null)
    		return null;
    	if(product == null)
    		return null;
    	if(product.getCategory() == null || product.getCategory().getCategoryId() == null)
    		return null;
    	Category cat = categoryRepo.findById(product.getCategory().getCategoryId()).orElse(null);
    	if(cat == null)
    		return null;
    	product.setCategory(cat);
        product.setProductId(id);
        return productRepo.save(product);
    }

    public void deleteProduct(Long id) {
    	if(id == null)
    		return;
        productRepo.deleteById(id);
    }
    
    public boolean setStock(Long id, boolean instock) {
    	if(id == null)
    		return false;
    	Product product = productRepo.findById(id).orElse(null);
    	if(product == null)
    		return false;
    	
    	product.setInstock(instock);
    	productRepo.save(product);
    	if(instock)
    		return true;
    	List<Long> uids = productRepo.findAllUsersWhereCartHasProduct(id);
    	List<User> users = userRepo.findAllById(uids);
    	users.forEach(x -> cartService.deleteCartItems(x, id, Integer.MAX_VALUE));
    	return true;
    }
    
    public List<Product> findAllInstockProducts(boolean instock){
    	return productRepo.findAllByInstock(instock);
    }
    
    public List<Product> findAllByCategoryInstock(boolean instock, Long categoryId){
    	if(categoryId == null)
    		return new ArrayList<>();
    	Category cat = categoryRepo.findById(categoryId).orElse(null);
    	if(cat == null)
    		return new ArrayList<>();
    	
    	return productRepo.findAllByInstockAndCategory(instock, cat);
    }
    
    public List<Product> searchByProductName(String query){
    	if(query == null)
    		return new ArrayList<>();
    	return productRepo.productNameSearchQuery(query + "%");
    }
}
