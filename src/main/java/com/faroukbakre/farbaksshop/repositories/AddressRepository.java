package com.faroukbakre.farbaksshop.repositories;

import com.faroukbakre.farbaksshop.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
