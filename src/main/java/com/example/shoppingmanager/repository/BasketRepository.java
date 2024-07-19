package com.example.shoppingmanager.repository;

import com.example.shoppingmanager.entity.Basket;
import com.example.shoppingmanager.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {

}