package com.faroukbakre.farbaksshop.models.repositories;

import com.faroukbakre.farbaksshop.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
