package com.faroukbakre.farbaksshop.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditOrderRequestDTO {

    @NotBlank(message = "Status cannot be blank")
    @Pattern(regexp = "^(shipped|delivered)$")
    private String status;
}
