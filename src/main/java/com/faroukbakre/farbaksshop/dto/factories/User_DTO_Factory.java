package com.faroukbakre.farbaksshop.dto.factories;

import com.faroukbakre.farbaksshop.dto.responses.AddressResponseDTO;
import com.faroukbakre.farbaksshop.dto.responses.DefaultResponseDTO;
import com.faroukbakre.farbaksshop.dto.responses.UserResponseDTO;
import com.faroukbakre.farbaksshop.models.entities.Address;
import com.faroukbakre.farbaksshop.models.entities.User;
import com.faroukbakre.farbaksshop.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class User_DTO_Factory {

    @Autowired
    private JwtUtil jwtUtil;

    public DefaultResponseDTO createUserListResponseDTO(List<UserResponseDTO> data) {
        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatusCode(200);
        response.setMessage("Users fetched successfully.");
        response.setData(data);

        return response;
    }

    public DefaultResponseDTO createUserResponseDTO(User user, String message)
    {
        UserResponseDTO userDTO = new UserResponseDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmailAddress(),
                user.getPhoneNumber(),
                user.getRole()
        );

        userDTO.setAddresses(createDTOCollection(user.getAddresses()));

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatusCode(200);
        response.setMessage(message);

        Map<String, Object> data = new HashMap<>();
        data.put("apiToken", jwtUtil.generateToken(user));
        data.put("user", userDTO);
        response.setData(data);;

        return response;
    }

    public UserResponseDTO createDTO(User user)
    {
        UserResponseDTO userDTO = new UserResponseDTO(
                       user.getId(),
                       user.getFirstName(),
                       user.getLastName(),
                       user.getEmailAddress(),
                       user.getPhoneNumber(),
                       user.getRole()
                );

        userDTO.setAddresses(createDTOCollection(user.getAddresses()));

        return userDTO;
    }

    private List<AddressResponseDTO> createDTOCollection(List<Address> addresses)
    {
        return addresses.stream().map(this::createAddressDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    public AddressResponseDTO createAddressDTO(Address address)
    {
        return new AddressResponseDTO(
                address.getId(),
                address.getAddressLine()
        );

    }
}
