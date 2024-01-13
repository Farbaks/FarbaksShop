package com.faroukbakre.farbaksshop.dto.requests;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class LoginUserRequestDTO {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email is not in the correct format")
    private String emailAddress;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}
