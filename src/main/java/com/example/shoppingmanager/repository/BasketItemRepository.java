package com.example.shoppingmanager.repository;

import com.example.shoppingmanager.entity.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {
}
