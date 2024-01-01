package com.faroukbakre.farbaksshop.repositories;

import com.faroukbakre.farbaksshop.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
