package com.project.code.Controller;

import com.project.code.Model.Review;
import com.project.code.Repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    // GET /reviews → return all reviews using findAll(), wrapped in structured response
    @GetMapping("/reviews")
    public Map<String, Object> getAllReviews() {
        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviewRepository.findAll());
        return response;
    }

    // GET /{storeId}/{productId} → fetch reviews for a product in a store, include customer names
    @GetMapping("/{storeId}/{productId}")
    public Map<String, Object> getReviewsByStoreAndProduct(@PathVariable Long storeId,
                                                           @PathVariable Long productId) {
        Map<String, Object> response = new HashMap<>();
        List<Review> reviews = reviewRepository.findByStoreIdAndProductId(storeId, productId);

        // Build structured response including customer names
        List<Map<String, Object>> reviewDetails = new ArrayList<>();
        for (Review review : reviews) {
            Map<String, Object> detail = new HashMap<>();
            detail.put("customerName", review.getCustomerName()); // assuming Review has getCustomerName()
            detail.put("rating", review.getRating());
            detail.put("comment", review.getComment());
            reviewDetails.add(detail);
        }

        response.put("reviews", reviewDetails);
        return response;
    }
}
