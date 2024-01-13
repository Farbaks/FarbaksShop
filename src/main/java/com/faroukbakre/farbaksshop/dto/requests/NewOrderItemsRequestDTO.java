package com.faroukbakre.farbaksshop.dto.requests;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class NewOrderItemsRequestDTO {

    @Min(value=1, message = "Product Id must be greater than zero.")
    private int productId;

    @Min(value=1, message = "Quantity must be greater than zero.")
    private int quantity;
}
