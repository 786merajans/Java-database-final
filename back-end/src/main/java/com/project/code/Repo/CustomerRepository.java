package com.project.code.Repo;

import com.project.code.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // 2. Find customer by email
    Customer findByEmail(String email);

    // 2. Find customer by ID
    Customer findById(Long id);

    // 3. Find customers by name
    List<Customer> findByName(String name);

    // 3. Find customers by phone number
    List<Customer> findByPhone(String phone);
}
