package com.faroukbakre.farbaksshop.dto;

import com.faroukbakre.farbaksshop.entities.Category;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ProductResponseDTO {

    private final int id;

    private final String name;

    private final int quantity;

    private final String description;

    private final float amount;

    private final String color;

    private Category category;
}
