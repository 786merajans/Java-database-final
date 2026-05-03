package com.project.code.Controller;

import com.project.code.Model.Product;
import com.project.code.Repo.ProductRepository;
import com.project.code.Repo.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    // ✅ GET /product/{id} → retrieve product by ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // ✅ DELETE /{id} → delete product and related inventory
    @DeleteMapping("/{id}")
    public Map<String, String> deleteProduct(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        // delete inventory entries linked to product
        inventoryRepository.deleteByProductId(id);
        // delete product itself
        productRepository.deleteById(id);
        response.put("message", "Product and related inventory deleted successfully.");
        return response;
    }
}
