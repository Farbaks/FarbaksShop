package com.faroukbakre.farbaksshop.dto.responses;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AddressResponseDTO {
    private int id;

    private String addressLine;

}
