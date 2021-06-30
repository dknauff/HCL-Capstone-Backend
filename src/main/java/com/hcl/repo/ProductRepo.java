package com.hcl.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hcl.model.Category;
import com.hcl.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

    public Optional<Product> findProductByProductId(Long id);

    public void deleteProductByProductId(Long id);
    
    public List<Product> findAllByInstock(boolean instock);

    public List<Product> findAllByInstockAndCategory(boolean instock, Category category);
    
    @Query(nativeQuery = true, value = "Select c.id FROM Cart c INNER JOIN Cart_Item ci ON c.cart_id = ci.cart_id INNER JOIN products P on p.product_id = ci.product_id WHERE p.product_id=?1")
    public List<Long> findAllUsersWhereCartHasProduct(Long productId);
    
    @Query(nativeQuery = true, value = "Select * FROM Products WHERE name LIKE ?1 AND instock is true")
    public List<Product> productNameSearchQuery(String name);
}
