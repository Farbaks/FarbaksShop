package com.faroukbakre.farbaksshop.models.repositories;

import com.faroukbakre.farbaksshop.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
