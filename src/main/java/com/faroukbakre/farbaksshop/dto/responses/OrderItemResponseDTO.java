package com.faroukbakre.farbaksshop.dto.responses;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class OrderItemResponseDTO {
    private int id;

    private int productId;

    private int quantity;

    private float amount;

    private float total;
}
