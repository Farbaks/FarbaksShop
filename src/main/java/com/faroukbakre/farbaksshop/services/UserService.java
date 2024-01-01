package com.faroukbakre.farbaksshop.services;

import com.faroukbakre.farbaksshop.dto.*;
import com.faroukbakre.farbaksshop.entities.Address;
import com.faroukbakre.farbaksshop.entities.Role;
import com.faroukbakre.farbaksshop.entities.User;
import com.faroukbakre.farbaksshop.factories.User_DTO_Factory;
import com.faroukbakre.farbaksshop.repositories.AddressRepository;
import com.faroukbakre.farbaksshop.repositories.RoleRepository;
import com.faroukbakre.farbaksshop.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final User_DTO_Factory userMapper;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AddressRepository addressRepository;

    public String helloWorld() {
        return "Hello World";
    }
    public DefaultResponseDTO createUser(NewUserRequestDTO data) {

        // Check if email exists
        User checkUserEmail = this.userRepository.findByEmailAddress(data.getEmailAddress());

        if(checkUserEmail != null) {
            return new DefaultResponseDTO(401, "Account with email already exists.");
        }

        // Check if phone number exists
        User checkUserPhoneNumber = this.userRepository.findByPhoneNumber(data.getPhoneNumber());

        if(checkUserPhoneNumber != null) {
            return new DefaultResponseDTO(401, "Account with phone number already exists.");
        }

        // Create new user
        User newUser = new User();
        newUser.setFirstName(data.getFirstName());
        newUser.setLastName(data.getLastName());
        newUser.setEmailAddress(data.getEmailAddress());
        newUser.setPhoneNumber(data.getPhoneNumber());

        String hashedPassword = passwordEncoder.encode(data.getPassword());
        newUser.setPassword(hashedPassword);

        // Set Role
        Role userRole = this.roleRepository.findById(data.getRoleId()).orElse(null);
        if (userRole == null) return null;
        newUser.setRole(userRole);

        this.userRepository.save(newUser);

        // Save Address
        Address newAddress = new Address();
        newAddress.setAddressLine(data.getAddress());
        newAddress.setUser(newUser);
        addressRepository.save(newAddress);

        newUser.addAddress(newAddress);

        return this.userMapper.createUserResponseDTO(newUser, "User signup successful.");
    }

    public DefaultResponseDTO loginUser(LoginUserRequestDTO data) {
        User user = this.userRepository.findByEmailAddress(data.getEmailAddress());

        if(user == null) {
            return new DefaultResponseDTO(401, "Account does not exist.");
        }

        if (!passwordEncoder.matches(data.getPassword(), user.getPassword())) {
            return new DefaultResponseDTO(401, "Incorrect password.");
        }

        return this.userMapper.createUserResponseDTO(user, "User login successful.");
    }

    public DefaultResponseDTO getAllUsers() {

        List<UserResponseDTO> list = new ArrayList<>();
        List<User> users = this.userRepository.findAll();

        for (User user : users)
        {
            UserResponseDTO userDTO = this.userMapper.createDTO(user);
            list.add(userDTO);
        }

        return this.userMapper.createUserListResponseDTO(list);
    }

    public DefaultResponseDTO getUserDetails(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");

        User user = this.userRepository.findById(Integer.valueOf(userId)).orElse(null);

        if(user == null) {
            return new DefaultResponseDTO(401, "Account does not exist.");
        }

        return this.userMapper.createUserResponseDTO(user, "User details fetched successful.");
    }

    public DefaultResponseDTO deleteUser(int userId) {

        User user = this.userRepository.findById(userId).orElse(null);

        if(user == null) {
            return new DefaultResponseDTO(401, "User does not exist.");
        }

        this.userRepository.deleteById(userId);

        return new DefaultResponseDTO(200, "User deleted successfully.");
    }
}