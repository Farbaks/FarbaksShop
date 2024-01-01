package com.faroukbakre.farbaksshop.dto;

import lombok.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class NewOrderRequestDTO {

    @Valid
    private List<NewOrderItemsRequestDTO> orders;
}
