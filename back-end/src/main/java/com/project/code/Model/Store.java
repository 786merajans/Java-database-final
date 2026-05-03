package com.project.code.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;

@Entity
public class Store {

    // 1. Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 2. Name field
    @NotNull(message = "Name cannot be null")
    private String name;

    // 3. Address field
    @NotNull(message = "Address cannot be null")
    @NotBlank(message = "Address cannot be blank")
    private String address;

    // 4. Relationship with Inventory
    @OneToMany(mappedBy = "store", fetch = FetchType.EAGER)
    @JsonManagedReference("inventory-store")
    private List<Inventory> inventories;

    // 5. Constructors
    public Store() {}

    public Store(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // 7. Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }
    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }
}
