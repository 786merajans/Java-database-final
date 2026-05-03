package com.project.code.Model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class OrderItem {

    // 1. Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 2. Order relationship
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonManagedReference
    private OrderDetails order;

    // 3. Product relationship
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonManagedReference
    private Product product;

    // 4. Quantity
    private Integer quantity;

    // 5. Price
    private Double price;

    // 6. Constructors
    public OrderItem() {}

    public OrderItem(OrderDetails order, Product product, Integer quantity, Double price) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    // 8. Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public OrderDetails getOrder() {
        return order;
    }
    public void setOrder(OrderDetails order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
}
