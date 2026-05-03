package com.project.code.Repo;

import com.project.code.Model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // 2. Find inventory by productId and storeId
    Optional<Inventory> findByProductIdAndStoreId(Long productId, Long storeId);

    // 2. Find inventory list by storeId
    List<Inventory> findByStore_Id(Long storeId);

    // 2. Delete inventory by productId
    @Modifying
    @Transactional
    void deleteByProductId(Long productId);

    // Extra: Find inventory by product and store (used in saveInventory logic)
    Optional<Inventory> findByProductAndStore(com.project.code.Model.Product product,
                                              com.project.code.Model.Store store);
}
