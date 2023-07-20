package com.utc2.it.ProductManagement.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    String resource;
    String filedName;
    long filedValue;
    public ResourceNotFoundException(String resource,String filedName,long filedValue){
        super(String.format("%s not found with %s: %s",resource,filedName,filedValue));
        this.resource=resource;
        this.filedName=filedName;
        this.filedValue=filedValue;
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>handleMethodArgsNotValidException(MethodArgumentNotValidException exception){
        Map<String,String>response=new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error)->{
            String filedName=((FieldError)error).getField();
            String message=error.getDefaultMessage();
            response.put(filedName,message);
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
