package com.project.code.Repo;

import com.project.code.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // ✅ Find product by name
    Product findByName(String name);

    // ✅ Filter products by category
    List<Product> findByCategory(String category);

    // ✅ Search products by partial name
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findProductBySubName(String name);

    // ✅ Filter products by category and storeId
    @Query("SELECT p FROM Product p JOIN Inventory i ON p.id = i.product.id WHERE p.category = :category AND i.store.id = :storeId")
    List<Product> findProductByCategoryAndStoreId(String category, Long storeId);

    // ✅ Filter products by storeId only
    @Query("SELECT p FROM Product p JOIN Inventory i ON p.id = i.product.id WHERE i.store.id = :storeId")
    List<Product> findByStoreId(Long storeId);

    // ✅ Filter products by partial name and category
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) AND p.category = :category")
    List<Product> findProductBySubNameAndCategory(String name, String category);

    // ✅ Filter products by name and storeId
    @Query("SELECT p FROM Product p JOIN Inventory i ON p.id = i.product.id WHERE p.name = :name AND i.store.id = :storeId")
    List<Product> findByNameAndStoreId(String name, Long storeId);

    // ✅ Filter products by name (like) and storeId
    @Query("SELECT p FROM Product p JOIN Inventory i ON p.id = i.product.id WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) AND i.store.id = :storeId")
    List<Product> findByNameLike(Long storeId, String name);
}
