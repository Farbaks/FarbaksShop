package com.faroukbakre.farbaksshop.repositories;

import com.faroukbakre.farbaksshop.entities.Category;
import com.faroukbakre.farbaksshop.entities.Order;
import com.faroukbakre.farbaksshop.entities.Product;
import com.faroukbakre.farbaksshop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Integer> {

    List<Order> findAllByUserId(int userId);

    List<Order> findAllByDriverId(int driverId);
}
