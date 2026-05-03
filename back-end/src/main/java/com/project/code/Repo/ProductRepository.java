package com.project.code.Repo;

import com.project.code.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 2. Retrieve all products
    List<Product> findAll();

    // 2. Retrieve products by category
    List<Product> findByCategory(String category);

    // 2. Retrieve products within a price range
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    // 2. Retrieve product by SKU
    Product findBySku(String sku);

    // 2. Retrieve product by name
    Product findByName(String name);

    // 2. Retrieve products by name pattern for a specific store
    @Query("SELECT p FROM Product p JOIN Inventory i ON p.id = i.product.id " +
           "WHERE i.store.id = :storeId AND p.name LIKE %:pname%")
    List<Product> findByNameLike(Long storeId, String pname);

    // Extra methods referenced in controllers:
    List<Product> findByCategoryAndName(String category, String name);

    List<Product> findByNameAndStoreId(String name, Long storeId);

    List<Product> findProductBySubNameAndCategory(String name, String category);

    List<Product> findProductByCategoryAndStoreId(String category, Long storeId);

    List<Product> findByStoreId(Long storeId);

    List<Product> findProductBySubName(String name);
}
