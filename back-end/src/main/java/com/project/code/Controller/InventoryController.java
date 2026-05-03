package com.project.code.Controller;

import com.project.code.Model.Inventory;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    // Add inventory
    @PostMapping
    public Map<String, Object> addInventory(@RequestBody Inventory inventory) {
        Map<String, Object> response = new HashMap<>();
        inventoryRepository.save(inventory);
        response.put("message", "Inventory added successfully.");
        return response;
    }

    // GET /filter/{category}/{name}/{storeId} with conditional logic
    @GetMapping("/filter/{category}/{name}/{storeId}")
    public Map<String, Object> filterInventory(@PathVariable String category,
                                               @PathVariable String name,
                                               @PathVariable Long storeId) {
        Map<String, Object> response = new HashMap<>();
        List<?> products;

        if ("null".equals(category) && "null".equals(name)) {
            products = productRepository.findByStoreId(storeId);
        } else if ("null".equals(category)) {
            products = productRepository.findByNameAndStoreId(name, storeId);
        } else if ("null".equals(name)) {
            products = productRepository.findProductByCategoryAndStoreId(category, storeId);
        } else {
            products = productRepository.findByNameLike(storeId, name);
        }

        response.put("products", products);
        return response;
    }

    // GET /validate/{quantity}/{storeId}/{productId} to validate available quantity
    @GetMapping("/validate/{quantity}/{storeId}/{productId}")
    public Map<String, Object> validateQuantity(@PathVariable int quantity,
                                                @PathVariable Long storeId,
                                                @PathVariable Long productId) {
        Map<String, Object> response = new HashMap<>();
        Optional<Inventory> inventoryOpt = inventoryRepository.findByProductIdAndStoreId(productId, storeId);

        if (inventoryOpt.isPresent()) {
            Inventory inventory = inventoryOpt.get();
            boolean available = inventory.getStockLevel() >= quantity;
            response.put("valid", available);
        } else {
            response.put("valid", false);
            response.put("message", "Inventory not found for given product and store.");
        }

        return response;
    }

    // List all inventory
    @GetMapping
    public Map<String, Object> listInventory() {
        Map<String, Object> response = new HashMap<>();
        List<Inventory> inventories = inventoryRepository.findAll();
        response.put("inventories", inventories);
        return response;
    }
}
