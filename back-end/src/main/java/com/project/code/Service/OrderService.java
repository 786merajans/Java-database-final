package com.project.code.Service;

import com.project.code.Model.*;
import com.project.code.Repo.*;
import com.project.code.DTO.PlaceOrderRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    // 1. Save order method
    public void saveOrder(PlaceOrderRequestDTO placeOrderRequest) {
        // 2. Retrieve or create customer
        Customer customer = customerRepository.findByEmail(placeOrderRequest.getCustomerEmail());
        if (customer == null) {
            customer = new Customer();
            customer.setName(placeOrderRequest.getCustomerName());
            customer.setEmail(placeOrderRequest.getCustomerEmail());
            customer.setPhone(placeOrderRequest.getCustomerPhone());
            customer = customerRepository.save(customer);
        }

        // 3. Retrieve store
        Store store = storeRepository.findById(placeOrderRequest.getStoreId());
        if (store == null) {
            throw new RuntimeException("Store not found with ID: " + placeOrderRequest.getStoreId());
        }

        // 4. Create OrderDetails
        OrderDetails orderDetails = new OrderDetails(customer, store,
                placeOrderRequest.getTotalPrice(), LocalDateTime.now());
        orderDetails = orderDetailsRepository.save(orderDetails);

        // 5. Create and save OrderItems
        placeOrderRequest.getProducts().forEach(productRequest -> {
            Optional<Inventory> inventoryOpt =
                    inventoryRepository.findByProductIdAndStoreId(productRequest.getProductId(), store.getId());

            if (inventoryOpt.isPresent()) {
                Inventory inventory = inventoryOpt.get();
                int updatedStock = inventory.getStockLevel() - productRequest.getQuantity();
                if (updatedStock < 0) {
                    throw new RuntimeException("Insufficient stock for product ID: " + productRequest.getProductId());
                }
                inventory.setStockLevel(updatedStock);
                inventoryRepository.save(inventory);

                OrderItem orderItem = new OrderItem(orderDetails,
                        productRequest.getProduct(),
                        productRequest.getQuantity(),
                        productRequest.getPrice());
                orderItemRepository.save(orderItem);
            } else {
                throw new RuntimeException("Inventory not found for product ID: " + productRequest.getProductId());
            }
        });
    }
}
