package com.project.code.Repo;

import com.project.code.Model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    // 2. Retrieve store by ID
    Store findById(Long id);

    // 2. Retrieve stores whose name contains a given substring
    @Query("SELECT s FROM Store s WHERE s.name LIKE %:pname%")
    List<Store> findBySubName(String pname);
}
