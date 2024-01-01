package com.faroukbakre.farbaksshop.exceptions;

import com.faroukbakre.farbaksshop.dto.DefaultResponseDTO;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    DefaultResponseDTO body;
    HttpStatus status;
    public CustomException(String message) {
        super(message);
        this.status = null; // Default status code
    }

    public CustomException(String message, DefaultResponseDTO body, HttpStatus status) {
        super(message);
        this.body = body;
        this.status = status;
    }

}
