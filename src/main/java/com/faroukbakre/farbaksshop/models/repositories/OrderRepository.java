package com.faroukbakre.farbaksshop.models.repositories;

import com.faroukbakre.farbaksshop.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Integer> {

    List<Order> findAllByUserId(int userId);

    List<Order> findAllByDriverId(int driverId);
}
