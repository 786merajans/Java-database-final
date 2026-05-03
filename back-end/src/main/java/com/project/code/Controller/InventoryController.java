package com.project.code.Controller;

import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Repository.InventoryRepository;
import com.project.code.Repository.ProductRepository;
import com.project.code.Service.ServiceClass;
import com.project.code.Request.CombinedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    // 2. Autowired dependencies
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ServiceClass serviceClass;

    // 3. Update inventory
    @PutMapping("/update")
    public Map<String, Object> updateInventory(@RequestBody CombinedRequest request) {
        Map<String, Object> response = new HashMap<>();
        Product product = request.getProduct();
        Inventory inventory = request.getInventory();

        if (serviceClass.validateProductId(product.getId())) {
            Optional<Inventory> existingInventory = inventoryRepository.findById(inventory.getId());
            if (existingInventory.isPresent()) {
                Inventory inv = existingInventory.get();
                inv.setStockLevel(inventory.getStockLevel());
                inventoryRepository.save(inv);
                response.put("message", "Inventory updated successfully.");
            } else {
                response.put("message", "No inventory data available.");
            }
        } else {
            response.put("message", "Invalid product ID.");
        }
        return response;
    }

    // 4. Save inventory
    @PostMapping("/save")
    public Map<String, Object> saveInventory(@RequestBody Inventory inventory) {
        Map<String, Object> response = new HashMap<>();
        Optional<Inventory> existingInventory = inventoryRepository.findByProductAndStore(
                inventory.getProduct(), inventory.getStore());

        if (existingInventory.isPresent()) {
            response.put("message", "Inventory already exists.");
        } else {
            inventoryRepository.save(inventory);
            response.put("message", "Inventory saved successfully.");
        }
        return response;
    }

    // 5. Get all products for a store
    @GetMapping("/products/{storeId}")
    public Map<String, Object> getAllProducts(@PathVariable Long storeId) {
        Map<String, Object> response = new HashMap<>();
        List<Product> products = productRepository.findByStoreId(storeId);
        response.put("products", products);
        return response;
    }

    // 6. Get product by category and name
    @GetMapping("/filter")
    public Map<String, Object> getProductName(@RequestParam String category,
                                              @RequestParam String name) {
        Map<String, Object> response = new HashMap<>();
        List<Product> products;

        if ("null".equals(category) && "null".equals(name)) {
            products = productRepository.findAll();
        } else if ("null".equals(category)) {
            products = productRepository.findByName(name);
        } else if ("null".equals(name)) {
            products = productRepository.findByCategory(category);
        } else {
            products = productRepository.findByCategoryAndName(category, name);
        }

        response.put("product", products);
        return response;
    }

    // 7. Search product by name in a store
    @GetMapping("/search")
    public Map<String, Object> searchProduct(@RequestParam String name,
                                             @RequestParam Long storeId) {
        Map<String, Object> response = new HashMap<>();
        List<Product> products = productRepository.findByNameAndStoreId(name, storeId);
        response.put("product", products);
        return response;
    }

    // 8. Remove product
    @DeleteMapping("/remove/{productId}")
    public Map<String, Object> removeProduct(@PathVariable Long productId) {
        Map<String, Object> response = new HashMap<>();
        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            productRepository.delete(product.get());
            inventoryRepository.deleteByProductId(productId);
            response.put("message", "Product and related inventory deleted successfully.");
        } else {
            response.put("message", "Product not found.");
        }
        return response;
    }

    // 9. Validate quantity
    @GetMapping("/validate")
    public boolean validateQuantity(@RequestParam Long productId,
                                    @RequestParam Long storeId,
                                    @RequestParam Integer quantity) {
        Optional<Inventory> inventory = inventoryRepository.findByProductIdAndStoreId(productId, storeId);
        return inventory.isPresent() && inventory.get().getStockLevel
