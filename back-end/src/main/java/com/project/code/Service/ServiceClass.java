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

    // ✅ validateInventory → checks if inventory entry already exists for product-store combination
    public boolean validateInventory(Inventory inventory) {
        Optional<Inventory> existingInventory =
                inventoryRepository.findByProductIdAndStoreId(
                        inventory.getProduct().getId(),
                        inventory.getStore().getId()
                );
        // false if inventory exists, true otherwise
        return existingInventory.isEmpty();
    }

    // ✅ validateProduct → checks if product exists by name
    public boolean validateProduct(Product product) {
        Product existingProduct = productRepository.findByName(product.getName());
        return existingProduct == null;
    }

    // ✅ validateProductId → checks if product exists by ID
    public boolean validateProductId(long id) {
        return productRepository.existsById(id);
    }

    // ✅ getInventoryId → returns inventory record using product and store IDs
    public Inventory getInventoryId(Inventory inventory) {
        Optional<Inventory> existingInventory =
                inventoryRepository.findByProductIdAndStoreId(
                        inventory.getProduct().getId(),
                        inventory.getStore().getId()
                );
        return existingInventory.orElse(null);
    }
}
