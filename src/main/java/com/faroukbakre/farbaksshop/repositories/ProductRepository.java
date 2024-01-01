package com.faroukbakre.farbaksshop.repositories;

import com.faroukbakre.farbaksshop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findByName(String name);

    Product findByIdAndQuantityGreaterThanEqual(int id, int value);
}
