package com.faroukbakre.farbaksshop.exceptions;

import com.faroukbakre.farbaksshop.dto.responses.DefaultResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<DefaultResponseDTO> handleCustomException(CustomException ex) {
        HttpStatus status = ex.getStatus() != null ? ex.getStatus() : HttpStatus.BAD_REQUEST;
        DefaultResponseDTO errorResponseDTO = new DefaultResponseDTO();
        errorResponseDTO.setMessage(ex.getMessage());
        errorResponseDTO.setStatusCode(ex.getBodyStatusCode());
        return new ResponseEntity<>(errorResponseDTO, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<DefaultResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        HttpStatus status = HttpStatus.BAD_REQUEST;
        DefaultResponseDTO errorResponseDTO = new DefaultResponseDTO();

        errorResponseDTO.setStatusCode(400);
        String message = fieldErrors.get(0).getDefaultMessage();
        errorResponseDTO.setMessage(message);
        return new ResponseEntity<>(errorResponseDTO, status);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<DefaultResponseDTO> handleException(Exception ex) {
        // Log actual error
        System.out.println(ex.getMessage());
        DefaultResponseDTO errorResponseDTO = new DefaultResponseDTO();
        errorResponseDTO.setStatusCode(500);
        errorResponseDTO.setMessage("Internal Server Error");
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
