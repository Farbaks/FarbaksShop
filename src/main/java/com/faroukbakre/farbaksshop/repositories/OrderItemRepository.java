package com.faroukbakre.farbaksshop.repositories;

import com.faroukbakre.farbaksshop.entities.Category;
import com.faroukbakre.farbaksshop.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
