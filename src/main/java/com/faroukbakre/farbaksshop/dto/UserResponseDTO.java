package com.faroukbakre.farbaksshop.dto;


import com.faroukbakre.farbaksshop.entities.Role;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserResponseDTO {
    private final int id;

    private final String firstName;

    private final String lastName;

    private final String emailAddress;

    private final String phoneNumber;

    private final Role role;

    private List<AddressResponseDTO> addresses;
}
