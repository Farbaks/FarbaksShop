package com.faroukbakre.farbaksshop.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    int bodyStatusCode;
    HttpStatus status;
    public CustomException(String message) {
        super(message);
        this.status = null; // Default status code
    }

    public CustomException(String message, int bodyStatusCode, HttpStatus status) {
        super(message);
        this.bodyStatusCode = bodyStatusCode;
        this.status = status;
    }

}
