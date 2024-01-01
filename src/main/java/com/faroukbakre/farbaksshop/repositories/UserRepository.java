package com.faroukbakre.farbaksshop.repositories;

import com.faroukbakre.farbaksshop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailAddress(String emailAddress);

    User findByPhoneNumber(String phoneNumber);

    User findByIdAndRoleId(int id, int roleId);
}
