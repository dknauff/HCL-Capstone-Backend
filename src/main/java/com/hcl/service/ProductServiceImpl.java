package com.hcl.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
public class ProductServiceImpl implements ProductService {

    @Autowired
	private ProductRepo productRepo;
    
    @Autowired
    private CategoryRepo categoryRepo;

	@Override
	public void deleteProduct(Long id) {
		productRepo.deleteProductByProductId(id);
		
	}
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private CartService cartService;
    
    @Override
    public Product addProduct(Product product) {
    	if(product.getCategory() == null || product.getCategory().getCategoryId() == null)
    		return null;
    	Category cat = categoryRepo.findById(product.getCategory().getCategoryId()).orElse(null);
    	if(cat == null || !cat.isInstock())
    		return null;
    	product.setCategory(cat);
        return productRepo.save(product);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product findProductById(Long id) {
    	if(id == null)
    		return null;
        return productRepo.findById(id).orElse(null);
    }

    @Override
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
    	Product oldProduct = productRepo.findById(id).orElse(null);
    	if(oldProduct == null)
    		return null;
    	oldProduct.setDescription(product.getDescription());
    	oldProduct.setImageUrl(product.getImageUrl());
    	oldProduct.setName(product.getName());
    	oldProduct.setPrice(product.getPrice());
    	oldProduct.setCategory(cat);
        return productRepo.save(oldProduct);
    }

    
    @Override
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
    
    @Override
    public List<Product> findAllInstockProducts(boolean instock){
    	return productRepo.findAllByInstock(instock);
    }
    
    @Override
    public List<Product> findAllByCategoryInstock(boolean instock, Long categoryId){
    	if(categoryId == null)
    		return new ArrayList<>();
    	Category cat = categoryRepo.findById(categoryId).orElse(null);
    	if(cat == null)
    		return new ArrayList<>();
    	
    	return productRepo.findAllByInstockAndCategory(instock, cat);
    }
    
    @Override
    public List<Product> searchByProductName(String query){
    	if(query == null)
    		return new ArrayList<>();
    	return productRepo.productNameSearchQuery(query + "%");
    }
}
