package com.project.code.Controller;

import com.project.code.Model.Review;
import com.project.code.Model.Customer;
import com.project.code.Repository.ReviewRepository;
import com.project.code.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    // 2. Autowired dependencies
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // 3. Get reviews for a product in a store
    @GetMapping("/{storeId}/{productId}")
    public Map<String, Object> getReviews(@PathVariable Long storeId,
                                          @PathVariable Long productId) {
        Map<String, Object> response = new HashMap<>();
        List<Review> reviews = reviewRepository.findByStoreIdAndProductId(storeId, productId);

        // Filter reviews to include comment, rating, and customerName
        List<Map<String, Object>> filteredReviews = new ArrayList<>();
        for (Review review : reviews) {
            Map<String, Object> reviewData = new HashMap<>();
            reviewData.put("comment", review.getComment());
            reviewData.put("rating", review.getRating());

            Optional<Customer> customer = customerRepository.findById(review.getCustomerId());
            reviewData.put("customerName", customer.map(Customer::getName).orElse("Unknown"));

            filteredReviews.add(reviewData);
        }

        response.put("reviews", filteredReviews);
        return response;
    }
}
