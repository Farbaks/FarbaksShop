package com.faroukbakre.farbaksshop.dto.requests;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AssignOrderRequestDTO {

    @Min(value = 1, message = "Id must be greater than zero")
    private int driverId;
}
