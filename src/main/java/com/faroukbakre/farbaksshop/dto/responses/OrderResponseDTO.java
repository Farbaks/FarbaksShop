package com.faroukbakre.farbaksshop.dto.responses;

import com.faroukbakre.farbaksshop.dto.responses.OrderItemResponseDTO;
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
