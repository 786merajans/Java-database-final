package com.project.code.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotNull;

@Document(collection = "reviews")
public class Review {

    // 9. Primary key for MongoDB
    @Id
    private String id;

    // 1. Customer ID
    @NotNull(message = "Customer cannot be null")
    private Long customerId;

    // 2. Product ID
    @NotNull(message = "Product cannot be null")
    private Long productId;

    // 3. Store ID
    @NotNull(message = "Store cannot be null")
    private Long storeId;

    // 4. Rating
    @NotNull(message = "Rating cannot be null")
    private Integer rating;

    // 5. Comment (optional)
    private String comment;

    // 8. Constructors
    public Review() {}

    public Review(Long customerId, Long productId, Long storeId, Integer rating, String comment) {
        this.customerId = customerId;
        this.productId = productId;
        this.storeId = storeId;
        this.rating = rating;
        this.comment = comment;
    }

    // 10. Getters and Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getStoreId() {
        return storeId;
    }
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}
