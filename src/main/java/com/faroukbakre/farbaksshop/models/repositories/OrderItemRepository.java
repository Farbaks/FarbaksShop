package com.faroukbakre.farbaksshop.models.repositories;

import com.faroukbakre.farbaksshop.models.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
