package com.project.code.Controller;

import com.project.code.DTO.PlaceOrderRequestDTO;
import com.project.code.Service.OrderService;
import com.project.code.Repo.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderService orderService;

    // ✅ GET (validate/store/{id}) → validate store existence
    @GetMapping("/validate/store/{id}")
    public boolean validateStore(@PathVariable Long id) {
        return storeRepository.existsById(id);
    }

    // ✅ placeOrder method with try-catch block
    @PostMapping("/placeOrder")
    public Map<String, String> placeOrder(@RequestBody PlaceOrderRequestDTO requestDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            orderService.saveOrder(requestDTO);
            response.put("message", "Order placed successfully.");
        } catch (Exception e) {
            response.put("error", "An error occurred while placing the order.");
        }
        return response;
    }
}
