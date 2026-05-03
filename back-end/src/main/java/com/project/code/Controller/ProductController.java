package com.project.code.Controller;

import com.project.code.Model.Product;
import com.project.code.Repo.ProductRepository;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Service.ServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ServiceClass serviceClass;

    // Add product
    @PostMapping
    public Map<String, Object> addProduct(@RequestBody Product product) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (serviceClass.validateProduct(product)) {
                productRepository.save(product);
                response.put("message", "Product added successfully.");
            } else {
                response.put("message", "Product validation failed.");
            }
        } catch (DataIntegrityViolationException e) {
            response.put("message", "Error: Product already exists or violates constraints.");
        }
        return response;
    }

    // Get product by ID
    @GetMapping("/{id}")
    public Map<String, Object> getProductById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Product> product = productRepository.findById(id);
        response.put("products", product.orElse(null));
        return response;
    }

    // Update product
    @PutMapping
    public Map<String, Object> updateProduct(@RequestBody Product product) {
        Map<String, Object> response = new HashMap<>();
        productRepository.save(product);
        response.put("message", "Product updated successfully.");
        return response;
    }

    // Filter by category and name
    @GetMapping("/category/{name}/{category}")
    public Map<String, Object> filterByCategoryProduct(@PathVariable String name,
                                                       @PathVariable String category) {
        Map<String, Object> response = new HashMap<>();
        List<Product> products;

        if ("null".equals(name) && "null".equals(category)) {
            products = productRepository.findAll();
        } else if ("null".equals(name)) {
            products = productRepository.findByCategory(category);
        } else if ("null".equals(category)) {
            products = Collections.singletonList(productRepository.findByName(name));
        } else {
            products = productRepository.findProductBySubNameAndCategory(name, category);
        }

        response.put("products", products);
        return response;
    }

    // List all products
    @GetMapping
    public Map<String, Object> listProduct() {
        Map<String, Object> response = new HashMap<>();
        List<Product> products = productRepository.findAll();
        response.put("products", products);
        return response;
    }

    // Get product by category and storeId
    @GetMapping("/filter/{category}/{storeId}")
    public Map<String, Object> getProductByCategoryAndStoreId(@PathVariable String category,
                                                              @PathVariable Long storeId) {
        Map<String, Object> response = new HashMap<>();
        List<Product> products = productRepository.findProductByCategoryAndStoreId(category, storeId);
        response.put("product", products);
        return response;
    }

    // Delete product (and inventory)
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteProduct(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        if (serviceClass.validateProductId(id)) {
            inventoryRepository.deleteByProductId(id);
            productRepository.deleteById(id);
            response.put("message", "Product and related inventory deleted successfully.");
        } else {
            response.put("message", "Product not found.");
        }
        return response;
    }

    // Search product by name
    @GetMapping("/searchProduct/{name}")
    public Map<String, Object> searchProduct(@PathVariable String name) {
        Map<String, Object> response = new HashMap<>();
        List<Product> products = productRepository.findProductBySubName(name);
        response.put("products", products);
        return response;
    }
}
