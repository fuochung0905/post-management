package com.utc2.it.ProductManagement.exception;

import com.utc2.it.ProductManagement.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse>ResourceNotFoundExceptionHandle(ResourceNotFoundException exception){
        String message=exception.getMessage();
        ApiResponse apiResponse= new ApiResponse(message,false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
