package com.faroukbakre.farbaksshop.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class DefaultResponseDTO {

    private int statusCode;

    private String message;

    private Object data;


    public DefaultResponseDTO(int statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }
}
