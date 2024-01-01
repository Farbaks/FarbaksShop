package com.faroukbakre.farbaksshop.dto;

import com.faroukbakre.farbaksshop.entities.Role;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class OrderResponseDTO {
    private final int id;

    private final float amount;

    private final int quantity;

    private final String status;

    private List<OrderItemResponseDTO> items;
}
