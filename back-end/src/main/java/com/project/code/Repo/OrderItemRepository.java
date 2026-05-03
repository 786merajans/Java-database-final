package com.project.code.Repo;

import com.project.code.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // No custom methods required — default CRUD operations are available:
    // save(), delete(), update(), findById(), findAll(), etc.
}
