package com.example.shoppingmanager.repository;

import com.example.shoppingmanager.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    void removeCustomerByEmail(String email);

    Optional<Customer> findById(long id);

    Optional<Customer> findByEmail(String email);

    List<Customer> findAll();
}