package com.faroukbakre.farbaksshop.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class EditProductRequestDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Min(value=0, message = "Quantity must be at least zero.")
    private int quantity;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @Min(value=1, message = "Amount must be greater than zero.")
    private float amount;

    @NotBlank(message = "Color cannot be blank")
    private String color;

    @Min(value=1, message = "Category Id must be greater than zero.")
    private int categoryId;

}
