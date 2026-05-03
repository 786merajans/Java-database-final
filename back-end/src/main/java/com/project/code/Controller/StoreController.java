package com.project.code.Controller;

import com.project.code.Model.Store;
import com.project.code.Repository.StoreRepository;
import com.project.code.Service.OrderService;
import com.project.code.DTO.PlaceOrderRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/store")
public class StoreController {

    // 2. Autowired dependencies
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderService orderService;

    // 3. Add store
    @PostMapping
    public Map<String, String> addStore(@RequestBody Store store) {
        storeRepository.save(store);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Store created successfully.");
        return response;
    }

    // 4. Validate store existence
    @GetMapping("validate/{storeId}")
    public boolean validateStore(@PathVariable Long storeId) {
        return storeRepository.existsById(storeId);
    }

    // 5. Place order
    @PostMapping("/placeOrder")
    public Map<String, String> placeOrder(@RequestBody PlaceOrderRequestDTO requestDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            boolean success = orderService.processOrder(requestDTO);
            if (success) {
                response.put("message", "Order placed successfully.");
            } else {
                response.put("Error", "Failed to process order.");
            }
        } catch (Exception e) {
            response.put("Error", "An error occurred while placing the order.");
        }
        return response;
    }
}
