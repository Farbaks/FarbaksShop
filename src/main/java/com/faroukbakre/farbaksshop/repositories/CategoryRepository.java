package com.faroukbakre.farbaksshop.repositories;

import com.faroukbakre.farbaksshop.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
