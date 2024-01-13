package com.faroukbakre.farbaksshop.services;

import com.faroukbakre.farbaksshop.dto.requests.LoginUserRequestDTO;
import com.faroukbakre.farbaksshop.dto.requests.NewUserRequestDTO;
import com.faroukbakre.farbaksshop.dto.responses.DefaultResponseDTO;
import com.faroukbakre.farbaksshop.dto.responses.UserResponseDTO;
import com.faroukbakre.farbaksshop.models.entities.Address;
import com.faroukbakre.farbaksshop.models.entities.Role;
import com.faroukbakre.farbaksshop.models.entities.User;
import com.faroukbakre.farbaksshop.exceptions.CustomException;
import com.faroukbakre.farbaksshop.dto.factories.User_DTO_Factory;
import com.faroukbakre.farbaksshop.models.repositories.AddressRepository;
import com.faroukbakre.farbaksshop.models.repositories.RoleRepository;
import com.faroukbakre.farbaksshop.models.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    public DefaultResponseDTO createUser(NewUserRequestDTO data) {

        // Check if email exists
        User checkUserEmail = this.userRepository.findByEmailAddress(data.getEmailAddress());
        if(checkUserEmail != null) throw new CustomException("Account with email already exists.", 400, HttpStatus.OK);

        // Check if phone number exists
        User checkUserPhoneNumber = this.userRepository.findByPhoneNumber(data.getPhoneNumber());
        if(checkUserPhoneNumber != null) throw new CustomException("Account with phone number already exists.", 400, HttpStatus.OK);

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

        if(user == null) throw new CustomException("Account does not exist.", 400, HttpStatus.OK);

        if (!passwordEncoder.matches(data.getPassword(), user.getPassword())) {
            throw new CustomException("Incorrect password.", 400, HttpStatus.OK);
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
        if(user == null) throw new CustomException("Account does not exist.", 400, HttpStatus.OK);

        return this.userMapper.createUserResponseDTO(user, "User details fetched successful.");
    }

    public DefaultResponseDTO deleteUser(int userId) {

        User user = this.userRepository.findById(userId).orElse(null);
        if(user == null) throw new CustomException("Account does not exist.", 400, HttpStatus.OK);

        this.userRepository.deleteById(userId);

        return new DefaultResponseDTO(200, "User deleted successfully.");
    }
}
