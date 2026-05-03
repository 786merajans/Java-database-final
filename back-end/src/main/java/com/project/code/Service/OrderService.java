package com.project.code.Service;

import com.project.code.Model.OrderDetails;
import com.project.code.Model.OrderItem;
import com.project.code.Model.Inventory;
import com.project.code.Model.Customer;
import com.project.code.Model.Store;
import com.project.code.DTO.PlaceOrderRequestDTO;
import com.project.code.Repo.OrderDetailsRepository;
import com.project.code.Repo.OrderItemRepository;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.CustomerRepository;
import com.project.code.Repo.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StoreRepository storeRepository;

    public void saveOrder(PlaceOrderRequestDTO request) {
        // Save OrderDetails
        Customer customer = customerRepository.findByEmail(request.getCustomerEmail());
        Store store = storeRepository.findById(request.getStoreId());
        OrderDetails orderDetails = new OrderDetails(customer, store, request.getTotalPrice(), LocalDateTime.now());
        orderDetails = orderDetailsRepository.save(orderDetails); // ✅ Explicit save

        // Reduce inventory and save updated stock
        request.getProducts().forEach(productRequest -> {
            Optional<Inventory> inventoryOpt =
                    inventoryRepository.findByProductIdAndStoreId(productRequest.getProductId(), store.getId());

            if (inventoryOpt.isPresent()) {
                Inventory inventory = inventoryOpt.get();
                int updatedStock = inventory.getStockLevel() - productRequest.getQuantity();
                inventory.setStockLevel(updatedStock);
                inventoryRepository.save(inventory); // ✅ Save updated inventory

                // Save OrderItem
                OrderItem orderItem = new OrderItem(orderDetails,
                        productRequest.getProduct(),
                        productRequest.getQuantity(),
                        productRequest.getPrice());
                orderItemRepository.save(orderItem);
            }
        });
    }
}
