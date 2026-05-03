package com.project.code.Service;

import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceClass {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    // 1. Validate inventory existence
    public boolean validateInventory(Inventory inventory) {
        Optional<Inventory> existingInventory =
                inventoryRepository.findByProductAndStore(inventory.getProduct(), inventory.getStore());
        return existingInventory.isEmpty(); // false if inventory exists, true otherwise
    }

    // 2. Validate product existence by name
    public boolean validateProduct(Product product) {
        Product existingProduct = productRepository.findByName(product.getName());
        return existingProduct == null; // false if product exists, true otherwise
    }

    // 3. Validate product existence by ID
    public boolean validateProductId(long id) {
        return productRepository.existsById(id); // true if product exists, false otherwise
    }

    // 4. Get inventory record for product-store combination
    public Inventory getInventoryId(Inventory inventory) {
        Optional<Inventory> existingInventory =
                inventoryRepository.findByProductAndStore(inventory.getProduct(), inventory.getStore());
        return existingInventory.orElse(null);
    }
}
